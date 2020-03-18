package com.yuq.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * PixivCrawler爬虫核心部分
 */
public class PixivCrawler {

    static Pattern RankStartPattern = Pattern.compile("/c/240x480/img-master");

    /**
     *
     * @param url
     * @param num
     * @return
     * @throws Exception
     */
    public static String rankCrawler(String url,int num,String cookie) throws Exception {
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
        JSONObject baseJson = HttpConn.temp(url,cookie);
        JSONArray contents = baseJson.getJSONArray("contents");
        //开始处理
        for(int i=0;i<contents.size();i++){
            boolean isR18=false;
            JSONObject content = contents.getJSONObject(i);
            JSONArray tagArr = content.getJSONArray("tags");
            for (Object o : tagArr) {
                if ("R-18".equals(o.toString()) || "R18".equals(o.toString()) || "R_18".equals(o.toString())
                        || "r-18".equals(o.toString()) || "r18".equals(o.toString()) || "r_18".equals(o.toString())
                        || "R-18G".equals(o.toString()) || "R18G".equals(o.toString())|| "r-18g".equals(o.toString()) || "r18g".equals(o.toString()) ) {
                    isR18 = true;
                    break;
                }
            }
            if(isR18){
                r_18_ImageName.add(content.get("illust_id").toString());
                r_18_ImageUrl.add(FileUtil.replaceUrl(content.get("url").toString()));
                r18Num++;
            }else {
                nomalImageName.add(content.get("illust_id").toString());
                nomalImageUrl.add(FileUtil.replaceUrl(content.get("url").toString()));
                nomalNum++;
            }
        }
        OkHttpDownloadImg.downloadImage(nomalImageUrl,nomalImageName,"normal","pixivimg/normal/");
        OkHttpDownloadImg.downloadImage(r_18_ImageUrl,r_18_ImageName,"r18","pixivimg/r18/");

        String result = "单次搜索"+num+"个，\n" +
                "其中常规图片："+nomalNum+"个，\n" +
                "非常规图片："+r18Num+"个，\n";
        return result;
    }
    /**
     *
     * @param url
     * @param cookies
     * @return
     * @throws Exception
     */
    public static String searchCrawler(String url,String cookies)throws Exception{
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
        JSONObject baseJson = HttpConn.temp(url,cookies);
        JSONArray contents = baseJson.getJSONObject("body").getJSONObject("illustManga").getJSONArray("data");
        //开始处理
        for(int i=0;i<contents.size();i++){

            boolean isR18=false;
            JSONObject content = contents.getJSONObject(i);
            if((boolean) content.get("isAdContainer")){
                continue;
            }
            JSONArray tagArr = content.getJSONArray("tags");
            for (Object o : tagArr) {
                if ("R-18".equals(o.toString()) || "R18".equals(o.toString()) || "R_18".equals(o.toString())
                        || "r-18".equals(o.toString()) || "r18".equals(o.toString()) || "r_18".equals(o.toString())
                        || "R-18G".equals(o.toString()) || "R18G".equals(o.toString())|| "r-18g".equals(o.toString()) || "r18g".equals(o.toString()) ) {
                    isR18 = true;
                    break;
                }
            }
            if(isR18){
                r_18_ImageName.add(content.get("illustId").toString());
                r_18_ImageUrl.add(FileUtil.replaceUrl(content.get("url").toString()));
                r18Num++;
            }else {
                nomalImageName.add(content.get("illustId").toString());
                nomalImageUrl.add(FileUtil.replaceUrl(content.get("url").toString()));
                nomalNum++;
            }
        }
        OkHttpDownloadImg.downloadImage(nomalImageUrl,nomalImageName,"normal","pixivimg/normal/");
        OkHttpDownloadImg.downloadImage(r_18_ImageUrl,r_18_ImageName,"r18","pixivimg/r18/");


        String result = "单次搜索50个，\n" +
                "其中常规图片："+nomalNum+"个，\n" +
                "非常规图片："+r18Num+"个，\n";
        return result;
    }

