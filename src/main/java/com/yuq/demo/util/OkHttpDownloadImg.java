package com.yuq.demo.util;

import okhttp3.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yuq.demo.TestEventHandler.*;


public class OkHttpDownloadImg {

    static Proxy proxy = new Proxy(Proxy.Type.SOCKS,new InetSocketAddress("127.0.0.1",10808));

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                            .callTimeout(60,TimeUnit.SECONDS)
                                            .readTimeout(60,TimeUnit.SECONDS)
                                            .proxy(proxy)
                                            .build();
    public static Integer downloadImage(ArrayList<String> imageUrl,ArrayList<String>imageName,String type, String imgDir) throws IOException {
        //对下载进行计数
        int downloadNum = 0;
        String path = "./data/image/"+imgDir;
        //图片的批量下载
        for (int i = 0; i < imageUrl.size(); i++) {
           downloadImg(imageUrl.get(i),type, path,imageName.get(i));
        }
        return downloadNum;
    }
    public static void downloadImg(String imgUrl,String type,String imgpath,String imgName) throws IOException {
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
                System.out.println("downLoadFalse");
                System.out.println(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    FileUtils.copyInputStreamToFile(response.body().byteStream(), new File(path + "/" + imgName + ".jpg"));
                    File file1 = new File(path + "/" + imgName + ".jpg");
                    if(file1.length()>maxFileLenth){
                        file1.renameTo(new File("./image/pixiv/" + imgName + ".jpg"));
                    }
                    System.out.println(path + "/" + imgName + ".jpg");
                    allPictureNum++;
                }else {
                    downloadPng(imgUrl,type, imgpath,imgName);
                }

            }
        });
    }

    static Pattern fileEndPattern =Pattern.compile(".jpg");
    public static void downloadPng(String imgUrl,String type,String imgpath,String imgName) throws IOException {
        File path = new File(imgpath);
        if (!path.exists()) {
            path.mkdirs();
        }
        Matcher matcher = fileEndPattern.matcher(imgUrl);
        // 将匹配到的非法字符以空替换
        imgUrl = matcher.replaceAll(".png");
        Request request = new Request.Builder().url(imgUrl)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1")
                .addHeader("Referer", "http://www.pixiv.net/").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("downLoadFalse");
                System.out.println(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200){
                    FileUtils.copyInputStreamToFile(response.body().byteStream(), new File(path + "/" + imgName + ".png"));
                    File file1 = new File(path + "/" + imgName + ".png");
                    if(file1.length()>maxFileLenth){
                        file1.renameTo(new File("./image/pixiv/" + imgName + ".png"));
                    }
                    System.out.println(path + "/" + imgName + ".png");
                    allPictureNum++;
                }
            }
        });

    }
}
