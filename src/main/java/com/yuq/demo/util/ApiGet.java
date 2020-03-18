package com.yuq.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class ApiGet {

    public static String RankImageGet(String url,int num) throws Exception{
        //设定计数
        int nomalNum = 0;
        int r18Num = 0;
        //设定下载的Url
        ArrayList<String> nomalImageUrl = new ArrayList<>();
        ArrayList<String> r_18_ImageUrl = new ArrayList<>();
        //设定下载的图片名称
        ArrayList<String> nomalImageName = new ArrayList<>();
        ArrayList<String> r_18_ImageName = new ArrayList<>();
        //发送请求基本返回值
        JSONObject baseJson = HttpConn.temp(url,null);
        //System.out.println("baseJson:"+baseJson.toString());
        String pagination = "";
        //如果请求成功，获取返回体进行拆分
        String status = baseJson.get("status").toString();
        if("success".equals(status)){
            pagination = baseJson.getJSONObject("pagination").toString();
            JSONObject responseJson = baseJson.getJSONArray("response").getJSONObject(0);
            //  System.out.println("responseJson"+responseJson.toString());
            //提取works-----排行结果有
            JSONArray workJson = responseJson.getJSONArray("works");
            //  System.out.println("work"+workJson.toString());
            JSONObject imageJson = null;
            //循环遍历works内容
            for(int i = 0;i<workJson.size();i++){
                imageJson = workJson.getJSONObject(i);
                JSONObject imgScore = imageJson.getJSONObject("work").getJSONObject("stats");
                int imageScoore= (int) imgScore.get("score");
                if(imageScoore>200){
                    String allAge = imageJson.getJSONObject("work").get("age_limit").toString();
                    JSONObject imageUrlArr =imageJson.getJSONObject("work").getJSONObject("image_urls");
                    String imageUrlS=imageUrlArr.get("large").toString();
                    String imageName =imageJson.getJSONObject("work").get("id").toString();
                    if("all-age".equals(allAge)){
                        nomalImageUrl.add(imageUrlS);
                        nomalImageName.add(imageName);
                        nomalNum++;
                    }else {
                        r_18_ImageUrl.add(imageUrlS);
                        r_18_ImageName.add(imageName);
                        r18Num++;
                    }
                }
            }
            OkHttpDownloadImg.downloadImage(nomalImageUrl,nomalImageName,"normal","pixivimg/normal/");
            OkHttpDownloadImg.downloadImage(r_18_ImageUrl,r_18_ImageName,"r18","pixivimg/r18/");
        }else {
            pagination=baseJson.toString();
        }

        String result = "单次搜索"+num+"个，\n" +
                "其中常规图片："+nomalNum+"个，\n" +
                "非常规图片："+r18Num+"个，\n"+pagination;
        return result;
    }



    public static String SearchImageGet(String url,int num)throws Exception{
        //设定计数
        int normalNum = 0;

        int r18Num = 0;
        //设定下载的Url
        ArrayList<String> nomalImageUrl = new ArrayList<>();
        ArrayList<String> r18ImageUrl = new ArrayList<>();
        //设定下载的图片名称
        ArrayList<String> normalImageUrl = new ArrayList<>();
        ArrayList<String> r18ImageName = new ArrayList<>();
        //发送请求基本返回值
        JSONObject baseJson = HttpConn.temp(url,"");

        String pagination = null;

        //如果请求成功，获取返回体进行拆分
        String status = baseJson.get("status").toString();
        if("success".equals(status)){
            JSONArray responseJson = baseJson.getJSONArray("response");
            pagination = baseJson.getJSONObject("pagination").toString();
            JSONObject imageJson = null;
            //循环遍历内容
            for(int i = 0;i<responseJson.size();i++){
                imageJson = responseJson.getJSONObject(i);
                JSONArray tagArr = imageJson.getJSONArray("tags");
                boolean iscontinue = false;
                for(int j=0;j<tagArr.size();j++){
                    String isGay = tagArr.get(j).toString();
                    if(isGay.contains("gay")){
                        iscontinue =true;
                        break;
                    }
                }
                if(iscontinue){
                    continue;
                }
                int socre = (int) imageJson.getJSONObject("stats").get("score");
                if(socre>200){
                    String allAge = imageJson.get("age_limit").toString();
                    JSONObject imageUrlArr =imageJson.getJSONObject("image_urls");
                    String imageUrlS=imageUrlArr.get("large").toString();
                    String imageName =imageJson.get("id").toString();
                    if("all-age".equals(allAge)){
                        nomalImageUrl.add(imageUrlS);
                        normalImageUrl.add(imageName);
                        normalNum++;
                    }else {
                        r18ImageUrl.add(imageUrlS);
                        r18ImageName.add(imageName);
                        r18Num++;
                    }
                }
            }

            OkHttpDownloadImg.downloadImage(nomalImageUrl,normalImageUrl,"normal","pixivimg/normal/");
            OkHttpDownloadImg.downloadImage(r18ImageUrl,r18ImageName,"r18","pixivimg/r18/");
        }


        String result = "单次搜索"+num+"个，\n" +
                "其中常规图片："+normalNum+"个，\n" +
                "非常规图片："+r18Num+"个，\n"+pagination;
        return result;
    }
}
