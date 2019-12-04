package com.tty.twsearch.controller;

import com.tty.twsearch.pojo.TwitterData;
import com.tty.twsearch.service.BucketSort;
import com.tty.twsearch.service.HeapSort;
import com.tty.twsearch.service.ReadCsv;
import com.tty.twsearch.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping("/index")
    public String indexPage(){
        return "index";
    }

    @RequestMapping("/search")
    public String searchPage() {
        return "search";
    }

    @RequestMapping("/contact")
    public String contactPage(){
        return "contact";
    }

    @RequestMapping("/thankyou")
    public String thankyouPage(){
        return "thankyou";
    }

    @RequestMapping("/result")
    public String resultPage(Model model, Map<String, Object> map) {
        model.addAllAttributes(map);
        return "result";
    }

    @PostMapping("/searchin")
    public String inSearchPage(Model model, @RequestParam("kin") String k, @RequestParam("keywordsin")String keywords) {
        HashMap<String, Object> hm = new HashMap<>();
        String[] words = keywords.split(" ");
        long startTime = System.currentTimeMillis();
        List<TwitterData> bucketList = bucketSort.topKInternal(Integer.valueOf(k), words);
        long endTime = System.currentTimeMillis();
        hm.put("bucketSortTime", String.valueOf(endTime - startTime) + "ms");
        hm.put("bucketList", bucketList);
        startTime = System.currentTimeMillis();
        List<TwitterData> heapList = heapSort.topKInternal(Integer.valueOf(k), words);
        endTime = System.currentTimeMillis();
        hm.put("heapSortTime", String.valueOf(endTime - startTime) + "ms");
        hm.put("heapList", heapList);
        return resultPage(model, hm);
    }

    @PostMapping("/searchex")
    public String exSearchPage (Model model, @RequestParam("file") MultipartFile file, @RequestParam("kex") String k, @RequestParam("keywordsex") String keywords) {
        HashMap<String, Object> hm = new HashMap<>();
        if (file.isEmpty()) {
            return "error";
        }
        String filename = "temp.csv";
        File dir = UploadUtil.getCsvDirFile();
        String[] words = keywords.split(" ");
        try {
            File newFile = new File(dir.getAbsolutePath() + File.separator +filename);
            file.transferTo(newFile);
            readCsv.readCsv(newFile.getAbsolutePath());
            newFile.delete();
            long startTime = System.currentTimeMillis();
            List<TwitterData> bucketList = bucketSort.topKTemp(Integer.valueOf(k), words);
            long endTime = System.currentTimeMillis();
            hm.put("bucketSortTime", String.valueOf(endTime - startTime) + "ms");
            hm.put("bucketList", bucketList);
            startTime = System.currentTimeMillis();
            List<TwitterData> heapList = heapSort.topKTemp(Integer.valueOf(k), words);
            endTime = System.currentTimeMillis();
            hm.put("heapSortTime", String.valueOf(endTime - startTime) + "ms");
            hm.put("heapList", heapList);
            return resultPage(model, hm);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

    }
}
