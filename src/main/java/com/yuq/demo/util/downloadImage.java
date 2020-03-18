package com.yuq.demo.util;

import okhttp3.OkHttpClient;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import sun.text.resources.cldr.FormatData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class downloadImage {


    public static Integer downloadImage(ArrayList<String> imageUrl,String pathEnd) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        //对下载进行计数
        int downloadNum = 0;
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            String path = "./image/pixivimg/"+pathEnd;
            //图片的批量下载
            for (int i = 0; i < imageUrl.size(); i++) {
                String imgName = String.valueOf(System.currentTimeMillis());;
                if(httpGetImg(client,imageUrl.get(i), path+"/"+imgName+".jpg")){
                    downloadNum++;
                };

                System.out.println("第"+i+"个，下载ok");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally{
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e2) {

                }
            }
        }


        return downloadNum;
    }


    public static boolean httpGetImg(CloseableHttpClient client, String imgUrl, String savePath) {
        //默认下载失败
        boolean downloadBoolean = false;
        // 发送get请求
        HttpGet request = new HttpGet(imgUrl);

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000).build();
        // 设置请求头
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
        request.setHeader("Referer","http://www.pixiv.net/");
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                InputStream in = entity.getContent();
                FileUtils.copyInputStreamToFile(in, new File(savePath));
                System.out.println("下载图片成功:" + imgUrl);
                downloadBoolean = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            request.releaseConnection();
        }

        return downloadBoolean;
    }
}
