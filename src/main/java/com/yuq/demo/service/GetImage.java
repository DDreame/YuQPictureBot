package com.yuq.demo.service;

import static com.yuq.demo.util.ApiGet.*;
import static com.yuq.demo.util.PixivCrawler.*;


/**
 * 发送请求链接，发向API或网页
 * 主要提供日常发图功能图源
 */
public class GetImage {
    final static String apiBaseUrl = "https://api.imjad.cn/pixiv/v1/";
    /**
     * V1API
     * 排行榜图片解析与下载--，每次查询Num张，Score超过2k才进行下载
     * @param mode  daily man daily_r18
     * @param num  每次查询数量
     * @return
     * @throws Exception
     */
    public String rankImage(String mode, int     num, int times) throws Exception {
        if(mode == null||mode == ""){
            mode = "daily";
        }
        if(num < 20){
            num = 60;
        }
        //设定请求路径
        String url = apiBaseUrl +"?type=rank&content=illust&mode="+mode+"&per_page="+num+"&page="+times;
        return  RankImageGet(url,num);

    }

    /**
     * V1API
     * search图片下载与解析
     * @param searchName
     * @param period
     * @param num
     * @param times
     * @return
     * @throws Exception
     */
    public String searchImage(String searchName, String period, int num, int times)throws Exception{
        if(num <20){
            num = 50;
        }
        if(searchName == ""|| searchName == null){
            searchName = "R-18";
        }
        String url =  apiBaseUrl +"?type=search&word="+searchName+"&mode=tag&order=asc&period="+period+"&per_page="+num+"&page="+times;
        return  SearchImageGet(url,num);
    }


    /**
     * 直接从Pixiv抓取Rank内容
     * @param mode
     * @param date
     * @param page
     * @return
     */
    public String rankImg(String mode,String date,String page,String cookies) throws Exception {


        String url ="https://www.pixiv.net/ranking.php?mode="+mode+"&page="+page+"&p="+page+"&date="+date+"&format=json";
        String result = rankCrawler(url,50,cookies);
        return "搜索类型："+mode+",\n搜索日期："+date+",\n第"+page+"页。\n"+result;
    }


    /**
     * 使用Pixiv的Search抓图
     * @param keyword 关键词
     * @param order 排序方式 结果_d表示倒数
     * @param page 页码 每页49-50
     * @param mode 年龄范围
     * @param cookies 受欢迎内容需要cookies
     * @return
     */
    public String searchImg(String keyword,String order,String page,String mode,String cookies) throws Exception {
        String url = "https://www.pixiv.net/ajax/search/artworks/"+keyword+"?word="+keyword+"&order="+order+"&mode="+mode+"&p="+page+"&s_mode=s_tag";
        String result = searchCrawler(url,cookies);

        return result;
    }


    /**
     * 抓取Pixiv作者作品
     * @param pid
     * @return
     * @throws Exception
     */
    public String searchUserImg(String pid,String cookie) throws Exception {
        String url = "https://www.pixiv.net/ajax/user/"+pid+"/profile/all";
        String result = userImgCrawler(pid,url,cookie);

        return result;
    }


    /**
     * 抓取pixiv指定用户的收藏夹
     * @param pid
     * @param startIndex
     * @return
     */
    public String searchUserBookmark(String pid,String startIndex,String cookies) throws Exception {
        String result = "";
        String url = "https://www.pixiv.net/ajax/user/"+pid+"/illusts/bookmarks?tag=&offset="+startIndex+"&limit=48&rest=show";
        result = userBookmarkCrawler(url,cookies);
        return result;
    }




}
