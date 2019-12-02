package com.tty.twsearch.service;

import com.tty.twsearch.mapper.TwitterMapper;
import com.tty.twsearch.pojo.TwitterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tty.twsearch.util.Similarity.returnSimilarity;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-12-01 23:01
 */
@Service
public class HeapSortImpl implements HeapSort {

    @Autowired
    TwitterMapper twitterMapper;

    @Override
    public List<TwitterData> topK(int k, String[] words, List<TwitterData> inputList) {
        HashSet<String> hs = new HashSet<>();
        hs.addAll(Arrays.asList(words));
        //HashMap<TwitterData, Integer> hm = new HashMap<>();
        PriorityQueue<TwitterData> pq = new PriorityQueue<>(new Comparator<TwitterData>() {
            @Override
            public int compare(TwitterData o1, TwitterData o2) {
                /**
                Integer sim1 = hm.getOrDefault(o1, returnSimilarity(o1.getTwString(), hs));
                if (!hm.containsKey(o1)) {
                    hm.put(o1, sim1);
                }
                Integer sim2 = hm.getOrDefault(o2, returnSimilarity(o2.getTwString(), hs));
                if (!hm.containsKey(o2)) {
                    hm.put(o2, sim2);
                }
                 **/
                Integer sim1 = returnSimilarity(o1.getTwString(), hs);
                Integer sim2 = returnSimilarity(o2.getTwString(), hs);
                return sim1.compareTo(sim2);
            }
        });
        for (TwitterData tw : inputList) {
            pq.offer(tw);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        List<TwitterData> ans = new ArrayList<>();
        while (!pq.isEmpty()) {
            ans.add(pq.poll());
            Collections.reverse(ans);
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