    /**
     * 画师爬虫
     * @param url
     * @return
     * @throws Exception
     */
    public static String userImgCrawler(String userId,String url,String cookie)throws Exception{
        //设定计数
        int nomalNum = 0;
        int r18Num = 0;
        int allNum =0;
        //设定下载的Url
        ArrayList<String> normalImageUrl = new ArrayList<>();
        ArrayList<String> r18ImageUrl = new ArrayList<>();
        //设定下载的图片名称
        ArrayList<String> normalImageName = new ArrayList<>();
        ArrayList<String> r18ImageName = new ArrayList<>();
        JSONObject baseJson = HttpConn.temp(url,cookie);
        JSONObject issusts = baseJson.getJSONObject("body").getJSONObject("illusts");
        ArrayList<String> illustsId = getAllKey(issusts);
        allNum = illustsId.size();
        StringBuilder basePictureUrl = new StringBuilder("https://www.pixiv.net/ajax/user/"+userId+"/profile/illusts?ids%5B%5D=");
        int maxPage = allNum/48+1;
        for(int i =0;i<maxPage;i++ ){
            StringBuilder allUrl = new StringBuilder(basePictureUrl);
            for(int j = 0;j<48;j++){
                int index = i*48+j;
                String illustId = illustsId.get(i*48+j);
                allUrl.append(illustId);
                if((index+1)==allNum){
                    break;
                }
                if(j!=47){
                    allUrl.append("&ids%5B%5D=");
                }
            }
            allUrl.append("&work_category=illustManga&is_first_page=");
            if(i == 0){
                allUrl.append(1);
            }else {
                allUrl.append(0);
            }
            JSONObject pictureJson = HttpConn.temp(String.valueOf(allUrl),cookie);
            if((boolean)pictureJson.get("error")){
                return pictureJson.toString();
            }else {
                JSONObject contents = pictureJson.getJSONObject("body").getJSONObject("works");
                //开始处理
                for(int j = 0;j<48;j++){
                    int index = i*48+j;
                    if(index==allNum){
                        break;
                    }
                    String illustId = illustsId.get(i*48+j);
                    boolean isR18=false;
                    JSONObject content = contents.getJSONObject(illustId);
                    if(content==null||(boolean) content.get("isAdContainer")){
                        continue;
                    }
                    JSONArray tagArr = content.getJSONArray("tags");
                    for (Object o : tagArr) {
                        if ("R-18".equals(o.toString()) || "R18".equals(o.toString()) || "R_18".equals(o.toString())
                                || "r-18".equals(o.toString()) || "r18".equals(o.toString()) || "r_18".equals(o.toString())
                                || "R-18G".equals(o.toString()) || "R18G".equals(o.toString())|| "r-18g".equals(o.toString()) || "r18g".equals(o.toString()) ) {
                            isR18 = true;
                            break;
                        }
                    }
                    if(isR18){
                        r18ImageName.add(content.get("illustId").toString());
                        r18ImageUrl.add(FileUtil.replaceUrl(content.get("url").toString()));
                        r18Num++;
                    }else {
                        normalImageName.add(content.get("illustId").toString());
                        normalImageUrl.add(FileUtil.replaceUrl(content.get("url").toString()));
                        nomalNum++;
                    }
                }
                OkHttpDownloadImg.downloadImage(normalImageUrl,normalImageName,"normal","pixivimg/normal/");
                OkHttpDownloadImg.downloadImage(r18ImageUrl,r18ImageName,"r18","pixivimg/r18/");
            }
        }
        return "总计"+allNum+"张图片\n" +
                "其中常规图片："+nomalNum+"个，\n" +
                "非常规图片："+r18Num+"个，\n";
    }




    public static String userBookmarkCrawler(String url,String cookies)throws Exception{
        //设定计数
        int nomalNum = 0;
        int r18Num = 0;
        int allNum =0;
        //设定下载的Url
        ArrayList<String> nomalImageUrl = new ArrayList<>();
        ArrayList<String> r_18_ImageUrl = new ArrayList<>();
        //设定下载的图片名称
        ArrayList<String> nomalImageName = new ArrayList<>();
        ArrayList<String> r_18_ImageName = new ArrayList<>();
        //发送请求基本返回值
        JSONObject baseJson = HttpConn.temp(url,cookies);
        if((boolean)baseJson.get("error")){
            return baseJson.toString();
        }else {
            JSONArray contents = baseJson.getJSONObject("body").getJSONArray("works");
            allNum = (int) baseJson.getJSONObject("body").get("total");
            //开始处理
            for(int i=0;i<contents.size();i++){

                boolean isR18=false;
                JSONObject content = contents.getJSONObject(i);
                if((boolean) content.get("isAdContainer")){
                    continue;
                }
                JSONArray tagArr = content.getJSONArray("tags");
                for (Object o : tagArr) {
                    if ("R-18".equals(o.toString()) || "R18".equals(o.toString()) || "R_18".equals(o.toString())
                            || "r-18".equals(o.toString()) || "r18".equals(o.toString()) || "r_18".equals(o.toString())
                            || "R-18G".equals(o.toString()) || "R18G".equals(o.toString())|| "r-18g".equals(o.toString()) || "r18g".equals(o.toString()) ) {
                        isR18 = true;
                        break;
                    }
                }
                if(isR18){
                    r_18_ImageName.add(content.get("illustId").toString());
                    r_18_ImageUrl.add(FileUtil.replaceUrl(content.get("url").toString()));
                    r18Num++;
                }else {
                    nomalImageName.add(content.get("illustId").toString());
                    nomalImageUrl.add(FileUtil.replaceUrl(content.get("url").toString()));
                    nomalNum++;
                }
            }
            OkHttpDownloadImg.downloadImage(nomalImageUrl,nomalImageName,"normal","pixivimg/normal/");
            OkHttpDownloadImg.downloadImage(r_18_ImageUrl,r_18_ImageName,"r18","pixivimg/r18/");
        }
        String result = "单次搜索48个，\n" +
                "其中常规图片："+nomalNum+"个，\n" +
                "非常规图片："+r18Num+"个，\n"+
                "该用户收藏夹总计"+allNum;
        return result;
    }

    /**
     * 递归读取所有的key
     *
     * @param jsonObject
     */
    public static ArrayList<String> getAllKey(JSONObject jsonObject) {
        ArrayList<String> illustsId = new ArrayList<>();
        Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            illustsId.add(key.toString());
            if (jsonObject.get(key) instanceof JSONObject) {
                JSONObject innerObject = (JSONObject) jsonObject.get(key);
                illustsId.add(key.toString());
            }
        }
        return illustsId;
    }

    public static void main(String[] args) throws Exception {
        userImgCrawler("6434573",
                "https://www.pixiv.net/ajax/user/6434573/profile/all","45849918_Nv31v499jOwnORClUgr6MEptzp4wQT7g");
    }
}
