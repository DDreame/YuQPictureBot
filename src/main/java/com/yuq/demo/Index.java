package com.yuq.demo;

import com.IceCreamQAQ.YuQ.platform.JCQ.JCQStartBase;
import org.meowy.cqp.jcq.entity.CoolQ;

public class Index extends JCQStartBase {

    public Index(CoolQ CQ) {
        super(CQ);
    }

    @Override
    public String appId() {
        return "com.yuq.demo.index";
    }




}
