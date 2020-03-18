package com.yuq.demo.controller;


import com.IceCreamQAQ.YuQ.YuQ;
import com.IceCreamQAQ.YuQ.annotation.Action;
import com.IceCreamQAQ.YuQ.annotation.Before;
import com.IceCreamQAQ.YuQ.annotation.Inject;
import com.IceCreamQAQ.YuQ.annotation.PrivateController;
import com.IceCreamQAQ.YuQ.entity.Message;
import com.yuq.demo.entity.PictureList;
import com.yuq.demo.service.SendImage;
import com.yuq.demo.util.DataPoor;
import org.meowy.cqp.jcq.entity.CoolQ;
import sun.dc.pr.PRError;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import static com.yuq.demo.TestEventHandler.*;


@PrivateController
public class AdultController {

    private SendImage sendImage = new SendImage();

    @Inject
    private YuQ yuQ;

    @Inject
    private CoolQ coolQ;



    @Before
    public void isFriend(Long qq) throws Message {
        boolean isfriend =false;
        for(int i = 0; i < allowQQ.size();i++){
            if(qq.equals(allowQQ.get(i))){
                isfriend=true;
            }
        }
        if(isfriend == false){
            throw new Message.Builder("该命令不存在").build();
        }
    }


    @Action("色图十连发")
    public String returnTenImg(Long qq) throws IOException {
        Date date = new Date(System.currentTimeMillis()-tenPicture);
        PictureList qqInfo = personPictureLimit.get(qq);
        if(qqInfo!= null){
            if(qqInfo.getSpecialDate().before(date)){
                date = new Date(System.currentTimeMillis());
                qqInfo.setSpecialDate(date);
                for(int i = 0;i< 10;i++) {
                    String fileName = sendImage.returnSpecialImage("r18",qqInfo.getR18PictureList());
                    yuQ.sendMessage(new Message.Builder("[CQ:image,file=pixivimg/" + fileName + "]").setQQ(qq).build());
                }
                personPictureLimit.put(qq,qqInfo);
            }else {
                String datePoor = DataPoor.getDatePoor(qqInfo.getSpecialDate(),date);
                yuQ.sendMessage(new Message.Builder("尚不可使用该功能，距离可使用还有"+datePoor).setQQ(qq).build());
            }
        }
        return "由于图片过大、服务器网络较差等原因，部分图片可能无法发送成功。";
    }


    @Action("十连发")
    public String returnTenNormalImg(Long qq) throws IOException {
        Date date = new Date(System.currentTimeMillis()-tenPicture);
        PictureList qqInfo = personPictureLimit.get(qq);
        if(qqInfo!= null){
            if(qqInfo.getNormalDate().before(date)){
                date = new Date(System.currentTimeMillis());
                qqInfo.setNormalDate(date);
                for(int i = 0;i< 10;i++) {
                    String fileName = sendImage.returnSpecialImage("normal",qqInfo.getNormalPictureList());
                    yuQ.sendMessage(new Message.Builder("[CQ:image,file=pixivimg/" + fileName + "]").setQQ(qq).build());
                }
            }else {
                String datePoor = DataPoor.getDatePoor(qqInfo.getNormalDate(),date);
                yuQ.sendMessage(new Message.Builder("尚不可使用该功能，距离可使用还有"+datePoor).setQQ(qq).build());
            }
        }
        return "由于图片过大、服务器网络较差等原因，部分图片可能无法发送成功。";
    }






    @Action("我还要")
    public String returnImg1(Long qq) throws IOException {

        String fileName = sendImage.returnSpecialImage("r18",personPictureLimit.get(qq).getR18PictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]测试中，版本0.3.4";
    }

    @Action("gkd")
    public String returnImg2(Long qq) throws IOException {
        String fileName = sendImage.returnSpecialImage("r18",personPictureLimit.get(qq).getR18PictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]测试中，版本0.3.4";
    }
    @Action("qwe")
    public String returnImg3(Long qq) throws IOException {
        String fileName = sendImage.returnSpecialImage("r18",personPictureLimit.get(qq).getR18PictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]测试中，版本0.3.4";
    }

    @Action("就这")
    public String SendNomal1(Long qq,Long group) throws IOException {
        String fileName = sendImage.returnSpecialImage("normal",personPictureLimit.get(qq).getR18PictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]测试中，版本0.3.4";
    }







    @Action("就这？")
    public String SendNomal2(Long qq,Long group) throws IOException {
        String fileName = sendImage.returnSpecialImage("normal",personPictureLimit.get(qq).getNormalPictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]测试中，版本0.3.4";
    }
    @Action("独白大剧场")
    public String SendNomal3(Long qq,Long group) throws IOException {
        String fileName = sendImage.returnSpecialImage("normal",personPictureLimit.get(qq).getNormalPictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]测试中，版本0.3.4";
    }

}
