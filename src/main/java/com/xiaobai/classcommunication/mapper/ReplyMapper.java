package com.xiaobai.classcommunication.mapper;

import com.xiaobai.classcommunication.bean.Reply;
import com.xiaobai.classcommunication.bean.UserforReply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper
public interface ReplyMapper {
    Integer insertReply(Reply reply);
    Collection<UserforReply> getAllReplyById(Integer ArticleID);
}
