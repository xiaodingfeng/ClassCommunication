package com.xiaobai.classcommunication.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.classcommunication.bean.*;
import com.xiaobai.classcommunication.mapper.ArticleMapper;
import com.xiaobai.classcommunication.service.ArticleService;
import com.xiaobai.classcommunication.service.ReplyService;
import com.xiaobai.classcommunication.service.UserSevice;
import com.xiaobai.classcommunication.service.elasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Controller
public class ArcticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ReplyService replyService;
    @Autowired
    UserSevice userSevice;
    @Autowired
    elasticsearchService elasticsearchService;
    @PostMapping("/insertActicle")
    public void insertArcticle(Article article, HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String UserEmail=request.getParameter("LoginUser");
        System.out.println(article.getContent());
        article.setUserID(userSevice.GetUserID(UserEmail));
        JSONObject json=new JSONObject();
        article.setWatchCount(0);
        article.setCommentsCount(0);
        article.setGoodPost(0);
        article.setPlacedTop(0);
        article.setUpdateTime(new Date());
        if(articleService.insertArticle(article)!=0){
            UserforArt articleByID = articleService.getArticleByID(article.getID());
            elasticsearchService.addSource(StaticName.indexName,articleByID);
            json.put("action","/");
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
    public void reply(Reply reply, HttpServletResponse response, HttpServletRequest request ) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String UserEmail=request.getParameter("LoginUser");
        reply.setLikeCount(0);
        reply.setReplyTime(new Date());
        reply.setReplyUserID(userSevice.GetUserID(UserEmail));
        System.out.println(reply);
        JSONObject json=new JSONObject();
        if(replyService.insertReply(reply)!=0){
            Integer articleID = reply.getArticleID();
            articleService.updateArticleCommentsCount(articleID);
            json.put("status",0);
            json.put("msg","回复成功！");
            json.put("action","/this");
        }
        else{
            json.put("status",1);
        }
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out=response.getWriter();
        out.print(json);
    }
    @PostMapping("/getMyArticle")
    public void getMyArticle(HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        Collection<UserforArt> article = null;
        String seach = request.getParameter("seach");
        if (seach!=null&&seach!="")
        {
            article = articleService.getArticleBySearch(seach);
        }
        else {
            Integer Columns = 0;
            try {
                Columns = Integer.valueOf(request.getParameter("id"));
            } catch (Exception e) {
            }
            if (Columns == 0) {
                article = articleService.getAllArticle();
            }
            else {
                article=(Collection<UserforArt>) elasticsearchService.searchSource(StaticName.indexName,Columns,"columns",1,10);
//                article = articleService.getListArticleByID(Columns,1,10);
                Integer Count=articleService.getAllArticleCount(Columns);
                json.put("data1",Count);
            }
        }
        json.put("status", 0);
        json.put("data", article);

        out.print(json);
    }
    @RequestMapping("/getMyArticleColomnPage")
    public void getMyArticleColomnPage(HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Integer pageNum = Integer.valueOf(request.getParameter("PageNum"));

        Integer pageSize = Integer.valueOf(request.getParameter("PageSize"));

        Integer Columns = Integer.valueOf(request.getParameter("Columns"));
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        Collection<UserforArt> article=null;
        article=(Collection<UserforArt>) elasticsearchService.searchSource(StaticName.indexName,Columns,"columns",pageNum,pageSize);
//        article=articleService.getListArticleByID(Columns,pageNum,pageSize);
        json.put("status", 0);
        json.put("data", article);
        out.print(json);
    }
    @PostMapping("/getArticleDetail")
    public void getArticleDetail(HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = 0;
        try {
            id = Integer.valueOf(request.getParameter("id"));
        } catch (Exception e) {

        }
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        UserforArt article = new UserforArt();
        if (id == 0) {
            article = null;
        }
        else {

            articleService.updateArticleWatchCountCount(id);
            article = articleService.getArticleByID(id);
        }
        Collection<UserforReply> reply=null;
        reply=replyService.getAllReplyById(id);
        json.put("status", 0);
        json.put("data", article);
        json.put("data2", reply);
        Collection<UserforArt> articles=null;
        articles = articleService.getArticleTopNowWeek();
        json.put("data1",articles);
        out.print(json);
    }

    @PostMapping("/getArticleTop15byColumns")
    public void getArticleTop15byColumns(HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Integer Columns = 0;
        try {
            Columns = Integer.valueOf(request.getParameter("id"));
        } catch (Exception e) {
        }
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        Collection<UserforArt> article = null;
        if (Columns == 0) {
            article = articleService.getArticleTop15();
        }
        else {
            article = articleService.getArticleTop15byColumns(Columns);
        }
        json.put("status", 0);
        json.put("data", article);
        out.print(json);
    }
    @PostMapping("/getIndexArticle")
    public void getIndexArticle(HttpServletResponse response,HttpServletRequest request) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        Collection<UserforArt> article = null;
        article = articleService.getArticleTop15();
        json.put("data1", article);
        article = articleService.getAllArticleTime15();
        json.put("data2", article);
        article=articleService.getArticlePlacedTop();
        json.put("data3", article);
        article=articleService.getArticleTopNowWeek();
        json.put("data4", article);
        json.put("status", 0);
        out.print(json);
    }
    @PostMapping("/Art/delete")
    public JSONObject delete(HttpServletRequest request){
        String id = request.getParameter("id");
        return new JSONObject();
    }
}
