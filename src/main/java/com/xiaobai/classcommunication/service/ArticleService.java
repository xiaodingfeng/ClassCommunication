package com.xiaobai.classcommunication.service;

import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.bean.UserforArt;
import com.xiaobai.classcommunication.mapper.ArticleMapper;
import com.xiaobai.classcommunication.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisTemplate redisTemplate;
   public Integer insertArticle(Article article){
       redisTemplate.delete("article_AllArticle");
       redisTemplate.delete("article_ArticleTime15");
       redisTemplate.delete("article_ArticleTop15");
       redisTemplate.delete("article_ArticlePlacedTop");
       redisTemplate.delete("article_ArticleTopNowWeek");
       redisTemplate.delete("articleColumns_"+article.getColumns());
       redisTemplate.delete("article_ArticleByUserID_"+article.getUserID());
       return articleMapper.insertArticle(article);
   }
   public Collection<UserforArt> getAllArticle( ){
       String key = "article_AllArticle";
       ValueOperations<String, Collection<UserforArt>> operations = redisTemplate.opsForValue();
       //判断redis中是否有键为key的缓存
       boolean hasKey = redisTemplate.hasKey(key);
       if (hasKey) {
           Collection<UserforArt> article = operations.get(key);
           return article;
       } else {
           Collection<UserforArt> article = articleMapper.getAllArticle();
           // 写入缓存
           operations.set(key, article, 5, TimeUnit.HOURS);
           return article;
       }
   }
    public Collection<UserforArt> getListArticleByID(Integer Columns,Integer pageNum, Integer pageSize){
        String key = "articleColumns_" + Columns+"_"+pageNum;
        ValueOperations<String, Collection<UserforArt>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            System.out.println("Redis中获取数据");
            Collection<UserforArt> article = operations.get(key);
            return article;
        } else {
            System.out.println("Mysql中获取数据");
            Map<String, Object> map = new HashMap<>();
            map.put("Columns",Columns);
            map.put("pageNum",(--pageNum)*pageSize);
            map.put("pageSize",pageSize);
            Collection<UserforArt> article = articleMapper.getListArticleByID(map);
            // 写入缓存
            operations.set(key, article, 5, TimeUnit.HOURS);
            return article;
        }
   }
    public UserforArt getArticleByID(Integer ID){

       String key = "article_" + ID;
       ValueOperations<String, UserforArt> operations = redisTemplate.opsForValue();
       //判断redis中是否有键为key的缓存
       boolean hasKey = redisTemplate.hasKey(key);
       if (hasKey) {
           UserforArt article = operations.get(key);
           return article;
       } else {
           UserforArt article = articleMapper.getArticleByID(ID);
           // 写入缓存
           operations.set(key, article, 5, TimeUnit.HOURS);
           return article;
       }
    }
    public Collection<UserforArt> getAllArticleTime15(){
        String key = "article_ArticleTime15";
        ValueOperations<String, Collection<UserforArt>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<UserforArt> article = operations.get(key);
            return article;
        } else {
            Collection<UserforArt> article = articleMapper.getAllArticleTime15();
            // 写入缓存
            operations.set(key, article, 5, TimeUnit.HOURS);
            return article;
        }
   }
    public Collection<UserforArt> getArticleTop15(){
        String key = "article_ArticleTop15";
        ValueOperations<String, Collection<UserforArt>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<UserforArt> article = operations.get(key);
            return article;
        } else {
            Collection<UserforArt> article = articleMapper.getArticleTop15();
            // 写入缓存
            operations.set(key, article, 5, TimeUnit.HOURS);
            return article;
        }
   }
    public Collection<UserforArt> getArticlePlacedTop(){
        String key = "article_ArticlePlacedTop";
        ValueOperations<String, Collection<UserforArt>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<UserforArt> article = operations.get(key);
            return article;
        } else {
            Collection<UserforArt> article = articleMapper.getArticlePlacedTop();
            // 写入缓存
            operations.set(key, article, 5, TimeUnit.HOURS);
            return article;
        }
   }
    public Collection<UserforArt> getArticleByUserID(Integer UserID){
        String key = "article_ArticleByUserID_"+UserID;
        ValueOperations<String, Collection<UserforArt>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<UserforArt> article = operations.get(key);
            return article;
        } else {
            Collection<UserforArt> article = articleMapper.getArticleByUserID(UserID);
            // 写入缓存
            operations.set(key, article, 5, TimeUnit.HOURS);
            return article;
        }
    }
   public Collection<UserforArt> getArticleTop15byColumns(Integer Columns){
       String key = "article_ArticleTop15byColumns_"+Columns;
       ValueOperations<String, Collection<UserforArt>> operations = redisTemplate.opsForValue();
       //判断redis中是否有键为key的缓存
       boolean hasKey = redisTemplate.hasKey(key);

       if (hasKey) {
           Collection<UserforArt> article = operations.get(key);
           return article;
       } else {
           Collection<UserforArt> article = articleMapper.getArticleTop15byColumns(Columns);
           // 写入缓存
           operations.set(key, article, 5, TimeUnit.HOURS);
           return article;
       }
   }
    public Collection<UserforArt> getArticleTopNowWeek(){
        String key = "article_ArticleTopNowWeek";
        ValueOperations<String, Collection<UserforArt>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<UserforArt> article = operations.get(key);
            return article;
        } else {
            Collection<UserforArt> article = articleMapper.getArticleTopNowWeek();
            // 写入缓存
            operations.set(key, article, 5, TimeUnit.HOURS);
            return article;
        }
   }
   public  Collection<UserforArt> getArticleBySearch(String Content){return articleMapper.getArticleBySearch(Content);}
    public Integer updateArticleCommentsCount(Integer ID){
        redisTemplate.delete("article_"+ID);
        return articleMapper.updateArticleCommentsCount(ID);
    }
    public Integer getAllArticleCount(Integer Columns){return articleMapper.getAllArticleCount(Columns);}
    public  Integer updateArticleWatchCountCount(Integer ID){

        redisTemplate.delete("getAllReplyById_"+ID);
        redisTemplate.delete("article_"+ID);
        redisTemplate.delete("article_AllArticle");
        redisTemplate.delete("article_ArticleTime15");
        redisTemplate.delete("article_ArticleTop15");
        redisTemplate.delete("article_ArticlePlacedTop");
        redisTemplate.delete("article_ArticleTopNowWeek");
       return articleMapper.updateArticleWatchCountCount(ID);
    }
}
