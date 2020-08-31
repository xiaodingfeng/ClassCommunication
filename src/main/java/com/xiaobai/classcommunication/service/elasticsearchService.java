package com.xiaobai.classcommunication.service;

import com.alibaba.fastjson.JSON;
import com.xiaobai.classcommunication.Dao.elasticsearchIml;
import com.xiaobai.classcommunication.bean.UserforArt;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class elasticsearchService implements elasticsearchIml {

    Logger logger= LoggerFactory.getLogger(Logger.class);
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public IndexRequest createIndex(String indexName)   {
        CreateIndexRequest xiao_user = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = null;
        try {
             createIndexResponse = restHighLevelClient.indices().create(xiao_user, RequestOptions.DEFAULT);
            return new IndexRequest(indexName);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean deleteIndex(String indexName) {
        DeleteIndexRequest xiao_user = new DeleteIndexRequest(indexName);
        AcknowledgedResponse delete=null;
        try {
             delete = restHighLevelClient.indices().delete(xiao_user, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert delete != null;
        return delete.isAcknowledged();
    }

    @Override
    public IndexRequest getIndex(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        IndexRequest indexRequest = null;
        try {
             Boolean response = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
             if (response)
                 indexRequest=new IndexRequest(indexName);
             else 
                 indexRequest=createIndex(indexName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexRequest;
    }

    @Override
    public Boolean addSource(String indexName,Object o) {
        IndexRequest request = getIndex(indexName);
        request.timeout("1s");
        IndexRequest source = request.source(JSON.toJSONString(o), XContentType.JSON);
        try {
            IndexResponse index = restHighLevelClient.index(source, RequestOptions.DEFAULT);
            System.out.println(index.status());
            if (index.status()== RestStatus.CREATED)
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Collection searchSource(String indexName,Object o,String col,Integer page,Integer size) {
        getIndex(indexName);

        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder builder=new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder= QueryBuilders.termQuery(col,o);
        //分页
        builder.from((page-1)*size);
        builder.size(size);
        builder.query(termQueryBuilder);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(builder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collection collection=new ArrayList();
        for (SearchHit searchHit:search.getHits().getHits()){
            collection.add(searchHit.getSourceAsMap());
        }
//        System.out.println("elasticsearch查询数据");
        return collection;
    }

    @Override
    public Boolean ListAddSource(String indexName, ArrayList<UserforArt> o) {
        getIndex(indexName);
        BulkRequest request=new BulkRequest();
        request.timeout("10s");
        for (int i = 0; i < o.size(); i++) {
            request.add(new IndexRequest(indexName)
                    .source(JSON.toJSONString(o.get(i)),XContentType.JSON));
        }
        BulkResponse bulk = null;
        try {
            bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
       if (bulk.status()==RestStatus.OK)
           return true;
       else return false;
    }

}
