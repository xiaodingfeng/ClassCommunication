package com.xiaobai.classcommunication.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.classcommunication.bean.Column;
import com.xiaobai.classcommunication.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@Controller
public class ColomnController {
    @Autowired
    ColumnService columnService;
    @RequestMapping("/getMyArticleColomn")
    public void getMyArticleColomn(HttpServletResponse response) throws IOException {
        JSONObject json=new JSONObject();
        json.put("status",0);
        Collection<Column> allColomn = columnService.getAllColomn();
        System.out.println(allColomn);
        json.put("data",allColomn);
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        out.print(json);
    }
}
