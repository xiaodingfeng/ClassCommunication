package com.xiaobai.classcommunication.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.classcommunication.bean.User;
import com.xiaobai.classcommunication.config.ServerConfig;
import com.xiaobai.classcommunication.service.UserSevice;
import com.xiaobai.classcommunication.untils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UploadController {
    @Autowired
    UploadFile uploadFile;
    @Autowired
    ServerConfig serverConfig;
    @Autowired
    UserSevice userSevice;
    @PostMapping("/api/upload/")
    public void uploadImage(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {

        String uploadfile = uploadFile.uploadfile(file, "/images/");
        JSONObject json=new JSONObject();
        String[] data=new String[1];
        if (uploadfile!=null){
            data[0]=uploadfile;
//            uploadfile=serverConfig.getUrl()+uploadfile;
            json.put("data",data);
            json.put("errno",0);
        }
        else{
            json.put("data",data);
            json.put("errno",1);
            json.put("msg","图片保存失败");
        }

        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        out.print(json);
    }

    @PostMapping("/user/upload/")
    public void uploadImageuser(@RequestParam("file") MultipartFile file, HttpServletResponse response,HttpSession session) throws IOException {
        String email= (String) session.getAttribute("LoginUser");
        String uploadfile = uploadFile.uploadfile(file, "/images/");
        JSONObject json=new JSONObject();
        if (uploadfile!=null){
            User user=new User();
            user.setEmail(email);
            user.setImage(uploadfile);
            userSevice.UpdateUserImage(user);
            json.put("data",uploadfile);
            json.put("errno",0);
        }
        else{
            json.put("errno",1);
            json.put("msg","图片保存失败");
        }

        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        out.print(json);
    }
}
