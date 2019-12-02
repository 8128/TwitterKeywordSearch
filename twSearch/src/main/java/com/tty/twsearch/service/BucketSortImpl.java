package com.tty.twsearch.service;

import com.tty.twsearch.mapper.TwitterMapper;
import com.tty.twsearch.pojo.TwitterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.tty.twsearch.util.Similarity.returnSimilarity;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-12-01 23:00
 */
@Service
public class BucketSortImpl implements BucketSort {

    @Autowired
    TwitterMapper twitterMapper;

    @Override
    public List<TwitterData> topK(int k, String[] words, List<TwitterData> inputList) {
        ArrayList<TwitterData>[] lists = new ArrayList[140];
        HashSet<String> hs = new HashSet<>();
        hs.addAll(Arrays.asList(words));
        for (TwitterData tw : inputList) {
            int similarity = returnSimilarity(tw.getTwString(), hs);
            if (lists[similarity] == null) {
                lists[similarity] = new ArrayList<>();
                lists[similarity].add(tw);
            }else {
                lists[similarity].add(tw);
            }
        }
        List<TwitterData> ans = new ArrayList<>();
        int pivot = 139;
        while (ans.size() < k && pivot >= 0) {
            if (lists[pivot] != null) {
                ans.addAll(lists[pivot]);
            }
            pivot--;
        }
        if (ans.size() > k) {
            ans = ans.subList(0,k);
        }
        return ans;
    }

    @Override
    public List<TwitterData> topKInternal(int k, String[] words) {
        return topK(k, words, twitterMapper.getAll());
    }

    @Override
    public List<TwitterData> topKTemp(int k, String[] words) {
        return topK(k, words, twitterMapper.getAllTemp());
    }
}
