package com.xiaobai.classcommunication.service;

import com.xiaobai.classcommunication.bean.Article;
import com.xiaobai.classcommunication.bean.Column;
import com.xiaobai.classcommunication.mapper.ColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service
public class ColumnService {
    @Autowired
    ColumnMapper columnMapper;
    @Autowired
    RedisTemplate redisTemplate;
    public Collection<Column> getAllColomn(){
        String key = "getAllColomn";
        ValueOperations<String, Collection<Column>> operations = redisTemplate.opsForValue();
        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Collection<Column> columns = operations.get(key);
            return columns;
        } else {
            Collection<Column> columns = columnMapper.getAllColomn();
            // 写入缓存
            operations.set(key, columns, 5, TimeUnit.HOURS);
            return columns;
        }
    }
}
