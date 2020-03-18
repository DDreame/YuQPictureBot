package com.yuq.demo.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuq.demo.util.FileUtil;
import com.yuq.demo.util.HttpConn;
import com.yuq.demo.util.OkHttpDownloadImg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * V1API解析，
 * 新建以SearchName为名的目标文件夹，提供诸如指定tag，指定作者的搜索结果
 * 此处不保证图片质量
 */
public class SpecialGetImage {


    public String GetImageByAuther(String AutherId){
        return "qwe";
    }


    /**
     * 传入API请求的URL和下载目的文件夹名称
     * @param url
     * @param DirName
     * @return
     */
    private String DownloadSearchImg(String url,String DirName) throws Exception {
        //设定计数
        int allNum = 0;

        //设定下载的Url
        ArrayList<String> allImageUrl = new ArrayList<>();
        //设定下载的图片名称
        ArrayList<String> allImageName = new ArrayList<>();
        //发送请求基本返回值
        JSONObject baseJson = HttpConn.temp(url,"");

        String pagination = null;

        //  System.out.println("baseJson:"+baseJson.toString());
        //如果请求成功，获取返回体进行拆分
        String status = baseJson.get("status").toString();
        if("success".equals(status)){
            JSONArray responseJson = baseJson.getJSONArray("response");
            //  System.out.println("responseJson"+responseJson.toString());
            pagination = baseJson.getJSONObject("pagination").toString();
            JSONObject imageJson = null;
            //循环遍历内容
            for(int i = 0;i<responseJson.size();i++){
                imageJson = responseJson.getJSONObject(i);
                JSONArray tagArr = imageJson.getJSONArray("tags");
                JSONObject imageUrlArr =imageJson.getJSONObject("image_urls");
                String imageUrlS=imageUrlArr.get("px_480mw").toString();
                allImageUrl.add(imageUrlS);
                String imageName =imageJson.get("id").toString();
                allImageName.add(imageName);
                allNum++;
            }
            OkHttpDownloadImg.downloadImage(allImageUrl,allImageName,"special",DirName);
        }


        String result = "搜索"+DirName+",\n下载"+allNum+"\n"+pagination;
        return result;
    }




}
