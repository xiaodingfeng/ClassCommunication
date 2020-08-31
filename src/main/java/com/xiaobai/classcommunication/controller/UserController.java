package com.xiaobai.classcommunication.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.bean.Content;
import com.xiaobai.classcommunication.bean.User;
import com.xiaobai.classcommunication.bean.UserforArt;
import com.xiaobai.classcommunication.service.ArticleService;
import com.xiaobai.classcommunication.service.MailService;
import com.xiaobai.classcommunication.service.UserSevice;
import com.xiaobai.classcommunication.untils.RSAenCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserSevice userSevice;
    @Autowired
    MailService mailService;
    @Autowired
    ArticleService articleService;
    @Autowired
    RSAenCrypt rsAenCrypt;
    @RequestMapping("/")
    public String index(HttpServletResponse response){
        return "index1";
    }

    @RequestMapping("/index")
    public String index1(){
        return "index";
    }
    private Map map =new HashMap<String,Integer>();

    @GetMapping("/user/info")
    public void userinfo(HttpServletResponse response, HttpSession session) throws IOException {
        String email;
        try {
            email= (String) session.getAttribute("LoginUser");
        }catch (Exception e){
            email=null;
        }
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        JSONObject object=new JSONObject();
        if (email==null)
            object.put("status",1);
        else{
            User user=userSevice.GetUser(email);
            Collection<UserforArt> articles=articleService.getArticleByUserID(user.getID());
            object.put("data",user);
            object.put("data1",articles);
            object.put("status",0);
        }
        out.print(object);
    }

    @GetMapping("/user/home")
    public void userhomeinfo(HttpServletResponse response,HttpServletRequest request, HttpSession session) throws IOException {
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        JSONObject object=new JSONObject();
        Integer userid=0;
        try {
            userid = Integer.valueOf(request.getParameter("userid"));
        }catch (Exception e){}
        if (userid==0)
            object.put("status",1);
        else{
            User user=userSevice.GetUserbyID(userid);
            Collection<UserforArt> articles=articleService.getArticleByUserID(userid);
            object.put("data",user);
            object.put("data1",articles);
            object.put("status",0);
        }
        out.print(object);
    }
    @PostMapping("/user/register")
    public void register(HttpServletResponse response, HttpServletRequest request, User user,HttpSession session) throws Exception {
        String code=request.getParameter("code");
        JSONObject object=new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        if (code.equals(map.get(user.getEmail()).toString())) {
            if (userSevice.CheckEmail(user.getEmail()) == 0) {
                user.setTime(new Date());
                user.setRole(3);
                user.setPassWord(rsAenCrypt.encrypt(user.getPassWord()));
                if (userSevice.InsertUser(user) != 0) {
                    object.put("status", 0);
                    map.remove(user.getEmail());
                    object.put("LoginUser", user.getEmail());
                    object.put("LoginRole", user.getRole());
                    session.setAttribute("LoginUser",user.getEmail());
                    session.setAttribute("LoginUserRole",user.getRole());
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
    public void login(HttpServletResponse response, User user,HttpSession session) throws Exception {
        JSONObject object=new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        try {
            if (rsAenCrypt.decrypt(userSevice.LoginCheck(user)).equals(user.getPassWord())) {
                object.put("status", 0);
                object.put("LoginUser", user.getEmail());
                object.put("LoginRole", userSevice.GetUser(user.getEmail()).getRole());
                session.setAttribute("LoginUser", user.getEmail());
                object.put("msg", "登陆成功");
                object.put("action", "/");
            } else {
                object.put("status", 1);
                object.put("msg", "登陆失败请检查账户密码是否正确！");
                object.put("action", "");
            }
        }catch (Exception e){
            object.put("status", 1);
            object.put("msg", "登陆失败请检查账户密码是否正确！");
            object.put("action", "/");
        }finally {
            out.print(object);
        }

    }
    @RequestMapping("/exit")
    public String exit(HttpSession session) {

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
        if (userSevice.CheckEmail(email) == 0) {
            int newNum = (int)((Math.random()*9+1)*100000);
            map.put(email,newNum);
            if (mailService.sendSimpleMail(email,"小狗论坛","验证码："+newNum))
                object.put("status", 0);
            else{
                object.put("status", 1);
                object.put("data","发送邮箱失败！");
            }
        }else {
            object.put("status", 1);
            object.put("data","邮箱已被注册！");
        }

        out.print(object);
    }

    @GetMapping("/contact/send")
    public void send(HttpServletResponse response, Content content) throws IOException {
        JSONObject jsonObject=new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        if(mailService.sendSimpleMail("mrxiaodfeng@163.com",content.getTitle(),content.toString()))
            jsonObject.put("status",0);
        else
            jsonObject.put("status",1);
    }
    @PostMapping("/user/forget")
    public JSONObject forget(HttpServletRequest request) throws Exception {
        String code=request.getParameter("code");
        String email=request.getParameter("email");
        JSONObject jsonObject=new JSONObject();
        if (!map.containsKey(email)){
            jsonObject.put("status",1);
            jsonObject.put("msg","邮箱未发送验证码！");
            return jsonObject;
        }
        if (code.equals(map.get(email).toString())) {
            User user=new User();
            user.setPassWord(String.valueOf((int) ((Math.random() * 9 + 1) * 100000)));
            user.setRole(3);
            mailService.sendSimpleMail("mrxiaodfeng@163.com","随机密码，请尽快重新更改密码",user.getPassWord());
            user.setPassWord(rsAenCrypt.encrypt(user.getPassWord()));
            userSevice.UpdateUserPassword(user);
            map.remove(user.getEmail());
            jsonObject.put("status",0);
            jsonObject.put("msg","密码已发送至您的邮箱！请尽快重新修改！");
            jsonObject.put("action","/this");
        }
        else {
            jsonObject.put("status",1);
            jsonObject.put("msg","验证码不正确！");
        }
        return jsonObject;
    }
    @PostMapping("/user/updateInfo")
    @ResponseBody
    public JSONObject updateInfo(HttpServletRequest request,User user,HttpSession session) throws Exception {
        JSONObject json=new JSONObject();
        String email= (String) session.getAttribute("LoginUser");

        if (email==null||email==""){
            json.put("status",1);
            return json;
        }
        System.out.println(user);
        user.setID(userSevice.GetUserID(email));
        if (user.getPassWord()!=null){
            user.setPassWord(rsAenCrypt.encrypt(user.getPassWord()));
            userSevice.UpdateUserPassword(user);
        }
        else {
            userSevice.UpdateUserInfo(user);
            session.removeAttribute("LoginUser");
            session.setAttribute("LoginUser", user.getEmail());
        }
        json.put("action","/this");
        json.put("msg","修改成功！");
        json.put("status",0);
        return json;
    }


}
