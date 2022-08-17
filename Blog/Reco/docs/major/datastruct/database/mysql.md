---
title: MySQL
date: 2021-5-1
tags:
  - DataBase
  - Middleware
categories:
  - WebApp
---

> 关系型数据库

## 下载与安装

### 初始化

1、登录mysql官网，点击侧边栏：download，选择对应版本的压缩包下载到本地

2、解压

3、在bin目录下初始化mysql：mysqld  --initialize

4、启动mysql服务器：net start mysql

5、确认用户身份：mysqladmin -u root -p password ——> 在初始化后，在bin的同级目录下会自动生成一个data目录，在data目录中的 DESKTOP-VCLA78O.err 文件中含有系统随机分配的初始密码，此时输入初始密码，再输入新密码，此时你的 mysql 算初始化完成

6、停止mysql服务器：net stop mysql

### 基础命令

0、linux启动/重启mysql

~~~bash
service mysqld restart
service mysql restart(某些版本)
~~~

1、启动命令行客户端：

mysqp -u root -p

~~~
本地机：
mysql -u root -p

远端机：
mysql -h ip -u root -p

再输入密码即可登录命令行客户端
~~~

2、创建数据库：

create database + 库名;

~~~sql
mysql> create database Book;

Query OK, 1 row affected (0.01 sec)xxxxxxxxxx creat database Book;mysql> create database Book;Query OK, 1 row affected (0.01 sec)
~~~

3、建表：

use + 库名 （无分号）

CREATE TABLE + 表名(表信息1, 表信息2, 表信息3...)；

~~~sql
//进入数据库
mysql> use Book
Database changed

//ctrl+c中断操作
mysql> CREATE TABLE bookList ()
    -> ^C

//建表
mysql> CREATE TABLE bookList(
    -> ISBN varchar(100) not null ,
    -> name varchar(100) CHARACTER SET gb2312,
    -> price float,
    -> chubanDate date,
    -> PRIMARY KEY (ISBN)
    -> );
Query OK, 0 rows affected (0.02 sec)

mysql>
~~~

4、添加、更新、查询......

insert into + 表名 values(数据信息...);

select * from + 表名；

~~~sql
mysql> select * from bookList
    -> ;
Empty set (0.01 sec)

mysql> insert into bookList values('7-302-01465-5','高等数学',28.27,'2020-12-10');
Query OK, 1 row affected (0.00 sec)

mysql> select * from bookList;
+---------------+----------+-------+------------+
| ISBN          | name     | price | chubanDate |
+---------------+----------+-------+------------+
| 7-302-01465-5 | 高等数学 | 28.27 | 2020-12-10 |
+---------------+----------+-------+------------+
1 row in set (0.00 sec)

mysql>
~~~

5、导入 .sql 文件中的SQL语句

sourse + 文件路径（无分号）

6、删除数据库或表

drop database + 库名;

drop table + 表名;

### 使用图形界面管理MySQL

下载解压 SQLyog

点击运行，注册：

~~~
名称：kuangshen
证书密匙：8d8120df-a5c3-4989-8f47-5afc79c56e7c
~~~

绑定本机MySql：

~~~
主机地址：localhost
用户名：root
密码：mysql登录密码
端口：3306
~~~

上一过程中登录密码可能会报错：plugin caching_sha2_password could not be loaded

这是由于密码格式不一致引起，只需执行下列命令重置密码即可

~~~sql
 mysql> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
~~~

### wget安装

#### 下载MySql源安装包

~~~bash
wget http://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm
~~~

#### 安装mysql源

~~~bash
yum -y install mysql57-community-release-el7-11.noarch.rpm
~~~

#### 安装mysql服务器

~~~bash
yum install mysql-community-server
~~~

在这一步，如果之前安装过mysql安装包，将报错：

错误：软件包：mysql-community-server-5.7.27-1.el7.x86_64 (mysql57-community) 需要：mysql-community-c

此时只需把之前的安装包删掉即可

~~~bash
//用yum搜索已安装软件
yum list installed
//删除掉已安装的
yum remove mysql-community-common.x86_64
~~~

再安装即可

#### 启动mysql服务

~~~bash
systemctl start  mysqld.service

systemctl status mysqld.service
~~~

#### 初始化数据库密码

1、查看初始密码

~~~bash
grep "password" /var/log/mysqld.log
~~~

2、登录

~~~bash
mysql -u root -p
~~~

3、修改密码

~~~bash
ALTER USER 'root'@'localhost' IDENTIFIED BY '*******';
~~~

4、此时修改密码一般报错不符合密码规范：

~~~bash
ERROR 1819 (HY000): Your password does not satisfy the current policy requirements
~~~

