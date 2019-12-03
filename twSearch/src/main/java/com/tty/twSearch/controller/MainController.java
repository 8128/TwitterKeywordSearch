package com.tty.twsearch.controller;

import com.tty.twsearch.service.BucketSort;
import com.tty.twsearch.service.HeapSort;
import com.tty.twsearch.service.ReadCsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-11-30 18:52
 */
@Controller
public class MainController {

    @Autowired
    BucketSort bucketSort;

    @Autowired
    HeapSort heapSort;

    @Autowired
    ReadCsv readCsv;

    @RequestMapping("/")
    public String mainPage(){
        return "index";
    }

    @RequestMapping("/search")
    public String searchPage() {
        return "search";
    }

    @RequestMapping("/result")
    @ResponseBody
    public String resultPage(Map<String, String> map) {
        return map.toString();
    }

    @RequestMapping("/searchin")
    public String inSearchPage(@RequestParam("kin") String k, @RequestParam("keywordin")String keywords) {
        HashMap<String, String> hm = new HashMap<>();
        String[] words = keywords.split(" ");
        long startTime = System.currentTimeMillis();
        bucketSort.topKInternal(Integer.valueOf(k), words);
        long endTime = System.currentTimeMillis();
        hm.put("bucketSortTime", String.valueOf(endTime - startTime));
        startTime = System.currentTimeMillis();
        heapSort.topKInternal(Integer.valueOf(k), words);
        endTime = System.currentTimeMillis();
        hm.put("heapSortTime", String.valueOf(endTime - startTime));
        return resultPage(hm);
    }

    @RequestMapping("/searchex")
    public String exSearchPage (@RequestParam("file") MultipartFile file, @RequestParam("kex") String k, @RequestParam("keywordsex") String keywords) {
        HashMap<String, String> hm = new HashMap<>();
        if (file.isEmpty()) {
            return "error";
        }
        String filename = file.getOriginalFilename();
        String filePath = "classpath:uploadfile";
        String[] words = keywords.split(" ");
        File dest = new File(filePath + filename);
        try {
            file.transferTo(dest);
            readCsv.readCsv(dest.getPath());
            long startTime = System.currentTimeMillis();
            bucketSort.topKTemp(Integer.valueOf(k), words);
            long endTime = System.currentTimeMillis();
            hm.put("bucketSortTime", String.valueOf(endTime - startTime));
            startTime = System.currentTimeMillis();
            heapSort.topKTemp(Integer.valueOf(k), words);
            endTime = System.currentTimeMillis();
            hm.put("heapSortTime", String.valueOf(endTime - startTime));
            return resultPage(hm);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

    }
}
