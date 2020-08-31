package com.xiaobai.classcommunication;

import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.bean.StaticName;
import com.xiaobai.classcommunication.bean.UserforArt;
import com.xiaobai.classcommunication.service.ArticleService;
import com.xiaobai.classcommunication.service.elasticsearchService;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@SpringBootTest
class ClasscommunicationApplicationTests {
    @Autowired
    ArticleService articleService;
    @Test
    void contextLoads() {

    }
    @Test
    void  test1(){
        for (int i = 1; i < 2; i++) {
            Article article=new Article();
            article.setUserID(10);
            article.setColumns(6);
            article.setTitle("标题"+i);
            article.setUpdateTime(new Date());
            article.setContent("内容"+i);
            Integer integer = articleService.insertArticle(article);
            System.out.println(integer);
            System.out.println(article.getID());
        }
    }
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void test11(){
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }
    @Autowired
    elasticsearchService elasticsearchService;
    @Test
    void elssss(){
        ArrayList<UserforArt> allArticle = (ArrayList<UserforArt>) articleService.getAllArticle();
        elasticsearchService.ListAddSource(StaticName.indexName,allArticle);
    }
    @Test
    void  jahsdjs(){
//        Collection collection = elasticsearchService.searchSource(StaticName.indexName, 11, "userID");
//        System.out.println(collection);
    }
}
