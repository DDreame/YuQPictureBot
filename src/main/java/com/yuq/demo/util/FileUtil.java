package com.yuq.demo.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 文件工具类
 * @author x8140
 */
public class FileUtil {
    static Pattern RankStartPattern = Pattern.compile("/c/240x480/img-master|/c/250x250_80_a2/img-master");
    static Pattern RankEndPattern = Pattern.compile("_p0_master1200|_p0_square1200|_p0_custom1200");
    static Pattern SearchStartPattern = Pattern.compile("");
    static Pattern SearchEndPattern = Pattern.compile("");
    /**
     * 文件名替换
     * @param url
     * @return
     */
    public static String replaceUrl(String url){
        Matcher matcher = RankStartPattern.matcher(url);
        // 将匹配到的非法字符以空替换
        url = matcher.replaceAll("/img-original");
        matcher = RankEndPattern.matcher(url);
        url = matcher.replaceAll("_p0");
        return url;
    }


    /**
     * 文件列表读取
     * @param allImageName
     * @param filepath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<String>  readfile(ArrayList<String> allImageName,String filepath) throws FileNotFoundException, IOException {

        try {
            File file = new File(filepath);

            if (!file.isDirectory()) {
                allImageName.add(file.getName());
            } else if (file.isDirectory()) {

                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "/" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        allImageName.add(readfile.getName());
                    } else if (readfile.isDirectory()) {
                        readfile(allImageName,filepath + "/" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return allImageName;
    }

    /**
     *
     * @param file
     */
    public static void deleteFile(File file) {
        //判断路径是否存在
        if(file.exists()) {
            //boolean isFile():测试此抽象路径名表示的文件是否是一个标准文件。
            if(file.isFile()){
                file.delete();
            }else{
                //不是文件，对于文件夹的操作
                //保存 路径下的所有的文件和文件夹到listFiles数组中
                //listFiles方法：返回file路径下所有文件和文件夹的绝对路径
                File[] listFiles = file.listFiles();
                for (File file2 : listFiles) {
                    /*
                     * 递归作用：由外到内先一层一层删除里面的文件 再从最内层 反过来删除文件夹
                     *    注意：此时的文件夹在上一步的操作之后，里面的文件内容已全部删除
                     *         所以每一层的文件夹都是空的  ==》最后就可以直接删除了
                     */
                    deleteFile(file2);
                }
            }
            file.delete();
        }else {
            System.out.println("该file路径不存在！！");
        }
    }



    public static void main(String[] args) throws IOException {
        ArrayList<String> allImageName = new ArrayList<>();
        allImageName=readfile(allImageName,"./data/image/pixivimg/");
        System.out.println(allImageName);
        allImageName.remove("0fb8369b-fcf6-43e3-aeac-ae59bc9b93511583747164100.jpg");
        System.out.println(allImageName);
    }

}
