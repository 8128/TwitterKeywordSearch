package com.tty.twsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-11-30 18:52
 */
@Controller
public class MainController {

    @RequestMapping("")
    public String index(){
        return "/frontend/index";
    }

}
