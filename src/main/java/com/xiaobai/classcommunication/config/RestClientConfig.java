package com.xiaobai.classcommunication.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client=new RestHighLevelClient(RestClient.builder(
                new HttpHost("47.115.118.94",9200,"http")
        ));
        return client;
    }
}