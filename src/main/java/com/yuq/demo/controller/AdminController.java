package com.yuq.demo.controller;


import com.IceCreamQAQ.YuQ.YuQ;
import com.IceCreamQAQ.YuQ.annotation.*;
import com.IceCreamQAQ.YuQ.entity.Message;
import com.yuq.demo.entity.PictureList;
import com.yuq.demo.service.GetImage;
import com.yuq.demo.util.FileUtil;
import com.yuq.demo.util.ZipUtils;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.entity.Friend;
import org.meowy.cqp.jcq.entity.Group;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yuq.demo.TestEventHandler.*;
import static com.yuq.demo.controller.ControlController.controlQQ;


@PrivateController
public class AdminController {



    @Inject
    private YuQ yuQ;

    @Inject
    private CoolQ coolQ;
    private GetImage getImage = new GetImage();
    int[] times ={1,1,1,1,1};
    @Before
    public void isAdmin(Long qq) throws Message {
        if(!qq.equals(adminQQ)){
                yuQ.sendMessage(new Message.Builder("AdminController Use Error From:"+qq).setQQ(adminQQ).build());
                throw new Message.Builder("你没有使用该命令的权限！").build();
        }
    }

    @Action("更新")
    public String downloadAll(Long qq){

        String result1 = null;
        String result2 = null;
        String result3 = null;
        try {
            result1 = getImage.searchImage("尻神様","week",100,times[2]);
            times[2]++;
            if(times[2]>5){
                times[2]=1;
            }

            result2 = getImage.searchImage("極上の乳","week",100,times[3]);
            times[3]++;
            if(times[3]>5){
                times[3]=1;
            }
            result3 = getImage.searchImage("黒スト","week",100,times[4]);
            times[4]++;
            if(times[4]>5){
                times[4]=1;
            }
        } catch (Exception e) {
            yuQ.sendMessage(new Message.Builder("AdminController update Error").setQQ(adminQQ).build());
        }

        return "pi：\n"+result1+"\nru："+result2+"\nhei："+result3;

    }


    @Action("日榜")
    public String download(Long qq, @PathVar(value = 1)String mode, @PathVar(value = 2,type = PathVar.Type.number) int num,
                                    @PathVar(value = 3,type = PathVar.Type.number) int times){

        String result = null;
        try {
            result = getImage.rankImage(mode,num,times);
        } catch (Exception e) {
            yuQ.sendMessage(new Message.Builder("AdminController DailyRank Error").setQQ(adminQQ).build());
        }
        return result;
    }


    @Action("男")
    public String downloadMale(Long qq) throws Exception {
        String result = null;

        result = getImage.rankImage("male",0,times[1]);

        times[1]++;
        if(times[1]>5){
            times[1]=1;
        }
        return result;
    }

    @Action("月更1")
    public String downloadCollage(Long qq) throws Exception {
        String result1 = null;
        String result2 = null;
        String result4 = null;
        //碧蓝航线
        result1 = getImage.searchImage("アズールレーン5000users入り","month",100,1);
        //Fate/GO
        result2 = getImage.searchImage("Fate/GO5000users入り","month",100,1);
        //原创
        result4 = getImage.searchImage("オリジナル10000users入り","month",100,1);
        return "碧蓝：\n"+result1+"Fate："+result2+"\n原创："+result4;
    }

    @Action("月更2")
    public String downloadCollage2(Long qq) throws Exception {
        String result1 = null;
        String result2 = null;
        String result3 = null;
        //少女前线
        result1 = getImage.searchImage("少女前線1000users入り","month",100,1);
        //明日方舟
        result2 = getImage.searchImage("アークナイツ1000users入り","month",100,1);
        //无
        result3 = getImage.searchImage("7500users入り","month",100,1);
        return "少女前线：\n"+result1+"\n明日方舟:"+result2+"\n无:"+result3;
    }
    @Action("月更3")
    public String downloadCollage3(Long qq) throws Exception {
        String result1 = null;
        String result2 = null;
        String result3 = null;
        //少女前线
        result1 = getImage.searchImage("少女前線5000users入り","month",100,1);
        //明日方舟
        result2 = getImage.searchImage("アークナイツ5000users入り","month",100,1);
        //无
        result3 = getImage.searchImage("10000users入り","month",100,1);
        return "少女前线：\n"+result1+"\n明日方舟:"+result2+"\n无:"+result3;
    }




