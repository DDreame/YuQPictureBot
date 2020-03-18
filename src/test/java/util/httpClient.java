package util;

import com.alibaba.fastjson.JSONObject;
import com.yuq.demo.util.HttpConn;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

public class httpClient {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        ArrayList<String> imageUrl=new ArrayList<>();
        imageUrl.add("https://i.pximg.net/img-original/img/2020/03/08/14/54/05/79974781_p0.jpg");
        imageUrl.add("https://i.pximg.net/img-original/img/2020/03/08/15/30/04/79975488_p0.jpg");
        imageUrl.add("https://i.pximg.net/img-original/img/2020/03/08/15/30/04/79975487_p0.jpg");
        imageUrl.add("https://i.pximg.net/img-original/img/2020/03/08/15/30/03/79975486_p0.jpg");
        imageUrl.add("https://i.pximg.net/img-original/img/2020/03/08/15/30/03/79975485_p0.png");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String data = simpleDateFormat.format(System.currentTimeMillis());
        Integer qwe = downloadImage(imageUrl,"normal/"+data);
    }
    private static boolean isDownload;
    static OkHttpClient okHttpClient = new OkHttpClient();
    public static Integer downloadImage(ArrayList<String> imageUrl, String imgDir) throws IOException {
        //对下载进行计数
        int downloadNum = 0;
        String path = "./image/pixivimg/"+imgDir;
        //图片的批量下载
        for (int i = 0; i < imageUrl.size(); i++) {
            String imgName = String.valueOf(UUID.randomUUID())+System.currentTimeMillis();
            if(downloadImg(imageUrl.get(i), path,imgName)){
                downloadNum++;
                System.out.println("第"+i+"个，下载ok");
            };


        }
        return downloadNum;
    }
    public static boolean downloadImg(String imgUrl,String imgpath,String imgName) throws IOException {
        isDownload = false;
        File path = new File(imgpath);
        if(!path.exists()){
            path.mkdirs();
        }
        
        Request request = new Request.Builder().url(imgUrl)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1")
                .addHeader("Referer","http://www.pixiv.net/").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                isDownload = false;
                System.out.println("downLoadFalse");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FileUtils.copyInputStreamToFile(response.body().byteStream(), new File(path + "/" + imgName + ".jpg"));
                System.out.println(path);
                isDownload = true;
            }
        });
        return isDownload;
    }


}
