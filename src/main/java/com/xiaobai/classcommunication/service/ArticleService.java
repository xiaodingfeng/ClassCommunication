package com.xiaobai.classcommunication.service;

import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.mapper.ArticleMapper;
import com.xiaobai.classcommunication.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisTemplate redisTemplate;
   public Integer insertArticle(Article article){
       return articleMapper.insertArticle(article);
   }
   public Collection<Article> getAllArticle(){
       return articleMapper.getAllArticle();
   }
   public Article getArticleByID(Integer ID){
       String key = "article_" + ID;

       ValueOperations<String, Article> operations = redisTemplate.opsForValue();

       //判断redis中是否有键为key的缓存
       boolean hasKey = redisTemplate.hasKey(key);

       if (hasKey) {
           Article article = operations.get(key);
           System.out.println("从缓存中获得数据："+article);
           System.out.println("------------------------------------");
           return article;
       } else {
           Article article = articleMapper.getArticleByID(ID);
           System.out.println("查询数据库获得数据："+article);
           System.out.println("------------------------------------");
           // 写入缓存
           operations.set(key, article, 5, TimeUnit.HOURS);
           return article;
       }
   };
}
