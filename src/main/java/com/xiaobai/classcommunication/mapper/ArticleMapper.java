package com.xiaobai.classcommunication.mapper;

import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.bean.UserforArt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Repository
@Mapper
public interface ArticleMapper {
   Integer insertArticle(Article article);
   Collection<UserforArt> getAllArticle();
    Collection<UserforArt> getListArticleByID(Map<String, Object> map);
 UserforArt getArticleByID(Integer ID);
    Collection<UserforArt> getAllArticleTime15();
    Collection<UserforArt> getArticleTop15();
    Collection<UserforArt> getArticlePlacedTop();
    Collection<UserforArt> getArticleTop15byColumns(Integer Columns);
    Collection<UserforArt> getArticleTopNowWeek();
    Collection<UserforArt> getArticleByUserID(Integer UserID);
    Collection<UserforArt> getArticleBySearch(String Content);
    Integer updateArticleCommentsCount(Integer ID);
    Integer getAllArticleCount(Integer Columns);
    Integer updateArticleWatchCountCount(Integer ID);
}
