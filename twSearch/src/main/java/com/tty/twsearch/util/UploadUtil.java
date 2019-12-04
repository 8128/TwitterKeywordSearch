package com.tty.twsearch.util;

import java.io.File;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-12-03 20:51
 */
public class UploadUtil {

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String CSV_PATH_PREFIX = "static/upload/csv";

    public static File getCsvDirFile(){

        // 构建上传文件的存放 "文件夹" 路径
        String fileDirPath = new String("src/main/resources/" + CSV_PATH_PREFIX);

        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }
}
