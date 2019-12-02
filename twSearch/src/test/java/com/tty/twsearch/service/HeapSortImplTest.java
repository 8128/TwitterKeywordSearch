package com.tty.twsearch.service;

import com.tty.twsearch.BaseTest;
import com.tty.twsearch.pojo.TwitterData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortImplTest extends BaseTest {

    @Autowired
    HeapSort heapSort;

    @Test
    void topK() {
        List<TwitterData> list = heapSort.topKInternal(10, new String[]{"me","answer"});
        for (TwitterData tw : list){
            System.out.println(tw.getTwString());
        }
    }
}