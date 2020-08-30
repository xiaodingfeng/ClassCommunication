package com.xiaobai.classcommunication.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.net.HttpResponse;
import com.xiaobai.classcommunication.config.ServerConfig;
import com.xiaobai.classcommunication.untils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UploadController {
    @Autowired
    UploadFile uploadFile;
    @Autowired
    ServerConfig serverConfig;
    @PostMapping("/api/upload/")
    public void uploadImage(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        String uploadfile = uploadFile.uploadfile(file, "/images/");
        JSONObject json=new JSONObject();

        if (uploadfile!=null){
//            uploadfile=serverConfig.getUrl()+uploadfile;
            json.put("url",uploadfile);
            json.put("code",0);
        }
        else{
            json.put("url",null);
            json.put("code",1);
            json.put("msg","图片保存失败");
        }

        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        out.print(json);
    }
}
