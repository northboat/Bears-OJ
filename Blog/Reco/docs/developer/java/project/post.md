---
title: 基于POP3、Mybatis的邮件系统
date: 2022-1-15
tags:
  - Web
  - Java
categories:
  - WebApp
---

> springboot、bootstrap、mybatis实现的邮件管理系统
>
> 定时发送基于多线程管理

## 核心功能

### Postman

`Postman`继承`Thread`类，在`run`方法中实现发送邮件功能

定时实现

~~~java
do{
    //send the message
    TimeUnit.DAYS.sleep(7);    
} while(!stop);
~~~

邮递员下班

~~~java
public void shutdown(){
    stop = true;
}

//用于office判断并在Map和sql中销毁邮件
public boolean isStopped(){
    return stop;
}
~~~

准备邮件

- Session从上一级PostOffice中传入

~~~java
//邮件信息
private String from;
private Mail mail;
private MimeMessage message;
//使线程停止
private boolean stop;

public Postman(){
    stop = false;
    from = "northboat@qq.com";
}

public void ready(Session session, Mail mail) throws MessagingException {
    this.mail = mail;
    this.mail.setFrom(from);
    //this.mail.setCount(0);
    message = new MimeMessage(session);
    message.setFrom(new InternetAddress(from));
    message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail.getTo()));
    message.setSubject(mail.getSubject());
    message.setText(mail.getText());
}

//初次启动直接从数据库中构造Postman
public Postman(Session session, Mail mail) throws MessagingException {
    stop = false;
    from = "northboat@qq.com";
    mail.setCount(0);
    mail.setFrom(from);
    this.mail = mail;
    message = new MimeMessage(session);
    message.setFrom(new InternetAddress(from));
    message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail.getTo()));
    message.setSubject("Hello");
    message.setText("这是由" + mail.getName() + "为您订阅的邮件，将会每周定时为你发送");
}
~~~

完整Postman类

~~~java
package com.postoffice.service;

import com.postoffice.vo.Mail;

import javax.mail.MessagingException;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;



public class Postman extends Thread{

    //邮件信息
    private String from;
    private Mail mail;
    private MimeMessage message;
    //使线程停止
    private boolean stop;

    public Mail getMail(){
        return mail;
    }

    public Postman(){
        stop = false;
        from = "northboat@qq.com";
    }

    public Postman(Session session, Mail mail) throws MessagingException {
        stop = false;
        from = "northboat@qq.com";
        mail.setCount(0);
        mail.setFrom(from);
        this.mail = mail;
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail.getTo()));
        message.setSubject("Hello");
        message.setText("这是由" + mail.getName() + "为您订阅的邮件，将会每周定时为你发送");
    }


    public boolean isStopped(){
        return stop;
    }


    public void ready(Session session, Mail mail) throws MessagingException {
        this.mail = mail;
        this.mail.setFrom(from);
        //this.mail.setCount(0);
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail.getTo()));
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
    }

    @Override
    public void run() {
        do{
            try {
                //say hello
                if(mail.getCount() == 0){
                    message.setSubject("Hello");
                    message.setText("这是由" + mail.getName() + "为您订阅的邮件，将会每周定时为你发送");
                    Transport.send(message);
                    System.out.println("提示邮件发送成功");
                    TimeUnit.SECONDS.sleep(20);

                    message.setSubject(mail.getSubject());
                    message.setText(mail.getText());
                }

                Transport.send(message);

                System.out.println(mail.getName() + "给" + mail.getTo() + "的第" + (mail.getCount()+1) + "封邮件发送成功");
                mail.setCount(mail.getCount()+1);

                TimeUnit.DAYS.sleep(7);
            } catch (Exception e){
                System.out.println("线程异常，已中断");
                e.printStackTrace();
                stop = true;
                if(PostOffice.has(mail.getNum())){
                    PostOffice.remove(mail.getNum());
                }
                break;
            }
        } while(!stop);

        //say goodbye
        try {
            message.setSubject("goodbye");
            message.setText("订阅已结束，爷光荣下班");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("bye, i am gone");
    }

    public void shutdown(){
        stop = true;
    }
}
~~~

### PostOffice

`PostOffice`维护一个`Map<Integer, Postman>`用于管理线程

static加载基础设置

- 设置发送邮件端口
- 开启ssl
- 设置邮件服务器
- 设置超时时间
- 验证账号密码

