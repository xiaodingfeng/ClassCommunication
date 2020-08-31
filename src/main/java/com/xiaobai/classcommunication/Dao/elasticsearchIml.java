package com.xiaobai.classcommunication.Dao;

import com.xiaobai.classcommunication.bean.UserforArt;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public interface elasticsearchIml {
    IndexRequest createIndex(String indexName);
    Boolean deleteIndex(String indexName);
    IndexRequest getIndex(String indexName);
    Boolean addSource(String indexName,Object o);
    Collection searchSource(String indexName,Object o,String col,Integer page,Integer size);
    Boolean ListAddSource(String indexName, ArrayList<UserforArt> o);
}
