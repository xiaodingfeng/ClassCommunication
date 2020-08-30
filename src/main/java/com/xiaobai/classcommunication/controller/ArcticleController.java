package com.xiaobai.classcommunication.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.bean.Reply;
import com.xiaobai.classcommunication.mapper.ArticleMapper;
import com.xiaobai.classcommunication.service.ArticleService;
import com.xiaobai.classcommunication.service.ReplyService;
import com.xiaobai.classcommunication.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@Controller
public class ArcticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ReplyService replyService;
    @Autowired
    UserSevice userSevice;
    @PostMapping("/insertActicle")
    public void insertArcticle(Article article, HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String UserEmail=request.getParameter("LoginUser");
        System.out.println(UserEmail);
        article.setUserID(userSevice.GetUserID(UserEmail));
        JSONObject json=new JSONObject();
        article.setWatchCount(0);
        article.setCommentsCount(0);
        for (int i = 0; i < 50; i++) {
            articleService.insertArticle(article);
        }
        if(articleService.insertArticle(article)!=0){
            json.put("action","./add.html");
            json.put("msg","提交成功！");
            json.put("status",0);
        }
        else{
            json.put("status",1);
            json.put("msg","提交失败！");
        }
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        out.print(json);
    }
    @PostMapping("/jie/reply")
    public void reply(Reply reply, HttpServletResponse response, HttpServletRequest request) throws IOException {

        request.setCharacterEncoding("UTF-8");
        reply.setArticleID(Integer.parseInt(request.getParameter("ArticleID")));
        reply.setLikeCount(0);
        System.out.println(reply);
        JSONObject json=new JSONObject();
        if(replyService.insertReply(reply)!=0){
            json.put("status",0);
        }
        else{
            json.put("status",1);
        }
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        out.print(json);
    }
    @GetMapping("/getMyArticle")
    public void getMyArticle(HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject json=new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        Collection<Article> article=articleService.getAllArticle();
        json.put("status",0);
        json.put("data",article);
        out.print(json);
    }
}
