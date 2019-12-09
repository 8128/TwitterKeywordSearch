package com.tty.twsearch.service;

import com.tty.twsearch.pojo.TwitterData;

import java.util.List;

public interface PreprocessData {

    List<TwitterData> preprocess(String str);
}
