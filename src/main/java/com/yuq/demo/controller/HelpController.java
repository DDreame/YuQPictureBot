package com.yuq.demo.controller;


import com.IceCreamQAQ.YuQ.YuQ;
import com.IceCreamQAQ.YuQ.annotation.*;
import com.IceCreamQAQ.YuQ.entity.Message;
import com.yuq.demo.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;

import static com.yuq.demo.TestEventHandler.*;

@GroupController
@PrivateController
public class HelpController {

    @Inject
    private YuQ yuQ;
    @Action("help")
    public String returnHelp(Long qq) throws Exception{


        return "flash刷新图片缓存。\nall可以查看所有图片数与剩余未看图片数（当前缓存）。\n" +
                "由于图片过大、服务器网络较差等原因，部分图片可能无法发送成功。\n普通口令有：独白大剧场，就这。\nNSFW口令有qwe，gkd，我还要。\n十连发口令为十连发、色图十连发。\n所有内容源自pixiv请尊重原创。\n" +
                "如有意见请使用：bug+空格+意见。如：\nbug 我想要指定画师的图。";
    }

    @Action("bug")
    public String returnBug(Long qq, @PathVar(1)String bug) throws Exception{
        yuQ.sendMessage(new Message.Builder(bug+"\nfromQQ:"+qq).setQQ(adminQQ).build());
        return "发送成功，请等待反馈。";
    }


    @Action("all")
    public String returnAllNum(Long qq,Long group){
        if(group!= null){
            return "总计"+allPictureNum+"\n剩余："+personPictureLimit.get(group).getNormalPictureList().size();
        }else {
            return "总计"+allPictureNum+"\n剩余：Norml:"+personPictureLimit.get(qq).getNormalPictureList().size()+
                    "\nNSFW:"+personPictureLimit.get(qq).getR18PictureList().size();
        }
    }

    @Action("flash")
    public String returnNewList(Long qq,Long group) throws IOException {
        if(group==null||group==0){
            ArrayList<String> pictureList = new ArrayList<>();
            ArrayList<String> pictureList2 = new ArrayList<>();
            FileUtil.readfile(pictureList, "./data/image/pixivimg/normal");
            FileUtil.readfile(pictureList2, "./data/image/pixivimg/r18");
            personPictureLimit.get(qq).setNormalPictureList(pictureList);
            personPictureLimit.get(qq).setR18PictureList(pictureList2);
            int allnum = pictureList.size()+pictureList2.size();
            return "总计"+allnum+"\n剩余：Norml:"+personPictureLimit.get(qq).getNormalPictureList().size()+
                    "\nNSFW:"+personPictureLimit.get(qq).getR18PictureList().size();
        }else {
            ArrayList<String> pictureList = new ArrayList<>();
            FileUtil.readfile(pictureList, "./data/image/pixivimg/normal");
            personPictureLimit.get(group).setNormalPictureList(pictureList);
            return "总计"+pictureList.size()+"\n剩余："+personPictureLimit.get(group).getNormalPictureList().size();
        }
    }
}



