package com.yuq.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPoor {

    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;

        long nh = 1000 * 60 * 60;

        long nm = 1000 * 60;

        long ns = 1000;

        // 获得两个时间的毫秒时间差异

        long diff = endDate.getTime() - nowDate.getTime();

        // 计算差多少天

        long day = diff / nd;

        // 计算差多少小时

        long hour = diff % nd / nh;

        // 计算差多少分钟

        long min = diff % nd % nh / nm;

        // 计算差多少秒//输出结果

        long sec = diff % nd % nh % nm / ns;

        return hour + "小时" + min + "分钟" + sec + "秒";
    }

}