    @Action("Path")
    public String returnPath(Long qq) throws Exception{
        File file = new File("./data/image");
        String fileAbsolutePath = file.getAbsolutePath();
        String filePaht=file.getCanonicalPath();
        return "FILEAbsolutePaht:"+fileAbsolutePath+",\nFileCPath:"+filePaht;
    }

    @Action("list")
    public String returnFriendList() throws Exception{
        allowQQ.clear();
        List<Friend> friendList = coolQ.getFriendList();
        for(int i = 0;i<friendList.size();i++){
            Date date = new Date(System.currentTimeMillis()-tenPicture);
            Date date2 = new Date(System.currentTimeMillis()-tenPicture);
            ArrayList<String> pictureList = new ArrayList<>();
            ArrayList<String> pictureList2 = new ArrayList<>();
            FileUtil.readfile(pictureList, "./data/image/pixivimg/normal");
            FileUtil.readfile(pictureList2, "./data/image/pixivimg/r18");
            if(i ==0){
                allPictureNum=pictureList.size()+pictureList2.size();
            }
            PictureList datePictureLimit = new PictureList(date,date2,pictureList,pictureList2);
            Long qqid = friendList.get(i).getQQId();
            allowQQ.add(qqid);
            personPictureLimit.put(qqid,datePictureLimit);
        }
        List<Group> qGroupList = coolQ.getGroupList();
        for(int i = 0;i<qGroupList.size();i++){
            Date date = new Date(System.currentTimeMillis()-tenPicture);
            Date date2 = new Date(System.currentTimeMillis()-tenPicture);
            ArrayList<String> pictureList = new ArrayList<>();
            ArrayList<String> pictureList2 = new ArrayList<>();
            FileUtil.readfile(pictureList, "./data/image/pixivimg/normal");
            if(i ==0){
                allPictureNum=pictureList.size()+pictureList2.size();
            }
            PictureList datePictureLimit = new PictureList(date,date2,pictureList,pictureList2);
            Long qqid = qGroupList.get(i).getId();
            personPictureLimit.put(qqid,datePictureLimit);
        }
        return allowQQ.toString() + "\n" + personPictureLimit.size();
    }




    @Action("cookie")
    public String checkOutCookie(@PathVar(value = 1)String newcookie){
        cookie = newcookie;
        return cookie.toString();
    }


    @Action("add")
    public String addControlQq(@PathVar(value = 1,type = PathVar.Type.qq)Long addQQ){
        controlQQ.add(addQQ);
        return controlQQ.toString();
    }

    @Action("del")
    public String delControlQq(@PathVar(value = 1,type = PathVar.Type.qq)Long delQQ){
        controlQQ.remove(delQQ);
        return controlQQ.toString();
    }
    @Action("zip")
    public String zipAllFile() throws FileNotFoundException {
        String date=new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
        FileOutputStream fos1= new FileOutputStream(new File("./zip/r18:"+date+".zip"));
        ZipUtils.toZip("./data/image/pixivimg/r18", fos1,true);
        FileOutputStream fos2= new FileOutputStream(new File("./zip/normal:"+date+".zip"));
        ZipUtils.toZip("./data/image/pixivimg/normal", fos1,true);
        FileUtil.deleteFile(new File("./data/image/pixivimg/r18"));
        FileUtil.deleteFile(new File("./data/image/pixivimg/normal"));
        return controlQQ.toString();
    }

}
