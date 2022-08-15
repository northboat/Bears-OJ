package com.oj.neuqoj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //登录页
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html", "/", "/login", "/send", "/verify",
                        "/userList", "/register", "/getRepo", "/getQuestion", "/getDetail", "/judge",
                        "/storeRes", "/getRes", "/getNews", "/getQuesNews", "/getTopUser", "/getTopic",
                        "/getTopics", "/getReply", "/reply", "/getComments", "/comment",
                        "/getPaintings", "/submitPainting", "/getList", "/write",
                        "/getTopicsByName", "/getPaintingsByName", "/getInfoByName",
                        "/deletePainting", "/deleteTopic", "/getCode", "/change");
    }
}
