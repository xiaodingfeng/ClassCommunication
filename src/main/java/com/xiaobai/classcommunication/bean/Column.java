package com.xiaobai.classcommunication.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class Column implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer ID;
    private String Name;
}
