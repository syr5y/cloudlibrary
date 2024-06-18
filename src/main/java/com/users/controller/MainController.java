package com.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    //    后台主页
    @RequestMapping("/tomain")
    public String getTomain(){
        return "main";
    }
    //登录页面
    @RequestMapping("/tologin")
    public String getTologin(){
        return "login";
    }
    //用户信息
    @RequestMapping("/touser")
    public String getTouser(){
        return "user";
    }
}
