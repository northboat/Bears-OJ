package com.postoffice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//使用JdbcTemplate的原生sql进行CRUD
@RestController
public class JDBCController {

    //set方法注入
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/list")
    public List<Map<String, Object>> list(){
        String sql = "select * from Postman";
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/add")
    public String add(){
        String sql = "insert into PostOffice.Postman(num,`count`,`name`,`to`,`subject`,`text`) values (3,0,'xzt','1543625674@qq.com','hello','hahaha')";
        jdbcTemplate.update(sql);
        return "add ok";
    }
}
