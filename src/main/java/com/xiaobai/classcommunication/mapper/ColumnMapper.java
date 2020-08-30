package com.xiaobai.classcommunication.mapper;

import com.xiaobai.classcommunication.bean.Column;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
@Repository
public interface ColumnMapper {
    @Select("select * from class_column")
    Collection<Column> getAllColomn();
}
