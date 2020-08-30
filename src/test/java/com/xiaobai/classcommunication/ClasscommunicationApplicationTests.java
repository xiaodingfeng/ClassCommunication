package com.xiaobai.classcommunication;

import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClasscommunicationApplicationTests {
    @Autowired
    ArticleService articleService;
    @Test
    void contextLoads() {
       articleService.getArticleByID(73);
        articleService.getArticleByID(73);
    }

}
