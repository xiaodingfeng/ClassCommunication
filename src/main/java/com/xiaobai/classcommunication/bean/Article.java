package com.xiaobai.classcommunication.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer ID;
    private Integer UserID;
    private String Title;
    private String Content;
    private Integer Columns;
    private Integer Experience;
    private Integer WatchCount;
    private Integer CommentsCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date UpdateTime;
    private Integer GoodPost;
    private Integer PlacedTop;

}
