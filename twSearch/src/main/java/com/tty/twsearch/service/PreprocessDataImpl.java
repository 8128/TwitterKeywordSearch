package com.tty.twsearch.service;

import com.tty.twsearch.mapper.TwitterMapper;
import com.tty.twsearch.pojo.TwitterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-12-08 23:40
 */
@Service
public class PreprocessDataImpl implements PreprocessData {

    @Autowired
    TwitterMapper twitterMapper;

    @Override
    public List<TwitterData> preprocess(String str) {
        List<TwitterData> list = null;
        if ("external".equals(str)){
            list = twitterMapper.getAllTemp();
        } else if ("internal".equals(str)){
            list = twitterMapper.getAll();
        }
        for (TwitterData twitterData : list) {
            twitterData.setSplitedString();
        }
        return list;
    }
}
