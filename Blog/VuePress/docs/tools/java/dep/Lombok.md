# Lombok

## 一、导入依赖

~~~xml
<!--lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
~~~

## 二、基本使用

`@Data`：包含了所有成员的`setter`和`getter`，常用于与数据库对接的实体类

`@EqualsAndHashCode`：自动生成`equals`和`hashCode`方法

~~~java
package com.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nickname;
    private String password;
    private String slat;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
~~~

`@Setter/@Getter`

~~~java
@Setter
@Getter
public class Example{
    private int id;
    private String name;
}
~~~

`@AllArgsConstructor/@NoArgsConstructor`：全参、无参构造器

~~~java
@AllArgsConstructor
@NoArgsConstructor
public class Example{
    private int id;
    private String name;
}
~~~







