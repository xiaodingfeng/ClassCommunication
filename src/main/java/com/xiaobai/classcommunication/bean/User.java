package com.xiaobai.classcommunication.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer ID;
    private String UserName;
    private String PassWord;
    private String Email;
    private String Role;
    private String Image;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date Time;
}
