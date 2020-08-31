package com.xiaobai.classcommunication.mapper;

import com.xiaobai.classcommunication.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
     String LoginCheck(User user);
     Integer InsertUser(User user);
     Integer CheckEmail(String email);
     Integer GetUserID(String email);
     User GetUser(String email);
     Integer UpdateUserImage(User user);
     User GetUserbyID(Integer ID);
     Integer UpdateUserInfo(User user);
     Integer UpdateUserPassword(User user);
}
