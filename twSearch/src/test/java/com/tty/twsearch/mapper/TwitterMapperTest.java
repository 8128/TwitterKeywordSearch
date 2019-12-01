package com.tty.twsearch.mapper;

import com.tty.twsearch.BaseTest;
import com.tty.twsearch.pojo.TwitterData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TwitterMapperTest extends BaseTest {

    @Autowired
    TwitterMapper twitterMapper;


    @Test
    void getAll() {
        System.out.println(twitterMapper.getAll());
    }

    @Test
    void saveList() {
        List<TwitterData> list = new ArrayList<>();
        TwitterData tw1 = new TwitterData();
        tw1.setTwDate(new Date());
        tw1.setTwString("   ");
        list.add(tw1);
        TwitterData tw2 = new TwitterData();
        tw2.setTwDate(new Date());
        tw2.setTwString("  2 ");
        list.add(tw2);
        twitterMapper.saveListTemp(list);
    }

    @Test
    void deleteAll() {
        twitterMapper.deleteAll();
    }
}