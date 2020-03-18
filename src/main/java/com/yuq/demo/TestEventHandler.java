package com.yuq.demo;

import com.IceCreamQAQ.YuQ.YuQ;
import com.IceCreamQAQ.YuQ.annotation.Event;
import com.IceCreamQAQ.YuQ.annotation.EventHandler;
import com.IceCreamQAQ.YuQ.annotation.Inject;
import com.IceCreamQAQ.YuQ.entity.Message;
import com.IceCreamQAQ.YuQ.event.EventBus;
import com.IceCreamQAQ.YuQ.event.events.*;
import com.yuq.demo.entity.PictureList;
import com.yuq.demo.util.FileUtil;
import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.entity.Friend;
import org.meowy.cqp.jcq.entity.Group;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.yuq.demo.controller.ControlController.controlQQ;

@EventHandler
public class TestEventHandler {

    @Inject
    private EventBus eventBus;

    @Inject
    private CoolQ coolQ;

    @Inject
    private YuQ yuQ;
    public static long maxFileLenth = 10*1024*1024;
    public static String cookie = "";
    public  static ArrayList<Long> allowQQ = new ArrayList<>();
    public static int allPictureNum = 0;
    public  static Long tenPicture = Long.valueOf(1000*60*15);
    public  static HashMap<Long, PictureList> personPictureLimit= new HashMap<>();
    public static Long adminQQ =0L;
    @Event
    public void AppEnableEvent(AppStatusChangedEvent event) throws IOException, InterruptedException {
        Thread.sleep(3000);
        System.out.println("--------------------------------------------------------------");
        System.out.println("AppEnableEvent");


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
        System.out.println(allowQQ.toString());
        System.out.println(personPictureLimit.toString());
        System.out.println("AppEnableEvent End!");
        System.out.println("--------------------------------------------------------------");

    }



    @Event
    public void addFriend(FriendAddEvent event) throws IOException {

         allowQQ.add(event.getQq());
        Date date = new Date(System.currentTimeMillis()-tenPicture);
        Date date2 = new Date(System.currentTimeMillis()-tenPicture);
        ArrayList<String> pictureList = new ArrayList<>();
        ArrayList<String> pictureList2 = new ArrayList<>();
        FileUtil.readfile(pictureList, "./data/image/pixivimg/normal");
        FileUtil.readfile(pictureList2, "./data/image/pixivimg/r18");
        PictureList datePictureLimit = new PictureList(date,date2,pictureList,pictureList2);
        personPictureLimit.put(event.getQq(),datePictureLimit);
        yuQ.sendMessage(new Message.Builder("已经加入列表，可以使用命令").setQQ(event.getQq()).build());
        yuQ.sendMessage(new Message.Builder(event.getQq()+"已经加入列表，可以使用命令").setQQ(adminQQ).build());
    }

    @Event
    public void friendRequest(FriendRequestEvent event){

        event.setAccept(true);
        yuQ.sendMessage(new Message.Builder( event.getQq()+":"+event.getMsg()).setQQ(adminQQ).build());
    }


//    @Event(weight = Event.Weight.height)
//    public void onMessage(OnMessageEvent event){
//        System.out.println("--------------------------------------------------------------");
//        System.out.println("收到了消息！");
//        System.out.println(event.getContext().getMessage().toString());
//
//        event.setCancel(true);
//
//        eventBus.post(new TestEvent());
//
//        System.out.println("--------------------------------------------------------------");
//    }




    @Event
    public void onGroupMessage(OnGroupMessageEvent event){
        System.out.println("--------------------------------------------------------------");
        System.out.println("收到了群聊消息！");
        System.out.println(event.getContext().getMessage().toString());
    }

    @Event
    public void onPrivateMessage(OnPrivateMessageEvent event){
        System.out.println("--------------------------------------------------------------");
        System.out.println("收到了私聊消息！");
        System.out.println(event.getContext().getMessage().toString());
    }

}
