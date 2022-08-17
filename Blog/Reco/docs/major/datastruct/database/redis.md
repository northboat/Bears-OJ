---
title: Redis
date: 2022-2-14
tags:
  - DataBase
  - Middleware
categories:
  - WebApp
---

> Key-Value型数据库

## 部署

上传安装包并放在/opt目录下

~~~java
mv redis-6.2.6.tar.gz /opt
~~~

解压

~~~bash
tar -zxvf redis-6.2.6.tar.gz
~~~

进入redis目录

~~~bash
cd redis-6.2.6.tar.gz
~~~

编译下载

~~~bash
make && make install
~~~

默认安装路径

~~~bash
/usr/local/bin
~~~

进入该目录，将redis.conf拷贝至当前目录，使用这个拷贝的文件进行配置启动

~~~bash
mkdir config
cp /opt/redis-6.2.6/redis.conf config
~~~

修改默认启动方式

~~~bash
cd config
vim redis.conf
#设置 daemonize 为 yes 保存并退出
~~~

在/usr/local/bin目录下启动服务，通过指定的配置文件启动

~~~bash
redis-server config/redis.conf
~~~

连接redis

~~~bash
redis-cli -p 6379 # -h 默认为本机
 
127.0.0.1:6379> ping
PONG
127.0.0.1:6379> set name NorthBoat
OK
127.0.0.1:6379> get name
"NorthBoat"
127.0.0.1:6379> get a
(nil)
127.0.0.1:6379> keys *
1) "name"
~~~

查看redis进程是否开启

~~~shell
ps -ef|grep redis

#查看后台java进程
ps -ef|grep java
jps -l -v
~~~

关闭redis服务

~~~java
127.0.0.1:6379> shutdown
not connected> exit
~~~

设置密码，在redis.conf中搜索requirepass，插入新行

~~~bash
# vim搜索：":/requirepass"，按寻找下一个
requirepass 123456
~~~

在登录redis后验证密码即可操作数据

~~~bash
auth "123456"

#或在启动时验证，这样并不安全，因为密码可见
redis-cli -p 6379 -a 123456
~~~

## 基本使用

| 命令                | 解释                                              |
| ------------------- | ------------------------------------------------- |
| set name NorthBoat  | 设置键值对{name: NorthBoat}                       |
| get name            | 获取键的值                                        |
| del name            | 删除键值对                                        |
| exists name         | 检查键是否存在                                    |
| expire name seconds | 为键设置过期时间，单位s（注意在设置完键值后使用） |
| move name db        | 将当前库的name转移到指定的数据库db中              |
| ttl name            | 返回name剩余时间，单位为s                         |
| persist name        | 移除name的过期时间                                |
| rename name n       | 将键name改名为n                                   |
| flushdb             | 刷新redis，清除所有键值                           |

## 基本数据类型

### String

~~~sql
127.0.0.1:6379> set name NorthBoat
OK
127.0.0.1:6379> get name
"NorthBoat"

# 批量设置
127.0.0.1:6379> mset id 4 grade 11
OK
# 批量获取
127.0.0.1:6379> mget id grade
1) "4"
2) "11"
~~~

从`Redis`中取出的字符串在`Java`程序中会显示为`"NorthBoat"`的形式，要加转义字符`\"`表示双引号

### List

### Set

### Hash

### Zset

## 特殊数据类型

### Geospatial

### Hyperloglog

### Bitmap

## 事物

事物：一组命令的集合，如多条SQL，入队出队依次执行。事物中的所有命令都会被序列化，会按顺序执行

~~~
==== 队头 set1 set2 set3 ... 队尾 ====
~~~

ACID原则：原子性、一致性、隔离性、持久性

redis单条命令保证原子性，但redis事物不保证原子性

redis事务没有隔离级别的概念，所有命令在事务中并没有直接被执行，只有发起执行命令的时候才会执行：exec

redis事务的一次正常执行：

- 开启事务：multi
- 命令入队：依次写命令
- 执行事务：exec

