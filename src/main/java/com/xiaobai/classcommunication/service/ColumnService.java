package com.xiaobai.classcommunication.service;

import com.xiaobai.classcommunication.bean.Column;
import com.xiaobai.classcommunication.mapper.ColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ColumnService {
    @Autowired
    ColumnMapper columnMapper;
    public Collection<Column> getAllColomn(){
        return columnMapper.getAllColomn();
    }
}
