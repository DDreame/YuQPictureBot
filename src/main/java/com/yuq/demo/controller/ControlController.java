package com.yuq.demo.controller;


import com.IceCreamQAQ.YuQ.annotation.Action;
import com.IceCreamQAQ.YuQ.annotation.Before;
import com.IceCreamQAQ.YuQ.annotation.PathVar;
import com.IceCreamQAQ.YuQ.annotation.PrivateController;
import com.IceCreamQAQ.YuQ.entity.Message;
import com.yuq.demo.service.GetImage;
import java.util.ArrayList;
import static com.yuq.demo.TestEventHandler.*;
/**
 * @author x8140
 */
@PrivateController
public class ControlController {


    public static ArrayList<Long> controlQQ = new ArrayList<>();
    private GetImage getImage = new GetImage();
    @Before
    public void per(Long qq)throws Message {
        boolean qwe =false;
        for(int i = 0;i<controlQQ.size();i++){
            if(qq.equals(controlQQ.get(i))){
                qwe =true;
            }
        }
        if(!qwe) {
            throw new Message.Builder("你没有使用该命令的权限！").build();
        }
    }


    @Action("123")
    public String returnHelp(Long qq) throws Exception{


        return "可以进行机器人图片更新\nQQ："+adminQQ+"拥有添加权限\n" +
                "口令有rank（排行榜每页48-50）：\n" +
                "rank 排行榜类型 日期 页数\n" +
                "search（关键字搜索）：\n" +
                "search 关键词（最好日文） 排序模式 页数  年龄范围\n" +
                "bookmark （指定用户收藏夹，每次抓取48张）\n" +
                "bookmark 用户id 抓取开始位置\n" +
                "请尽量保证抓取质量，图片所有人共享\n" +
                "如有问题请找"+adminQQ;
    }
    @Action("rank")
    public String downloadPixivRank(@PathVar(value = 1)String mode, @PathVar(value = 2)String date,
                                    @PathVar(value = 3)String page) throws Exception {
        String result = null;

        result = getImage.rankImg(mode,date,page,cookie);

        return result;
    }

    @Action("search")
    public String downloadPixivSearch( @PathVar(value = 1)String keyword,@PathVar(value = 2)String order,
                                       @PathVar(value = 3)String page,@PathVar(value = 4)String mode) throws Exception {
        String result = null;

        result = getImage.searchImg(keyword,order,page,mode,cookie);

        return result;
    }

    @Action("bookmark")
    public String downloadPixivBookmark(@PathVar(value = 1)String pid,@PathVar(value = 2)String startindex) throws Exception {
        String result = null;

        result = getImage.searchUserBookmark(pid,startindex,cookie);

        return result;
    }
    @Action("author")
    public String downloadPixivAuhtor(@PathVar(value = 1)String pid) throws Exception {
        String result = null;
        result = getImage.searchUserImg(pid,cookie);

        return result;
    }
}
