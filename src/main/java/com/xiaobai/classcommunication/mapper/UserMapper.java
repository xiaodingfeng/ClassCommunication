package com.xiaobai.classcommunication.mapper;

import com.xiaobai.classcommunication.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
     Integer LoginCheck(User user);
     Integer InsertUser(User user);
     Integer CheckEmail(User user);
     Integer GetUserID(String email);
}
