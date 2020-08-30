package com.xiaobai.classcommunication.service;

import com.xiaobai.classcommunication.bean.Reply;
import com.xiaobai.classcommunication.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class ReplyService {
    @Autowired
    ReplyMapper replyMapper;
    public Integer insertReply(Reply reply){
        return replyMapper.insertReply(reply);
    }
    public Collection<Reply> getAllReply(){
        return replyMapper.getAllReply();
    }
}