5、重新修改密码长度以及安全等级

~~~sql
//设置长度为4
set global validate_password_length=4;
//设置安全等级为low
set global validate_password_policy=0;
~~~

6、再重新修改密码即可

数据库授权

数据库没有授权，只支持localhost本地访问

~~~SQL
mysql>GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '123456' WITH GRANT OPTION;
~~~

远程连接数据库的时候需要输入用户名和密码

用户名：root

密码:123456

指点ip:%代表所有Ip,此处也可以输入Ip来指定Ip

输入后使修改生效还需要下面的语句

~~~sql
mysql>FLUSH PRIVILEGES;
~~~



## 操作数据库

### 创建删除数据库

~~~sql
create database is not exists `school`;

show create database `school`; #显示创建school的sql语句

use school;

drop database if exists `school`;
~~~

### 操作数据表

列属性

1. unsigned：无符号整数，该数据必不为负
2. zerofill：`0`填充的，十位数将从前往后填充`0`，如`312 ——> 0000000312`
3. auto_increment：自增的，可自定义设置起始值和步长
4. not null：非空，默认为可空
5. default：设置默认值
6. comment：解释

#### 创建/查看

~~~sql
create table if not exists `student`(
	`id` int(4) not null auto_increment comment '学号',
    `name` varchar(30) not null default '匿名' comment '姓名',
    `birthday` datetime default null comment '出生日期',
    primary key(`id`) #设置主键，莫忘辽
)engine = innodb default charset = utf8;

show tables;

show create table `student`;

#展示表列属性信息
desc `student`;
~~~

#### 修改/删除

~~~ sql
#修改表名
alter table `student` rename `teacher`;
#增加字段
alter table `teacher` add `age` int(11) not null

#修改表的字段
#1、重命名，同时修改约束，不加int(4)会报错
alter table `teacher` change `age` `age1` int(4);
#2、修改约束，modify只能修改约束
alter table `teacher` modify `age` varchar(9);

#删除表的字段
alter table `teacher` drop `age1`;

#删除表
drop table if exists `teacher`
~~~

- 所有删除创建操作尽量加上判断语句，以免报错

### 数据表引擎类型

| 区别       | MyISAM | InnoDB             |
| ---------- | ------ | ------------------ |
| 事务支持   | 不支持 | 支持               |
| 外键约束   | 不支持 | 支持               |
| 全文索引   | 支持   | 不支持             |
| 数据行锁定 | 不支持 | 支持               |
| 表空间大小 | 较小   | 较大，约为前者两倍 |

MyISAM更轻便（节约空间）、更快

InnoDB安全性高，支持事务处理，支持多表多用户操作（外键、多行同时操作）

- 数据库的本质仍是文件存储，储存再data文件夹下，一个文件夹对应一个数据库

MySQL引擎再物理文件上的区别

- InnoDB中数据库表中只有一个`*.frm`以及上级目录下的`ibdata1`文件
- MyISAM对应的文件
  - *.frm	表结构的定义文件
  - *.MYD 数据文件(data)
  - *.MYI   索引文件

表默认的字符集编码并不支持中文，要手动设置

~~~sql
default charset=utf8
~~~



## 数据管理

### 外键

在创建时绑定约束设置外键

~~~sql
create table if not exists `grade`(
	`gradeid` varchar(4) not null comment '年纪代号',
    `gradename` varchar(7) not null comment '年级中文',
    primary key(`gradeid`)
)engine = innodb default charset = utf8;

create table if not exists `student`(
	`id` int(4) not null auto_increment comment '学号',
    `name` varchar(30) not null default '匿名' comment '姓名',
    `gradeid` varchar(5) not null comment '年纪',
    `birthday` datetime default null comment '出生日期',
    primary key(`id`), #设置主键，莫忘辽
    #设置外键
    key 'FK_grade' (`gradeid`),
    #绑定约束：将当前表的gradeid绑定到grade表中的gradeid列
    constraint `FK_grade` foreign key ('gradeid') references `grade` (`gradeid`)
)engine = innodb default charset = utf8;
~~~

- 在删除有外键关系的表时，必须删除引用别人的表（从表），再删除主表

在创建后设置外键关系

~~~sql
create table if not exists `grade`(
	`gradeid` varchar(4) not null comment '年纪代号',
    `gradename` varchar(7) not null comment '年级中文',
    primary key(`gradeid`)
)engine = innodb default charset = utf8;

create table if not exists `student`(
	`id` int(4) not null auto_increment comment '学号',
    `name` varchar(30) not null default '匿名' comment '姓名',
    `gradeid` varchar(5) not null comment '年纪',
    `birthday` datetime default null comment '出生日期',
    primary key(`id`) #设置主键，莫忘辽
)engine = innodb default charset = utf8;

