package com.xiaobai.classcommunication.service;

import com.xiaobai.classcommunication.bean.User;
import com.xiaobai.classcommunication.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSevice {

    @Autowired
    UserMapper userMapper;
    public Integer LoginCheck(User user){
        return userMapper.LoginCheck(user);
    }
    public Integer InsertUser(User user){
        return userMapper.InsertUser(user);
    }
    public Integer CheckEmail(User user){
        return userMapper.CheckEmail(user);
    }
    public Integer GetUserID(String email){return userMapper.GetUserID(email) ;   }
}
