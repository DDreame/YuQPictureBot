package com.yuq.demo.controller;


import com.IceCreamQAQ.YuQ.YuQ;
import com.IceCreamQAQ.YuQ.annotation.*;
import com.IceCreamQAQ.YuQ.entity.Message;
import com.yuq.demo.service.GetImage;

@PrivateController
@GroupController
@Path("search")

public class SearchController {

    @Inject
    private YuQ yuQ;

    private GetImage getImage = new GetImage();



    int[] times ={1,1,1};
    @Before
    public void per(Long qq, Long group) throws Message {
        if (qq != 81061923L)return;
        throw new Message.Builder("你没有使用该命令的权限！").build();

    }

    public String downloadPigu(Long qq,@PathVar(value = 1)String name,@PathVar(value = 2)int times) throws Exception{

        String result = getImage.searchImage(name,"week",100,times);

        return result;
    }



}
