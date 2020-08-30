package com.xiaobai.classcommunication.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.classcommunication.bean.User;
import com.xiaobai.classcommunication.service.MailService;
import com.xiaobai.classcommunication.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

@Controller
public class LoginController {
    @Autowired
    UserSevice userSevice;
    @Autowired
    MailService mailService;
    @RequestMapping("/")
    public String index(HttpServletResponse response){
        return "index";
    }

    String Code="";

    @PostMapping("/user/register")
    public void register(HttpServletResponse response, HttpServletRequest request, User user) throws IOException {
        String code=request.getParameter("code");
        JSONObject object=new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        if (code.equals(Code)) {
            if (userSevice.CheckEmail(user) == 0) {
                user.setTime(new Date());
                if (userSevice.InsertUser(user) != 0) {
                    object.put("status", 0);
                    object.put("LoginUser", user.getEmail());
                    object.put("LoginRole", user.getRole());
                    object.put("msg", "注册成功");
                    object.put("action", "/");
                }
                else
                {
                    object.put("status", 1);
                    object.put("msg", "注册失败！");
                    object.put("action", "error");
                }
            }
            else{
                object.put("status", 1);
                object.put("msg", "邮箱已存在！");
                object.put("action", "error");
            }
        }
        else{
            object.put("status", 1);
            object.put("msg", "验证码错误！");
            object.put("action", "error");
        }
        out.print(object);
    }
    @PostMapping("/user/login")
    public void login(HttpServletResponse response, User user) throws IOException {
        JSONObject object=new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        if(userSevice.LoginCheck(user)!=0) {
            object.put("status", 0);
            object.put("LoginUser", user.getEmail());
            object.put("LoginRole", user.getRole());
            object.put("msg", "登陆成功");
            object.put("action", "/");
        }
        else{
            object.put("status", 1);
            object.put("msg", "登陆失败请检查账户密码是否正确！");
            object.put("action", "/");
        }
        System.out.println(object);
        out.print(object);
    }
    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        System.out.println("进来了");
        session.removeAttribute("LoginUser");
        session.removeAttribute("LoginUserRole");
        return "redirect:/";

    }
    @PostMapping("/user/Verification")
    public void Verification(HttpServletResponse response ,HttpServletRequest request) throws IOException {
        String email = request.getParameter("email");
        JSONObject object=new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        int newNum = (int)((Math.random()*9+1)*100000);
        Code=String.valueOf(newNum);
        System.out.println(Code);
        if (mailService.sendSimpleMail(email,"小狗论坛","验证码："+newNum))
            object.put("status", 0);
        else
            object.put("status", 1);
        System.out.println(object);
        out.print(object);
    }
}
