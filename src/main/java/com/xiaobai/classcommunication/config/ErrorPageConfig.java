package com.xiaobai.classcommunication.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage errorPage=new ErrorPage(HttpStatus.NOT_FOUND,"/html/other/404.html");
//        ErrorPage errorPage1=new ErrorPage(HttpStatus.)
        registry.addErrorPages(errorPage);
    }
}
