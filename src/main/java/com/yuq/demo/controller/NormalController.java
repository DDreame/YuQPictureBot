package com.yuq.demo.controller;


import com.IceCreamQAQ.YuQ.YuQ;
import com.IceCreamQAQ.YuQ.annotation.*;
import com.IceCreamQAQ.YuQ.entity.Message;
import com.yuq.demo.entity.PictureList;
import com.yuq.demo.service.SendImage;
import com.yuq.demo.util.DataPoor;
import java.io.IOException;
import java.util.Date;

import static com.yuq.demo.TestEventHandler.*;

/**
 * @author x8140
 */
@GroupController
public class NormalController {
    @Inject
    private YuQ yuQ;

    private SendImage sendImage = new SendImage();

    @Action("就这")
    public String SendNormal(Long qq,Long group) throws Exception {
        String fileName = sendImage.returnSpecialImage("normal",personPictureLimit.get(group).getNormalPictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]\n测试中，版本0.3.4";
    }
    @Action("就这？")
    public String SendNormal1(Long qq,Long group) throws Exception {
        String fileName = sendImage.returnSpecialImage("normal",personPictureLimit.get(group).getNormalPictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]\n测试中，版本0.3.4";
    }
    @Action("独白大剧场")
    public String SendNormal2(Long qq,Long group) throws Exception {
        String fileName = sendImage.returnSpecialImage("normal",personPictureLimit.get(group).getNormalPictureList());
        return "[CQ:image,file=pixivimg/"+fileName+"]\n测试中，版本0.3.4";
    }
    @Action("十连发")
    public String returnTenNormalImg(Long group) throws IOException {
        Date date = new Date(System.currentTimeMillis()-tenPicture);
        PictureList qqInfo = personPictureLimit.get(group);
        if(qqInfo!= null){
            if(qqInfo.getNormalDate().before(date)){
                date = new Date(System.currentTimeMillis());
                qqInfo.setNormalDate(date);
                for(int i = 0;i< 10;i++) {
                    String fileName = sendImage.returnSpecialImage("normal",qqInfo.getNormalPictureList());
                    yuQ.sendMessage(new Message.Builder("[CQ:image,file=pixivimg/" + fileName + "]").setGroup(group).build());
                }
            }else {
                String datePoor = DataPoor.getDatePoor(qqInfo.getNormalDate(),date);
                yuQ.sendMessage(new Message.Builder("尚不可使用该功能，距离可使用还有"+datePoor).setGroup(group).build());
            }
        }
        return "由于图片过大、服务器网络较差等原因，部分图片可能无法发送成功。";
    }



}
