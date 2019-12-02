package com.tty.twsearch.service;

import com.tty.twsearch.BaseTest;
import com.tty.twsearch.pojo.TwitterData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BucketSortImplTest extends BaseTest {

    @Autowired
    BucketSort bucketSort;

    @Test
    void topK() {
    }

    @Test
    void topKInternal() {
        List<TwitterData> list = bucketSort.topKInternal(10, new String[]{"me","answer"});
        for (TwitterData tw : list){
            System.out.println(tw.getTwString());
        }
    }

    @Test
    void topKTemp() {
    }
}