~~~java
private static int num;
private static Session session;
private static Map<Integer, Postman> office;


static{
    office = new HashMap<>();

    Properties properties = new Properties();
    properties = System.getProperties();
    //设置第三方服务器
    properties.setProperty("mail.smtp.host", "smtp.qq.com");
    //开启密码验证
    properties.setProperty("mail.smtp.auth", "true");
    //设置超时时间
    properties.setProperty("mail.smtp.timeout", "4000");
    //开启debug
    properties.setProperty("mail.debug", "true");

    //开启ssl服务
    properties.setProperty("mail.smtp.ssl.enable", "true");
    //设置端口
    properties.setProperty("mail.smtp.port", "465");
    //设置ssl端口，必要的，否则连接不上
    properties.setProperty("mail.smtp.socketFactory.port", "465");
    properties.setProperty("mail.smtp.socketFactory.fallback", "false");
    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

    session = Session.getDefaultInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("northboat@qq.com", "oxftgstrzznrbddc");
        }
    });
}
~~~

从数据库中同步邮件数据，构造Postman线程存于Map中

~~~java
public static void flush(List<Mail> mails){
    for(Mail mail: mails){
        if(!PostOffice.has(mail.getNum())){
            //System.out.println("进来了");
            Postman postman = new Postman();
            try {
                postman.ready(session, mail);
            } catch (MessagingException e) {
                System.out.println("初始化邮件" + mail.getNum() + "失败，已跳过");
                continue;
            }
            //System.out.println(mail.getNum() + " " + mail.getName());
            office.put(mail.getNum(), postman);
        }
    }
}
~~~

首次启动时触发，令所有线程开始工作，初始化编号

~~~java
public static void beginWork(){
    for(Postman postman: office.values()){
        postman.start();
    }
    num = office.size();
}
~~~

新增邮件发送，在此处未直接连接数据库，选择在Controller中处理

~~~java
public static Mail send(Mail mail) {
    //在此处设置编号
    mail.setNum(++num);
    try {
        //在ready函数中设置from
        Postman postman = new Postman(session, mail);
        postman.start();
        if(postman.isStopped()){
            return null;
        }
        office.put(num, postman);
    } catch (MessagingException e) {
        System.out.println("初始化邮差报错，准备邮件失败");
    }
    return mail;
}
~~~

删除线程

~~~java
public static void remove(int num){
    office.get(num).shutdown();
    //office.get(num).destroy();
    office.remove(num);
}
~~~

判断是否存在编号为`num`的邮递员（邮件）

~~~java
public static boolean has(int num){
    return office.getOrDefault(num, null) != null;
}
~~~

获取邮递员

~~~java
public static Collection<Postman> getPostmen(){
    return office.values();
}

public static Postman getPostman(int num){
    return office.get(num);
}
~~~

完整代码

~~~java
package com.postoffice.service;

import com.postoffice.mapper.MailMapper;
import com.postoffice.vo.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import java.util.*;

//vnufsybifrabicjj SMTP/POP3(154)
//oxftgstrzznrbddc SMTP/POP3(193)
//bgmrnmqksqabbfaa IMAP/SMTP


public class PostOffice {

    private static int num;
    private static Session session;
    private static Map<Integer, Postman> office;


    static{
        office = new HashMap<>();

        Properties properties = new Properties();
        properties = System.getProperties();
        //设置第三方服务器
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        //开启密码验证
        properties.setProperty("mail.smtp.auth", "true");
        //设置超时时间
        properties.setProperty("mail.smtp.timeout", "4000");
        //开启debug
        properties.setProperty("mail.debug", "true");

        //开启ssl服务
        properties.setProperty("mail.smtp.ssl.enable", "true");
        //设置端口
        properties.setProperty("mail.smtp.port", "465");
        //设置ssl端口，必要的，否则连接不上
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("northboat@qq.com", "oxftgstrzznrbddc");
            }
        });
    }

    public static void flush(List<Mail> mails){
        for(Mail mail: mails){
            if(!PostOffice.has(mail.getNum())){
                //System.out.println("进来了");
                Postman postman = new Postman();
                try {
                    postman.ready(session, mail);
                } catch (MessagingException e) {
                    System.out.println("初始化邮件" + mail.getNum() + "失败，已跳过");
                    continue;
                }
                //System.out.println(mail.getNum() + " " + mail.getName());
                office.put(mail.getNum(), postman);
            }
        }

    }

    public static void beginWork(){
        for(Postman postman: office.values()){
            postman.start();
        }
        num = office.size()-1;
    }

    public static Mail send(Mail mail) {
        //在此处设置编号
        mail.setNum(++num);
        try {
            //在ready函数中设置from
            Postman postman = new Postman(session, mail);
            postman.start();
            if(postman.isStopped()){
                return null;
            }
            office.put(num, postman);
        } catch (MessagingException e) {
            System.out.println("初始化邮差报错，准备邮件失败");
        }
        return mail;
    }

    public static boolean has(int num){
        return office.getOrDefault(num, null) != null;
    }

    public static void remove(int num){
        office.get(num).shutdown();
        //office.get(num).destroy();
        office.remove(num);
    }

    public static Collection<Postman> getPostmen(){
        return office.values();
    }

    public static Postman getPostman(int num){
        return office.get(num);
    }
}
~~~

