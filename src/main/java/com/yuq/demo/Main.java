package com.yuq.demo;

import org.meowy.cqp.jcq.entity.CQDebug;
import org.meowy.cqp.jcq.entity.CoolQ;

public class Main {

    public static void main(String[] args) {
        CoolQ cq = new CQDebug();
        Index index = new Index(cq);
        index.startup();

        index.privateMsg(1, 666, 814061923L, "add 927891735", 1);
  //      index.privateMsg(1, 666, 814061923L, "rank daily 20200312 1", 1);
  //     index.privateMsg(1, 666, 814061923L, "search 黒スト popular_male_d 1 r18", 1);
   //     index.privateMsg(1, 666, 814061923L, "日榜 daily_r18 30", 1);

      //  index.privateMsg(1, 666, 814061923L, "bookmark 3259336 48", 1);

        index.privateMsg(1, 666, 814061923L, "色图十连发", 1);
        index.privateMsg(1, 666, 123456789L, "色图十连发", 2);
//        index.groupMsg(1, 666, 1234567L, 123456789L, null, "十连发", 1);
//        index.groupMsg(1,667,123456L,1234567890L,null,"a b 菜单2",1);
    }

}