~~~bash
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> set k1 v1
QUEUED
127.0.0.1:6379(TX)> set k2 v2
QUEUED
127.0.0.1:6379(TX)> set k3 v3
QUEUED
127.0.0.1:6379(TX)> get k2
QUEUED
127.0.0.1:6379(TX)> exec
1) OK
2) OK
3) OK
4) "v2"
~~~

- 放弃事务：discard（在multi后、exec前执行，队列中所有命令都不会执行）

事务报错：

- 编译型异常：代码有问题，编译都过不了，整个事务都不会被执行

  ~~~bash
  127.0.0.1:6379> multi
  OK
  127.0.0.1:6379(TX)> set k1 v1
  QUEUED
  127.0.0.1:6379(TX)> set k2 v2
  QUEUED
  127.0.0.1:6379(TX)> getset k3
  (error) ERR wrong number of arguments for 'getset' command
  127.0.0.1:6379(TX)> exec
  (error) EXECABORT Transaction discarded because of previous errors.
  
  127.0.0.1:6379(TX)> get k1
  (nil)
  ~~~

- 运行时异常：如果事务队列中某个命令存在某个问题，类似与RuntimeException，在执行事务时，其他命令可以正常执行，错误命令抛出异常

  ~~~bash
  127.0.0.1:6379> set k1 "v1"
  OK
  127.0.0.1:6379> multi
  OK
  127.0.0.1:6379(TX)> incr k1
  QUEUED
  127.0.0.1:6379(TX)> set k2 v2
  QUEUED
  127.0.0.1:6379(TX)> get k2
  QUEUED
  127.0.0.1:6379(TX)> exec
  
  1) (error) ERR value is not an integer or out of range
  2) OK
  3) "v2"
  127.0.0.1:6379>
  ~~~

锁：redis可以实现乐观锁，使用watch实现

## Java-Redis

### Jedis

#### 导入依赖

~~~xml
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>4.1.1</version>
</dependency>
~~~

#### 基本使用

方法名与`Redis`命令保持一致，返回值同样保持一致，告知操作结果

~~~java
public class Listener {
    
    private Jedis jedis;
    private String url = "39.106.160.174";
    private int port = 6379;


    private int initRedis(){
        try{
            jedis = new Jedis(url, port);
            jedis.auth("011026");
        } catch (redis.clients.jedis.exceptions.JedisConnectionException e){
            // 若没连接上，将报此错
            return -1;
        }
        return 200;
    }
    
    public void test(){
        String str = jedis.set("1", "hahaha"); //OK
        long r1 = jedis.expire("1", 3); //1
        long r2 = jedis.del("1"); //1
        long r3 = jedis.del("2"); //0
        long r4 = jedis.del("3"); //0
        jedis.close();
    }
}
~~~

#### 事务

`Transaction multi = jedis.multi()`

~~~java
public class Listener {
    
    private Jedis jedis = new Jedis("39.106.160.174", 6379);
    
    public void test(){
        Transaction multi = jedis.multi();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sad", "NorthBoat");
        jsonObject.put("hello", "hahaha");

        String jsonString = jsonObject.toString();
        try{
            multi.set("user1", jsonString);
            multi.set("user2", jsonString);
            int i = 1/0; //当出现错误，捕获异常后discard，整个事务全不会生效
            multi.exec();
        }catch (Exception e){
            multi.discard();
            e.printStackTrace();
        }finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.del("user1");
            jedis.del("user2");
            jedis.close();
        }
    }
    
    public static void main(String[] args) {
        Listener listener = new Listener();
        listener.initRedis();
        listener.test();
    }
}
~~~

### SprintBoot整合

#### 预备知识

在`SpringData`项目中

在`SpringBoot2.x`后，`spring-boot-starter-data-redis`的底层实现，`jedis`被替换为了`lettuce`

- jedis：采用的直连，多个线程操作的话，是不安全的，为了避免不安全，使用Jedis Pool，像BIO模式
- lettuce：采用netty，实例可以在多个线程中进行共享，不存在线程不安全的情况，可以减少线程数量，更像NIO模式，性能更高

