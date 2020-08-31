package com.xiaobai.classcommunication.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class Reply implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer ID;
    private Integer ArticleID;
    private Integer ReplyUserID;
    private String ReplyContent;
    private Integer LikeCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ReplyTime;
}