#修改已有表的外键关系
alter table `student`
add constraint `FK_gradeid` foreign key(`gradeid`) references `grade` (`gradeid`);
~~~

以上都是物理外键，数据库级别的外键不建议使用，避免数据库过多造成臃肿

最佳实践

- 数据库就是单纯的表，只用来存数据，只有行（数据）列（字段）
- 我们想使用多张表的数据，想使用外键，我们用程序去实现（一段逻辑）

### DML

参考SQL语句编写

## JDBC

> Java Data Base Connector

### 基本操作

#### 连接MySQL

导入jar包：mysql-connector-java-8.0.25.jar（百度下载即可）

调用 connector 中 api 即可

~~~java
public void connect(){
        try{
            //在 uri 处便定义了所要进入的数据库，此处为 Book
            String uri = "jdbc:mysql://localhost:3306/Book?user=root&password=011026&useSSL=false" + "&serverTimezone = GMT";
            String user = "root";
            String password = "011026";
            con = DriverManager.getConnection(uri, user, password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
~~~

当未设置密码时，还提供了另一种连接方式

~~~java
public void connect(){
        try{
            String uri = "jdbc:mysql://localhost:3306/Book?user=root&password=011026&useSSL=false" + "&serverTimezone = GMT";
            con = DriverManager.getConnection(uri);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
~~~

#### 关闭连接

~~~java
public void stop(){
        try{
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
~~~

#### 执行SQL

~~~java
//传入参数 table 为表的名字 （table要在库中，否则res.next()将返回false）
public void search(String table){
        try{
            //获取SQL语句
            Statement sql = con.createStatement();
            //获取整个表中结果
            ResultSet res = sql.executeQuery("SELECT * FROM " + table);
            //打印结果
            while(res.next()){
                for (int i = 1; i <= 4; i++) {
                    System.out.println(res.getString(i));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
}
~~~

注意：

- ResultSet 提供了很多返回函数，如 getInt(), getDouble()，这些数据都可以通过 getString() 以字符串的形式打印出来
- getString(int index) 中 index 表示数据在表中的列数
- next()函数将返回一个布尔值，即该行是否为空，同时将 res 移到下一行

### MySQL连接池

> 获取连接：getConnection()
>
> 归还连接：close()

#### C3P0

c3p0 + machange + jdbc驱动包

#### 配置文件

- c3p0-config.xml 或 c3p0.properties

  ~~~xml
  <c3p0-config>
      <default-config>
          <property name="driverClass">com.mysql.jdbc.Driver</property>
          <property name="jdbcUrl">jdbc:mysql://localhost:3306</property>
          <property name="user">root</property>
          <property name="password">123456</property>
  
          <!--初始化申请连接数量-->
          <property name="initialPoolSize">5</property>
          <!--最大连接数量-->
          <property name="maxPoolSize">10</property>
          <!--超时时间-->
          <property name="checkoutTimeout">3000</property>
      </default-config>
  
      <named-config name="otherc3p0">
          <property name="driverClass">com.mysql.jdbc.Driver</property>
          <property name="jdbcUrl">jdbc:mysql://localhost:3306/account</property>
          <property name="user">root</property>
          <property name="password">011026</property>
  
          <property name="initialPoolSize">5</property>
          <property name="maxPoolSize">8</property>
          <property name="checkoutTimeout">1000</property>
      </named-config>
  </c3p0-config>
  ~~~

- 路径：java项目在src目录下，web项目在src/main/resource目录下

#### 创建核心对象

~~~java
DataSource ds = new ComboPooledDataSource();
~~~

#### 获取连接

getConnection

~~~java
Connection con = ds.getConnection();
System.out.println(con);
~~~

#### 最大连接数量

~~~java
for(int i = 0; i < 10; i++){
    Connection con = ds.getConnection();
	System.out.println(con);
}
//当获取连接的数量超过xml文件中配置的最大数量时，将报错An attempt by a client to checkout a Connection has timed out.
~~~

#### 归还连接

~~~java
ds.close();
//将连接归还，此时连接数归零，又可以重新连接
~~~

####  自定义配置

在c3p0.xml中进行配置

~~~xml
<named-config name="database name">
    
</named-config>
~~~

在获取连接时传入参数(String databaseName)

~~~java
DataSource ds = new ComboPooledDataSource("databaseName");
//该语句将自动找到xml文件中对应数据库的配置
~~~

#### Druid

[Druid连接池的使用](https://www.cnblogs.com/chy18883701161/p/12594889.html)