> SpringBoot所有的配置类，都有一个自动配置类
>
> 在外部依赖中找到Maven: org.springframework.boot:spring-boot-test-autoconfig
>
> 在其META-INF中通过spring.factories找到redis的自动配置类（ctrl+f搜索）
>
> 自动类都会绑定一个 properties 配置文件

通过上述方法找到`RedisAutoConfiguration.java`

~~~java
@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({RedisOperations.class})
@EnableConfigurationProperties({RedisProperties.class})
@Import({LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class})
public class RedisAutoConfiguration {
    public RedisAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(
        name = {"redisTemplate"}
    )
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
~~~

- `@ConditionalOnMissingBean(name = {"redisTemplate"})`该注释意为，当这个Bean不存在时，被其修饰的类就生效。也就是明确告诉我们可以自己定义一个`redisTemplate`（原有的泛型为两个Object，不方便）
- 默认的`template`没有过多配置，redis对象一定是需要序列化的，用到了类似NIO这样的异步技术，通通需要序列化

`ctrl+b`找到`RedisProperties.class`，该类详细记录了自动配置信息，以下省掉了两百多行方法

~~~java
@ConfigurationProperties(
    prefix = "spring.redis"
)
public class RedisProperties {
    private int database = 0;
    private String url;
    private String host = "localhost";
    private String password;
    private int port = 6379;
    private boolean ssl;
    private Duration timeout;
    private String clientName;
    private RedisProperties.Sentinel sentinel;
    private RedisProperties.Cluster cluster;
    private final RedisProperties.Jedis jedis = new RedisProperties.Jedis();
    private final RedisProperties.Lettuce lettuce = new RedisProperties.Lettuce();
}
~~~

#### 整合测试

1、导入依赖

~~~xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
~~~

2、配置连接

~~~properties
spring.redis.host=39.106.160.174
spring.redis.port=6379
spring.redis.password="123456";
#密码要加""号，和mybatis一样
~~~

3、测试

~~~java
@SpringBootTest
class RemoteControllerServerApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("test", "sad");
        System.out.println(redisTemplate.opsForValue().get("test"));
        redisTemplate.delete("test");
    }
}
~~~

`redisTemplate`常用API

| 方法        | 注释       |
| ----------- | ---------- |
| opsForValue | 操作String |
| opsForList  | 操作List   |
| opsForSet   | 操作Set    |
| opsForHash  | 操作Hash   |
| opsForZSet  | 操作ZSet   |

同理有`opsForGeo`等等

#### 自定义Template

新建`config`包，参照`RedisTemplate`源码，注入`RedisTemplate`Bean，替换掉默认的`RedisTemplate`（看过源码得知其上有`@ConditionalOnClass`注解），简单将key修改为String类型

~~~java
@Configuration
public class RedisConfig {

    //编写我们自己的RedisTemplate
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
~~~

在企业开发中，所有的pojo都是要序列化的，最简单的即继承`Serializable`接口，默认使用JDK进行序列化

只有经过序列化的对象才能进行NIO网络传输，我们自定义的`template`，最重要的工作便是定义序列化规则，将未经过序列化的对象在`template`中序列化后成功传输

~~~java
package com.northboat.remotecontrollerserver.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {

    //一个固定的模板，在企业中可以直接使用，几乎包含了所有场景
    //编写我们自己的RedisTemplate
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> myRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //Json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //String序列化配置
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // key和Hash的key使用String序列化
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // value和Hash的value使用Jackson序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
~~~

这里将方法名修改为`myRedisTemplate`，防止与原模板重名

`@SuppressWarnings("all")`作用只是表明不提醒警告，看着舒服一点

测试

~~~java
package com.northboat.remotecontrollerserver;

import com.northboat.remotecontrollerserver.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RemoteControllerServerApplicationTests {

    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        User user = new User("哈哈哈", 17);
        redisTemplate.opsForValue().set("user", user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
}
~~~

`@Qualifier`注解用于区分同类型`Bean`，默认是`by type`，修饰后`by id`，在区分方法名后也可以直接定义

~~~java
@Autowired
private RedisTemplate myRedisTemplate;
~~~





