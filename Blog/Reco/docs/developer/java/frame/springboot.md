---
title: SpringBoot
date: 2021-11-13
categories:
  - WebApp
tags:
  - Java
---

## SpringBoot

### 映射

@RestController

- == @Controller+@ResponseBody

@Controller

@RequestMapping

- @PathVariable
- @RequestParam

@GetMapping

### 注入

> 需要在@Component或@Controller中注入

@Autowired

变量注入（不推荐）

~~~java
@Autowired
JdbcTemplate jdbcTemplate
~~~

构造器注入

~~~java
final UserDao userDao;

@Autowired
public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
}
~~~

set方法注入（推荐）

~~~java
//set方法注入
private JdbcTemplate jdbcTemplate;
@Autowired
public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate = jdbcTemplate;
}
~~~

@Bean



### 测试

@SpringBootTest

@Test

`junit`测试单元

@Configuration



## Thymeleaf

引入

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org"></html>
```

~~~xml
<!--thymeleaf依赖  -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
</dependency>
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-java8time</artifactId>
</dependency>
~~~

设置不缓存，修改立即生效，否则缓存将影响测试，部署时可改回

~~~yml
spring:
  thymeleaf:
    cache: false
~~~

判断并打印

~~~html
<div th:text="${msg}"><h1>cnm</h1></div>
~~~

提取公共元素

~~~html
<div th:insert="${commons/commons.html:topbar}"></div>
<div th:replace="${commons/commons.html:topbar}"></div>
~~~

传值

~~~html
<a th:href="@{/drop/}+${p.getMail().getNum()}" class="card-more" data-toggle="read" data-id="1"><i class="ion-ios-arrow-left"></i>删除<i class="ion-ios-arrow-right"></i></a>
~~~

## SpringDate

application.yml

> 密码是数字要加引号，否则会报错 errorCode 1045, state 28000 验证密码错误

> 默认的数据源为Hikari，最快
>
> 更改数据源为druid，日志监控，安全

~~~yml
spring:
  application:
    name: PostOffice
  datasource:
    username: root
    password: "011026"
    url: jdbc:mysql://39.106.160.174:3306/PostOffice?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    filters: stat,wall,log4j
~~~

pom.xml

~~~xml
<!--SpringDate-->
<!--jdbc-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<!--MySQL-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<!--日志log4j-->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

<!--druid德鲁伊数据源-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.21</version>
</dependency>

<!--测试-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
~~~

配置后台监控页面

~~~java
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
        properties.put("loginUsername", "admin");
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

log4j报错

~~~bash
log4j:WARN No appenders could be found for logger (org.apache.ibatis.logging.LogFactory).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
~~~

原因：未配置log4j.properties

~~~properties
log4j.rootLogger=debug, stdout, R

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout

# Pattern to output the caller's file name and line number.
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n

log4j.appender.R=org.apache.log4j.RollingFileAppender
log4j.appender.R.File=example.log

log4j.appender.R.MaxFileSize=100KB
# Keep one backup file
log4j.appender.R.MaxBackupIndex=5

log4j.appender.R.layout=org.apache.log4j.PatternLayout
log4j.appender.R.layout.ConversionPattern=%p %t %c - %m%n
~~~

使用原生jdbc进行CRUD

注意全局查找返回结果类型`List<Map<String, Object>>`，以及插入列名用飘号括起防止关键字造成sql语句报错

~~~java
@RestController
public class JDBCController {

    @Autowired
    JdbcTemplate jdbcTemplate;

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

整合mybatis

~~~xml
<!--mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
~~~

@Mapper

~~~java
//这个注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface MailMapper {

    List<Postman> queryPostmanList();

    int removePostman(int num);

    int addPostman(Postman postman);
}
~~~

@MapperScan

~~~java
@SpringBootApplication
public class PostOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostOfficeApplication.class, args);
    }
}
~~~

application.yml

~~~yml
#整合mybatis
mybatis:
  type-aliases-package: com.postoffice.vo
  mapper-locations: classpath:mybatis/mapper/*.xml
~~~

MailMapper

~~~java
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

MailMapper.xml，位于resources/mybatis/mapper

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

- 传递多个参数用Map封装，用`#{key}`取值



