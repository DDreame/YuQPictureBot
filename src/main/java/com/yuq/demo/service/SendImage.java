package com.yuq.demo.service;

import com.yuq.demo.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import static com.yuq.demo.TestEventHandler.*;

public class SendImage {


    public String returnSpecialImage(String path, ArrayList<String> imgNameLsit) throws IOException {
        String basePath = path+"/";
        String dirPath = "";
        String filePath = "";
        File file = new File("./data/image/pixivimg/"+basePath);
        String[] imgNameL = file.list();
        int max = imgNameL.length;

        String fileName =null;
        boolean isRemove = false;
        int times=0;
        do{
            times++;
            int index = (int) (max*Math.random());
            if (index ==0){
                index++;
            }
            fileName = imgNameL[index-1];
            File file1 = new File(basePath+dirPath+fileName);
            if(file1.length()>maxFileLenth){
                file1.renameTo(new File("./image/pixiv/" + fileName));
                imgNameLsit.remove(fileName);
            }
            if(imgNameLsit.contains(fileName)){
                imgNameLsit.remove(fileName);
                isRemove =true;
            }
        }while(!isRemove&&times<10);
        filePath=basePath+dirPath+fileName;
        if(imgNameLsit.isEmpty()||times== 10){
            FileUtil.readfile(imgNameLsit, "./data/image/pixivimg/"+basePath);
            filePath=fileName+"]\n刷新了图片缓存。";
        }

        return filePath;
    }
}
