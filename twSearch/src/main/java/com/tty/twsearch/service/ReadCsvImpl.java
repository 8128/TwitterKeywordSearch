package com.tty.twsearch.service;

import com.tty.twsearch.mapper.TwitterMapper;
import com.tty.twsearch.pojo.TwitterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-12-01 05:06
 */
@Service
public class ReadCsvImpl implements ReadCsv{

    @Autowired
    TwitterMapper twitterMapper;

    @Override
    public void readCsv(String fileName){
        twitterMapper.deleteAll();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();
            String line = null;
            List<TwitterData> list = new ArrayList<>();
            while((line=reader.readLine())!=null){
                String item[] = line.split(",");
                TwitterData tw = new TwitterData();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                if (item.length < 2) {
                    continue;
                }
                Date date = dateFormat.parse(item[0]);
                tw.setTwDate(date);
                tw.setTwString(item[1]);
                list.add(tw);
                if (list.size() > 20) {
                    twitterMapper.saveListTemp(list);
                    list = new ArrayList<>();
                }
            }
            if (!list.isEmpty()) {
                twitterMapper.saveListTemp(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
