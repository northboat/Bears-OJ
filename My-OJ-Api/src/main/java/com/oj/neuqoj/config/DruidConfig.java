package com.oj.neuqoj.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控
    //相当于web.xml
    @Bean
    public ServletRegistrationBean servlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        Map<String, String> properties = new HashMap<>();
        properties.put("loginUsername", "NorthBoat");
        properties.put("loginPassword", "011026");

        //允许谁能访问
        properties.put("allow", "");
        //禁止谁能访问
        //properties.put("NorthBoat", "39.106.160.174");

        bean.setInitParameters(properties);
        return bean;
    }

}
