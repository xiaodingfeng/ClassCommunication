package com.xiaobai.classcommunication.service;

import com.xiaobai.classcommunication.bean.Column;
import com.xiaobai.classcommunication.bean.Reply;
import com.xiaobai.classcommunication.bean.UserforReply;
import com.xiaobai.classcommunication.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service
public class ReplyService {
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    RedisTemplate redisTemplate;
    public Integer insertReply(Reply reply){
        redisTemplate.delete("getAllReplyById_"+reply.getArticleID());
        redisTemplate.delete("article_AllArticle");
        redisTemplate.delete("article_ArticleTime15");
        redisTemplate.delete("article_ArticleTop15");
        redisTemplate.delete("article_ArticlePlacedTop");
        redisTemplate.delete("article_ArticleTopNowWeek");
        redisTemplate.delete("article_"+reply.getArticleID());
        return replyMapper.insertReply(reply);
    }
    public Collection<UserforReply> getAllReplyById(Integer ArticleID){
        String key = "getAllReplyById_"+ArticleID;
        ValueOperations<String, Collection<UserforReply>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<UserforReply> replies = operations.get(key);
            return replies;
        } else {
            Collection<UserforReply> replies = replyMapper.getAllReplyById(ArticleID);
            // 写入缓存
            operations.set(key, replies, 5, TimeUnit.HOURS);
            return replies;
        }
    }
}
