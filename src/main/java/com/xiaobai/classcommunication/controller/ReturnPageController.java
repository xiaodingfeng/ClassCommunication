package com.xiaobai.classcommunication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ReturnPageController {
    @RequestMapping("/user/loginhtml")
    public String loginhtml(){
        return "/user/login";
    }
    @RequestMapping("/user/reghtml")
    public String reghtml(){
        return "/user/reg";
    }
    @RequestMapping("/user/sethtml")
    public String sethtml(){
        return "/user/set";
    }
    @RequestMapping("/user/messagehtml")
    public String messagehtml(){
        return "/user/message";
    }
    @RequestMapping("/user/homehtml")
    public String homehtml(){
        return "/user/home";
    }
    @RequestMapping("/user/forgethtml")
    public String forgethtml(){
        return "/user/forget";
    }
    @GetMapping("/jie/indexhtml")
    public String indexhtml()  {
        return "/jie/index";
    }
    @RequestMapping("/jie/detailhtml")
    public String detailhtml(){
        return "/jie/detail";
    }

    /**
     * 添加文章页面
     * @return
     */
    @RequestMapping("/jie/add")
    public String addPage(){
        return "/jie/add";
    }

    @RequestMapping("/contact/writer")
    public String contact(){
        System.out.println("进来了");
        return "/other/contact";
    }

}
