package com.tty.twsearch.util;

import java.util.HashSet;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-12-01 23:11
 */
public class Similarity {
    public static int returnSimilarity(String[] twitter, HashSet<String> hs){
        int ans = 0;
        for (String str : twitter) {
            if (hs.contains(str)) {
                ans++;
            }
        }
        return ans;
    }
}
