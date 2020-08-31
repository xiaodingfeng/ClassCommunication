package com.xiaobai.classcommunication.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserforArt implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer UserID;
    private String UserName;
    private Integer Role;
    private String Image;
    private Integer ID;
    private String Title;
    private String Content;
    private Integer Columns;
    private String ColumnsName;
    private Integer Experience;
    private Integer WatchCount;
    private Integer CommentsCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date UpdateTime;
    private Integer GoodPost;
    private Integer PlacedTop;
}
