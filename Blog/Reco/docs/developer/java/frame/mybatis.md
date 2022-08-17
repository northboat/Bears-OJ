---
title: MyBatis
date: 2021-11-22
categories:
  - WebApp
tags:
  - Java
  - DataBase
---

## 简介

### Junit

> junit

java单元测试模板

### 什么是Mybatis

- MyBatis 是一款优秀的持久层框架
- 它支持自定义 SQL、存储过程以及高级映射
- MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作
- MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录
- MyBatis 本是apache的一个开源项目iBatis, 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis 。2013年11月迁移到Github。

## Mybatis程序

### 搭建环境

1、建库

~~~sql
CREATE DATABASE `mybatis`;

USE mybatis;

CREATE TABLE `user`(
    `id` INT(20) NOT NULL PRIMARY KEY,
    `name` VARCHAR(20) DEFAULT NULL,
    `pwd` VARCHAR(30) DEFAULT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `user`(`id`, `name`, `pwd`) VALUES
(1, 'NorthBoat', '123456');

INSERT INTO `user`(`id`, `name`, `pwd`) VALUES
(2, '张三', '654321'),
(3, '李四', '135792');
~~~

2、新建项目

干净的maven项目

3、导入依赖

~~~xml
<dependencies>
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/junit/junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>


    </dependencies>
~~~

### 创建模块

- 编写mybatis的核心配置文件

  ~~~xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="011026"/>
              </dataSource>
          </environment>
      </environments>
  </configuration>
  ~~~

  用IDEA连接mysql，先设置mysql时区

  ~~~sql
  若显示为system则未设置时区
  show variables like'%time_zone';  
  设置时区
  set global time_zone = '+8:00';
  ~~~

- 编写mybatis工具类

  ~~~java
  package com.utils;
  
  
  import org.apache.ibatis.io.Resources;
  import org.apache.ibatis.session.SqlSession;
  import org.apache.ibatis.session.SqlSessionFactory;
  import org.apache.ibatis.session.SqlSessionFactoryBuilder;
  
  import java.io.IOException;
  import java.io.InputStream;
  
  //sqlSessionFactory
  public class MybatisUtils {
  
      private static SqlSessionFactory sqlSessionFactory;
  
      static{
          try {
              //使用xml文件获取sqlSessionFactory对象
              String resource = "mybatis-config.xml";
              InputStream inputStream = Resources.getResourceAsStream(resource);
              sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  
      public static SqlSession getSqlSession(){
          return sqlSessionFactory.openSession();
      }
  }
  ~~~

### 编写代码

- 实体类

  ~~~java
  package com.pojo;
  
  public class User {
      private int id;
      private String name;
      private String pwd;
  
      public User(){};
  
      public User(int id, String name, String pwd){
          this.id = id;
          this.name = name;
          this.pwd = pwd;
      }
  
      public int getId() {
          return id;
      }
  
      public void setId(int id) {
          this.id = id;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public String getPwd() {
          return pwd;
      }
  
      public void setPwd(String pwd) {
          this.pwd = pwd;
      }
  }
  ~~~

- Dao接口

  ~~~java
  package com.dao;
  
  import com.pojo.User;
  
  import java.util.List;
  
  public interface UserDao {
      public List<User> getUserList();
  }
  ~~~

- 接口实现类：由原来的UserDaoImpl转变为一个Mapper配置文件

  ~~~xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <!--namespace绑定一个对应的Dao/Mapper接口-->
  <mapper namespace="com.dao.UserDao">
      <!--select查询语句-->
      <select id="getUserList" resultType="com.pojo.User">
          select * from mybatis.user;
      </select>
  </mapper>
  ~~~

- 原来的UserDaoImpl

  ~~~java
  package com.dao;
  
  import com.pojo.User;
  
  import java.util.List;
  
  public class UserDaoImpl implements UserDao {
      public List<User> getUserList() {
          //获取mysql连接
          return null;
          //写sql语句
          //执行并返回结果
      }
  }
  ~~~

### 测试

Junit测试代码

~~~java
package com.dao;

import com.pojo.User;
import com.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class UserMapperTest {
    @Test
    public void getUserListTest() {
        MyBatisUtils mbu = new MyBatisUtils();
        SqlSession sqlSession = mbu.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        for(User user: userMapper.getUserList()){
            System.out.println(user.toString());
        }

        sqlSession.close();
    }

    @Test
    public void getUserById(){
        MyBatisUtils mbu = new MyBatisUtils();
        SqlSession sqlSession = mbu.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        System.out.println(userMapper.getUserById(1).toString());

        sqlSession.close();
    }
}
~~~

maven导出资源问题

~~~xml
<build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
~~~

找不到Mapper.xml问题：要用 / 分割

~~~xml
<mappers>
    <mapper resource="com/dao/UserMapper.xml"/>
</mappers>
~~~

mysql驱动问题：其中Drive在8.x版本要加上cj

~~~xml
<environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="011026"/>
            </dataSource>
        </environment>
    </environments>
~~~

mapper书写问题：namespace填mapper路径，id后填方法名称，resuleType填实体类具体路径

~~~xml
<mapper namespace="com.dao.UserMapper">
    <select id="getUserList" resultType="com.pojo.User">
        select * from mybatis.user;
    </select>
</mapper>
~~~

### 字段映射

**resultMap**

~~~xml
<!-- 通用查询映射结果 -->
<resultMap id="BaseResultMap" type="com.seckill.pojo.User">
    <id column="id" property="id" />
    <result column="nickname" property="nickname" />
    <result column="password" property="password" />
    <result column="slat" property="slat" />
    <result column="head" property="head" />
    <result column="register_date" property="registerDate" />
    <result column="last_login_date" property="lastLoginDate" />
    <result column="login_count" property="loginCount" />
</resultMap>
~~~

**sql id**

~~~xml
<!-- 通用查询结果列 -->
<sql id="Base_Column_List">
    id, nickname, password, slat, head, register_date, last_login_date, login_count
</sql>
~~~

## MyBatis-Plus

### 逆向工程

### BaseMapper
