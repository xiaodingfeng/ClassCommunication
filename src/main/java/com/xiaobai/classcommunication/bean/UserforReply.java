package com.xiaobai.classcommunication.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserforReply implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer Role;
    private String Image;
    private Integer ID;
    private Integer ArticleID;
    private Integer UserID;
    private Integer ReplyUserID;
    private String ReplyContent;
    private Integer LikeCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ReplyTime;

}
