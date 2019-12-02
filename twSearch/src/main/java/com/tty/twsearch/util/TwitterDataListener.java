package com.tty.twsearch.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.tty.twsearch.mapper.TwitterMapper;
import com.tty.twsearch.pojo.TwitterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-11-30 18:39
 */
public class TwitterDataListener extends AnalysisEventListener<TwitterData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterDataListener.class);
    private static final int BATCH_COUNT = 5;
    private List<TwitterData> list = new ArrayList<TwitterData>();
    private TwitterMapper twitterMapper;

    public TwitterDataListener(TwitterMapper twitterMapper) {
        this.twitterMapper = twitterMapper;
    }

    @Override
    public void invoke(TwitterData twitterData, AnalysisContext analysisContext) {
        LOGGER.info("One line parsed:{}", JSON.toJSONString(twitterData));
        list.add(twitterData);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("All data parsed!");
    }

    private void saveData() {
        LOGGER.info("The dataset size is {}, starting storing to database!", list.size());
        twitterMapper.saveList(list);
        LOGGER.info("All data stored！");
    }
}
