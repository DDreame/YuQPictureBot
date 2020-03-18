package com.yuq.demo.controller;

import com.IceCreamQAQ.YuQ.YuQ;
import com.IceCreamQAQ.YuQ.annotation.*;
import com.IceCreamQAQ.YuQ.entity.Message;

import static com.yuq.demo.TestEventHandler.adminQQ;

@PrivateController
public class TestController {



    @Before
    public void per(Long qq) throws Message {
        System.out.println("--------------------------------------------------------------");
        System.out.println("经过 拦截器 per！");
        if (adminQQ!=0L ){

            throw new Message.Builder("AdminQQ已确认为"+adminQQ).build();
        }
    }
    @Action("admin")
    public String menu(Long qq) throws Exception {
        adminQQ=qq;

        return "adminQQ确认成功";
    }
}