## DB

### 改用druid数据源

spring-jdbc默认数据源为`HikariDataSource`，优势：最高效

`druid`优势：信息监控

application.yml

~~~yml
spring:
  application:
    name: PostOffice
  datasource:
    username: root
    password: "011026"
    url: jdbc:mysql://localhost:3306/postoffice?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    filters: stat,wall,log4j

server:
  port: 8083
~~~

### druid的后台监控

通过`config`将`ServletRegistrationBean`注入`bean`

设置账号密码等基础信息，通过`/druid`可直接访问到`druid`已集成好的后台监控网站

~~~java
package com.postoffice.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
~~~

### 整合mybatis

application.yml

在此处绑定实体类目录，使mapper.xml可以找到对应类，同时绑定mapper.xml所在目录

~~~yml
#整合mybatis
mybatis:
  type-aliases-package: com.postoffice.vo
  mapper-locations: classpath:mybatis/mapper/*.xml
~~~

MailMapper.java，放置在com.postoffice.mapper目录下

~~~java
package com.postoffice.mapper;


import com.postoffice.vo.Mail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


//这个注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface MailMapper {

    List<Mail> queryMailList();

    void removeMail(int num);

    void addMail(Mail mail);

    void updateMailCount(Map<String, Integer> map);
}
~~~

MailMapper.xml，放在resources.mybatis.mapper目录下

第一件事：绑定命名空间，即绑定对应的Mapper接口

第二件事：绑定方法，通过`id="方法名"`进行绑定

传参问题：若为单一参数，直接写参数名即可；若为多个参数，这里采用Map的方式传入，通过key进行取值

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.postoffice.mapper.MailMapper">
    <select id="queryMailList" resultType="Mail">
        select * from `mail`
    </select>

    <delete id="removeMail" parameterType="int">
        delete from mail where `num` = #{num}
    </delete>

    <insert id="addMail" parameterType="Mail">
        insert into mail(`num`, `count`, `name`, `to`, `from`, `subject`, `text`) values(#{num}, #{count}, #{name}, #{to}, #{from}, #{subject}, #{text})
    </insert>

    <update id="updateMailCount" parameterType="java.util.Map">
        update `mail` set `count`=#{count} where `num`=#{num}
    </update>
</mapper>
~~~

## Config

### 设置拦截器

检查当前网页session，若无直接将msg重转发到登录页，进行打印输出

~~~java
package com.postoffice.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录成功之后session中有用户信息，据此判断
        Object loginUser = request.getSession().getAttribute("loginUser");
        if(loginUser == null){
            request.setAttribute("msg", "没有权限，请先登录！");
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        } else{
            return true;
        }
    }
}
~~~

### MVC设置

注册拦截器，放行静态资源

~~~java
package com.postoffice.config;


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
                .excludePathPatterns("/index.html", "/", "/login",
                        "/css/**", "/icons/**", "/img/**",
                        "/bootstrap/**", "/sweetalert/**");
    }
}
~~~

### druid设置

~~~java
package com.postoffice.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
~~~

## Controller

### 数据库测试

采用spring-data集合的template进行crud操作和`RestController`进行数据库链接测试

~~~java
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
~~~

### MailController

`/send`

1. 创建线程，开启线程
2. 将线程录入Office（Map）
3. 将mail信息录入数据库

~~~java
@RequestMapping("/send")
public String send(@RequestParam("name")String name,
                   @RequestParam("to")String to,
                   @RequestParam("subject")String subject,
                   @RequestParam("text")String text){
    Mail mail = new Mail(name, to, subject, text);
    Mail m = PostOffice.send(mail);
    //System.out.println(m.getNum() + m.getFrom());
    mailMapper.addMail(m);
    return "redirect:/main";
}
~~~

每次回到`/mail`，从office中拿取mail信息，返回前端

~~~java
@RequestMapping("/main")
public String main(Model model){
    model.addAttribute("postmen", PostOffice.getPostmen());
    return "main";
}
~~~

每次登录`/login`成功，调用flush函数同步数据库数据，同时更新数据库中邮件发送次数

注销账号`/logout`，删除掉session中`loginUser`，返回index

~~~java
@RequestMapping("/login")
public String login(@RequestParam("username") String username,
                    @RequestParam("password") String password,
                    Model model, HttpSession session){
    if(username.equals("") || password.equals("")){
        model.addAttribute("msg", "用户名或密码不能为空");
        return "index";
    }
    if(!password.equals("123456")){
        model.addAttribute("msg", "密码错误");
    }
    session.setAttribute("loginUser", username);
    List<Mail> mails = mailMapper.queryMailList();
    PostOffice.flush(mails);
    //每次登录更新一遍每封邮件发送的次数
    for(Mail mail: mails){
        int count = PostOffice.getPostman(mail.getNum()).getMail().getCount();
        if(mail.getCount() != count){
            System.out.println("开始修改当前邮件发送次数");
            Map<String, Integer> map = new HashMap<>();
            map.put("num", mail.getNum());
            map.put("count", count);
            mailMapper.updateMailCount(map);
        }
    }
    //首次启动，开始发送所有邮件，启动所有线程
    if(firstStart){
        PostOffice.beginWork();
        firstStart = false;
    }
    model.addAttribute("postmen", PostOffice.getPostmen());
    //        for(Postman p: PostOffice.getPostmen()){
    //            System.out.println(p.getMail().getNum() + p.getMail().getName());
    //        }
    return "main";
}

@RequestMapping("/logout")
public String logout(HttpSession session){
    session.removeAttribute("loginUser");
    return "index";
}
~~~

删除邮件，通过路径中num分别在office和db中删除数据，并停止线程

~~~java
@RequestMapping("/drop/{num}")
public String drop(@PathVariable("num")Integer num){
    PostOffice.remove(num);
    mailMapper.removeMail(num);
    return "redirect:/main";
}
~~~

完整代码

`firstStart`用于判断是否第一次启动，开启所有线程

~~~java
package com.postoffice.controller;


import com.postoffice.mapper.MailMapper;
import com.postoffice.service.PostOffice;
import com.postoffice.vo.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MailController {

    private boolean firstStart = true;

    private MailMapper mailMapper;
    @Autowired
    public void setMailMapper(MailMapper mailMapper){
        this.mailMapper = mailMapper;
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       Model model, HttpSession session){
        if(username.equals("") || password.equals("")){
            model.addAttribute("msg", "用户名或密码不能为空");
            return "index";
        }
        if(!password.equals("123456")){
            model.addAttribute("msg", "密码错误");
        }
        session.setAttribute("loginUser", username);
        List<Mail> mails = mailMapper.queryMailList();
        PostOffice.flush(mails);
        //每次登录更新一遍每封邮件发送的次数
        for(Mail mail: mails){
            int count = PostOffice.getPostman(mail.getNum()).getMail().getCount();
            if(mail.getCount() != count){
                System.out.println("开始修改当前邮件发送次数");
                Map<String, Integer> map = new HashMap<>();
                map.put("num", mail.getNum());
                map.put("count", count);
                mailMapper.updateMailCount(map);
            }
        }
        //首次启动，开始发送所有邮件，启动所有线程
        if(firstStart){
            PostOffice.beginWork();
            firstStart = false;
        }
        model.addAttribute("postmen", PostOffice.getPostmen());
//        for(Postman p: PostOffice.getPostmen()){
//            System.out.println(p.getMail().getNum() + p.getMail().getName());
//        }
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "index";
    }

    @RequestMapping("/send")
    public String send(@RequestParam("name")String name,
                       @RequestParam("to")String to,
                       @RequestParam("subject")String subject,
                       @RequestParam("text")String text){
        Mail mail = new Mail(name, to, subject, text);
        Mail m = PostOffice.send(mail);
        //System.out.println(m.getNum() + m.getFrom());
        mailMapper.addMail(m);
        return "redirect:/main";
    }

    @RequestMapping("/main")
    public String main(Model model){
        PostOffice.flush(mailMapper.queryMailList());
        model.addAttribute("postmen", PostOffice.getPostmen());
        return "main";
    }

    @RequestMapping("/drop/{num}")
    public String drop(@PathVariable("num")Integer num){
        PostOffice.remove(num);
        mailMapper.removeMail(num);
        return "redirect:/main";
    }
}
~~~

## 前端

采用thymeleaf进行前后端交互

开发阶段令缓存为false，使每次重启后更改立即生效，否则缓存会引发各种bug猜想，开始折磨

application.yml

~~~yml
spring:
	thymeleaf:
    	cache: false
~~~

通过路径传参，后端进行删除操作

通过`@{/drop/}+${p.getMail().getNum()}`这种方式传入

~~~html
<div class="text-right">						    	
    <a th:href="@{/drop/}+${p.getMail().getNum()}" class="card-more" data-toggle="read" data-id="1">
        <i class="ion-ios-arrow-left"></i>
        删除
        <i class="ion-ios-arrow-right"></i>
    </a>
</div>
~~~

登陆页面`index.html`

~~~html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        a {
            text-decoration: none;
        }

        input,
        button {
            background: transparent;
            border: 0;
            outline: none;
        }

        body {
            height: 100vh;
            background: linear-gradient(#141e30, #243b55);
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 16px;
            color: #03e9f4;
        }

        .loginBox {
            width: 400px;
            height: 364px;
            background-color: #0c1622;
            margin: 100px auto;
            border-radius: 10px;
            box-shadow: 0 15px 25px 0 rgba(0, 0, 0, .6);
            padding: 40px;
            box-sizing: border-box;
        }

        h2 {
            text-align: center;
            color: aliceblue;
            margin-bottom: 30px;
            font-family: 'Courier New', Courier, monospace;
        }

        .item {
            height: 45px;
            border-bottom: 1px solid #fff;
            margin-bottom: 40px;
            position: relative;
        }

        .item input {
            width: 100%;
            height: 100%;
            color: #fff;
            padding-top: 20px;
            box-sizing: border-box;
        }

        .item input:focus+label,
        .item input:valid+label {
            top: 0px;
            font-size: 2px;
        }

        .item label {
            position: absolute;
            left: 0;
            top: 12px;
            transition: all 0.5s linear;
        }

        .btn {
            padding: 10px 20px;
            margin-top: 30px;
            color: #03e9f4;
            position: relative;
            overflow: hidden;
            text-transform: uppercase;
            letter-spacing: 2px;
            left: 35%;
        }

        .btn:hover {
            border-radius: 5px;
            color: #fff;
            background: #03e9f4;
            box-shadow: 0 0 5px 0 #03e9f4,
            0 0 25px 0 #03e9f4,
            0 0 50px 0 #03e9f4,
            0 0 100px 0 #03e9f4;
            transition: all 1s linear;
        }

        .btn>span {
            position: absolute;
        }

        .btn>span:nth-child(1) {
            width: 100%;
            height: 2px;
            background: -webkit-linear-gradient(left, transparent, #03e9f4);
            left: -100%;
            top: 0px;
            animation: line1 1s linear infinite;
        }

        @keyframes line1 {

            50%,
            100% {
                left: 100%;
            }
        }

        .btn>span:nth-child(2) {
            width: 2px;
            height: 100%;
            background: -webkit-linear-gradient(top, transparent, #03e9f4);
            right: 0px;
            top: -100%;
            animation: line2 1s 0.25s linear infinite;
        }

        @keyframes line2 {

            50%,
            100% {
                top: 100%;
            }
        }

        .btn>span:nth-child(3) {
            width: 100%;
            height: 2px;
            background: -webkit-linear-gradient(left, #03e9f4, transparent);
            left: 100%;
            bottom: 0px;
            animation: line3 1s 0.75s linear infinite;
        }

        @keyframes line3 {

            50%,
            100% {
                left: -100%;
            }
        }

        .btn>span:nth-child(4) {
            width: 2px;
            height: 100%;
            background: -webkit-linear-gradient(top, transparent, #03e9f4);
            left: 0px;
            top: 100%;
            animation: line4 1s 1s linear infinite;
        }

        @keyframes line4 {

            50%,
            100% {
                top: -100%;
            }
        }
    </style>
</head>

<body>
<div class="loginBox">
    <h2>login</h2>
    <form th:action="@{/login}" method="post">
        <div class="item">
            <input type="text" required name="username" id="username">
            <label for="username">username</label>
        </div>
        <div class="item">
            <input type="password" required name="password" id="password">
            <label for="password">password</label>
        </div>
        <button class="btn">submit
            <span></span>
            <span></span>
            <span></span>
            <span></span>
        </button>
    </form>
</div>
</body>
</html>
~~~

主页`main.html`

~~~html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta charset="utf-8">
		<title>Post Office</title>
		<link rel="stylesheet" href="icons/css/ionicons.min.css">
		<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="sweetalert/dist/sweetalert.css">
		<link rel="stylesheet" href="css/stisla.css">

		<style>
			body{
				background-color: black;
			}

			#footer p{
				color: white;
			}

			#footer h2{
				color: white;
			}
		</style>
	</head>

	<body>

		<!--导航栏-->>
		<nav class="navbar navbar-expand-lg main-navbar" style="position: fixed; top: 0; background-color: black;">
			<div class="container-fluid">
				<div class="navbar-brand">
				  <h4 style="color: white; font-family: Georgia, 'Times New Roman', Times, serif">
					  <a th:href="@{/druid}" style="color: white; text-decoration: none"><span class="bold">NorthBoat's Post Office</span></a>
				  </h4>
				</div>

				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon">
						<i class="ion-navicon"></i>
					</span>
				</button>

				<div class="collapse navbar-collapse" id="navbarNav">
					<div class="mr-auto"></div>
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link smooth-link" href="#home">Home</a></li>
						<li class="nav-item"><a class="nav-link smooth-link" href="#manage">Mails Manage</a></li>
						<li class="nav-item"><a class="nav-link smooth-link" href="#send">Send Mails</a></li>
						<li class="nav-item"><a class="nav-link smooth-link" th:href="@{/logout}">Logout</a></li>
					</ul>
				</div>
			</div>
		</nav>


		<!--Home页-->>
		<section class="hero bg-overlay" id="home" style="margin-bottom: 49px; margin-top: -49px;">
			<div class="text" style="margin-top: -24px;">
				<p class="lead">Welcome To NorthBoat's Post Office</p>
				<h1><span class="bold">常 </span>联 系 勿 <span class="bold">相 忘</span></h1>
				<div class="cta">
					<a href="#send" class="btn btn-primary smooth-link">Get Started</a>
					<div class="link" style="margin-top: 4%;">
						<a href="https://northboat.github.io/" style="text-decoration: none;">
							love is the one thing that transcends time and space
						</a>
					</div>
				</div>
			</div>
		</section>


		<!--管理页面-->
		<section class="padding bg-grey" id="manage">
			<div class="container">
				<h2 class="section-title" style="margin-top: 3%">Mail Manage</h2>
				<p class="section-lead text-muted">管理已收录的邮件</p>
				<div class="section-body">
					<div class="row col-spacing">

						<div class="col-12 col-md-6 col-lg-4" th:each="p:${postmen}">
							<article class="card">
								<div class="card-body">
								  	<div class="card-subtitle mb-2 text-muted">
										To <span th:text="${p.getMail().getTo()}"></span>
										from <span th:text="${p.getMail().getName()}"></span>
									</div>
							    	<h4 class="card-title"><span th:text="${p.getMail().getSubject()}"></span></h4>
							    	<p class="card-text" th:text="${p.getMail().getText()}"></p>
							    	<div class="text-right">						    	
								    	<a th:href="@{/drop/}+${p.getMail().getNum()}" class="card-more" data-toggle="read" data-id="1">
											<i class="ion-ios-arrow-left"></i>
											删除
											<i class="ion-ios-arrow-right"></i>
										</a>
							    	</div>
						    	</div>
						  	</article>
					  	</div>
					</div>
				</div>
			</div>
		</section>



		<section class="padding bg-grey" id="send" style="margin-top: -4%; padding-bottom: 12%;">
			<div class="container">
				<br><br><h2 class="section-title text-center">Send Mail</h2>
				<p class="section-lead text-center text-muted">Send friends u greetings, which will be sent once a week by u personal postman</p>
				<div class="section-body">				
					<div class="row col-spacing">
						<div class="col-12 col-md-6">
							<h2>stick u message ♂ in their heart</h2>
							<p class="text-muted">这发邮件，多是一件美事呀</p>
						</div>

						<div class="col-12 col-md-6">
							<div class="subscribe">
								<input type="button" class="btn btn-primary" onclick="resetForm()" value="清空">
							</div>
						</div>
						
						<div class="col-12 col-md-5">
							<p class="contact-text">If something wrong happened, please contact me</p>
							<ul class="contact-icon">
								<li><i class="ion ion-ios-telephone"></i> <div>+86 18630338418</div></li>
								<li><i class="ion ion-ios-email"></i> <div>1543625674@qq.com</div></li>
							</ul>
						</div>
						<div class="col-12 col-md-7">
							<form th:action="@{/send}" method="post" class="contact row" id="contact-form">

								<div class="form-group col-6">
									<input type="text" class="form-control" placeholder="Your Name" name="name">
								</div>
								<div class="form-group col-6">
									<input type="email" class="form-control" placeholder="the Email Address u wanna send" name="to" required>
								</div>
								<div class="form-group col-12">
									<input type="text" class="form-control" placeholder="Subject" name="subject" required>
								</div>
								<div class="form-group col-12">
									<textarea class="form-control" placeholder="Text Here" name="text" required></textarea>
								</div>
								<br>
								<div class="form-group col-12 mt-2">
									<button type="submit" class="btn btn-primary">
										Send Message
									</button>
								</div>
							</form>
						</div>

					</div>
				</div>
			</div>
		</section>



		<section class="padding" id="footer">
			<div class="container">
				<div class="row">
					<div class="col-12 col-md-4 col-sm-12">
						<div class="list-item">
							<div class="icon">
								<i class="ion-code"></i>
							</div>
							<div class="desc">
								<h2>javax.mail</h2>
								<p>
									萌新踩坑之作，查看更多作品
								</p>
								<a href="http://39.106.160.174:8080/" class="more">learn more</a>
							</div>
						</div>
					</div>
					<div class="col-12 col-md-4 col-sm-12">
						<div class="list-item">
							<div class="icon">
								<i class="ion-social-github"></i>
							</div>
							<div class="desc">
								<h2>Open Source</h2>
								<p>
									toys仓库中有本站源码
								</p>
								<a href="https://github.com/NorthBoat" class="more">learn more</a>
							</div>
						</div>
					</div>
					<div class="col-12 col-md-4 col-sm-12">
						<div class="list-item no-spacing">
							<div class="icon">
								<i class="ion-paintbrush"></i>
							</div>
							<div class="desc">
								<h2>Blog</h2>
								<p>
									本人学习博客，或许有所帮助
								</p>
								<a href="https://northboat.github.io/Blog/" class="more">learn more</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		<script src="js/jquery.min.js"></script>
		<script src="bootstrap/js/bootstrap.min.js"></script>
		<script>
			function resetForm(){
				document.getElementById("contact-form").reset();
			}		
		</script>
	</body>
</html>
~~~

## 一些问题

400错误，传参有问题，检查参数名

`java.lang.IllegalStateException: Failed to load property source from 'file:/E:/JavaWeb/springboot/PostOffice/target/classes/application.yml' `检查yml文件是否有不合规字符

`Couldn't connect to host, port: localhost, 25; timeout -1` 纯纯牛马

改用javax.mail，同样出现默认端口25无法使用的情况，加上以下设置修改端口号并且开启ssl连接

~~~java
Properties properties = new Properties();
properties = System.getProperties();
//设置第三方服务器
properties.setProperty("mail.smtp.host", "smtp.qq.com");
//开启密码验证
properties.setProperty("mail.smtp.auth", "true");
//设置超时时间
properties.setProperty("mail.smtp.timeout", "4000");
//开启debug
properties.setProperty("mail.debug", "true");
//开启ssl服务
properties.setProperty("mail.smtp.ssl.enable", "true");
//设置端口
properties.setProperty("mail.smtp.port", "465");
//设置ssl端口，必要的，否则连接不上
properties.setProperty("mail.smtp.socketFactory.port", "465");
properties.setProperty("mail.smtp.socketFactory.fallback", "false");
properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
~~~

 
