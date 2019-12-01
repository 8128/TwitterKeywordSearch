package com.tty.twsearch.csvutil;

import com.alibaba.excel.EasyExcel;
import com.tty.twsearch.dao.CsvDAO;
import com.tty.twsearch.pojo.TwitterData;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-11-30 18:01
 */
public class ReadCSV {

    @Autowired
    private CsvDAO csvDAO;

    public void readCSV (String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, TwitterData.class, new TwitterDataListener(csvDAO)).sheet().doRead();
    }
}
