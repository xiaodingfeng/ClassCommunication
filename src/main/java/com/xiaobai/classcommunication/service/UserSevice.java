package com.xiaobai.classcommunication.service;

import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.bean.User;
import com.xiaobai.classcommunication.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service
public class UserSevice {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    public String LoginCheck(User user){
        return userMapper.LoginCheck(user);
    }
    public Integer InsertUser(User user){
        return userMapper.InsertUser(user);
    }
    public Integer CheckEmail(String email){
        return userMapper.CheckEmail(email);
    }
    public Integer GetUserID(String email){return userMapper.GetUserID(email) ;   }
    public User GetUser(String email){
        String key = "UserEmail_"+email;
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            User user = operations.get(key);
            return user;
        } else {
            User user = userMapper.GetUser(email);
            // 写入缓存
            operations.set(key, user, 5, TimeUnit.HOURS);
            return user;
        }
    }
    public User GetUserbyID(Integer ID){
        String key = "UserID_"+ID;
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            User user = operations.get(key);
            return user;
        } else {
            User user = userMapper.GetUserbyID(ID);
            // 写入缓存
            operations.set(key, user, 5, TimeUnit.HOURS);
            return user;
        }
    }
    public Integer UpdateUserImage(User user){
        redisTemplate.delete("UserEmail_"+user.getEmail());
        redisTemplate.delete("UserID_"+user.getID());
        return userMapper.UpdateUserImage(user);}
    public Integer UpdateUserInfo(User user){
        redisTemplate.delete("UserEmail_"+user.getEmail());
        redisTemplate.delete("UserID_"+user.getID());
        return userMapper.UpdateUserInfo(user);
    }
    public Integer UpdateUserPassword(User user){
        return userMapper.UpdateUserPassword(user);
    }
}
