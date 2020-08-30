package com.xiaobai.classcommunication.mapper;

import com.xiaobai.classcommunication.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
@Mapper
public interface ArticleMapper {
   Integer insertArticle(Article article);
   Collection<Article> getAllArticle();
    Article getArticleByID(Integer ID);
}
