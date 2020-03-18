package com.yuq.demo.entity;

import java.util.ArrayList;
import java.util.Date;

public class PictureList {



    public Date getSpecialDate() {
        return specialDate;
    }

    public void setSpecialDate(Date specialDate) {
        this.specialDate = specialDate;
    }

    public Date getNormalDate() {
        return normalDate;
    }

    public void setNormalDate(Date normalDate) {
        this.normalDate = normalDate;
    }



    Date specialDate;
    Date normalDate;
    ArrayList<String> NormalPictureList;
    ArrayList<String> r18PictureList;

    public PictureList(Date specialDate, Date normalDate, ArrayList<String> normalPictureList, ArrayList<String> r18PictureList) {
        this.specialDate = specialDate;
        this.normalDate = normalDate;
        NormalPictureList = normalPictureList;
        this.r18PictureList = r18PictureList;
    }

    public ArrayList<String> getNormalPictureList() {
        return NormalPictureList;
    }

    public void setNormalPictureList(ArrayList<String> normalPictureList) {
        NormalPictureList = normalPictureList;
    }

    public ArrayList<String> getR18PictureList() {
        return r18PictureList;
    }

    public void setR18PictureList(ArrayList<String> r18PictureList) {
        this.r18PictureList = r18PictureList;
    }
}
