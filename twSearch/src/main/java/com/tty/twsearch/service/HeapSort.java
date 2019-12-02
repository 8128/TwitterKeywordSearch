package com.tty.twsearch.service;

import com.tty.twsearch.pojo.TwitterData;

import java.util.List;

public interface HeapSort {

    List<TwitterData> topK(int k, String[] words, List<TwitterData> inputList);

    List<TwitterData> topKInternal(int k, String[] words);

    List<TwitterData> topKTemp(int k, String[] words);
}
