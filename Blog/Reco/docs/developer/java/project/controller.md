---
title: 基于Redis的远程控制器
date: 2022-2-24
tags:
  - Web
  - Java
categories:
  - WebApp
---

> 基于Redis的消息分发

## Listener

> 基于Jedis实现消息共享

### 导入依赖

~~~xml
<dependencies>
    <!--jedis依赖-->
    <!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>4.0.1</version>
    </dependency>

    <!--一部分slf4日志，jedis依赖于此-->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-nop</artifactId>
        <version>1.7.6</version>
    </dependency>

</dependencies>
~~~

### listener.java

> 与远端`Redis`通信，设置每4s循环一次

- getStatus(String token)/getStatus()：获取token的值(String类型)，没有返回null
- setToken()：设置Token以及作用时间，用Scanner获取用户输入，用一个while循环捕捉不合规范的输入，并要求重新输入，返回值为`int`，用于判断是否设置成功
- close()：关闭jedis连接
- flush()：刷新Redis数据库
- listening()：一个while循环，首次进入打印当前状态，状态即Redis中当前Token对应的值，当收到不同于`alive`的值或值为`null`时退出循环，返回值`status`，交由`Executor`处理

~~~java
package com;


import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Listener {

    private String token;
    private Jedis jedis;
    private String url = "39.106.160.174";
    private int port = 6379;


    public void setHost(String url, int port){
        this.url = url;
        this.port = port;
    }

    private int initRedis(){
        try{
            jedis = new Jedis(url, port);
            jedis.auth("011026");
        } catch (redis.clients.jedis.exceptions.JedisConnectionException e){
            return -1;
        }
        return 200;
    }



    // 获取当前用户Token状态
    public String getStatus(){
        return jedis.get(this.token);
    }
    // 获取传入的Token状态，在设置Token时使用，用于避免重复设置
    public String getStatus(String token){
        return jedis.get(token);
    }



    // 监听当前Token状态，间隔5s
    public String listening() throws InterruptedException{
        boolean firstEnter = true;
        String status;
        while(true) {
            // getStatus获取Token的value
            status = getStatus();
            if(firstEnter){
                System.out.println("当前状态: " + status);
                firstEnter = false;
            }
            // 若不为alive，返回当前状态，交由Executor处理
            if(status==null || !status.equals("\"alive\"")){
                break;
            }
            // 测试用，模拟收到命令
            //jedis.set(token, "ipconfig");
            TimeUnit.SECONDS.sleep(4);
        }
        return status;
    }


    // 将查询信息返回到Redis中，给用户去读
    public void response(String resp){
        jedis.set(token, resp);
    }


    // 设置Token
    public int setToken() {
        // 初始化redis连接，并检查状态，主动catch网络异常
        int init = initRedis();
        if(init != 200){ return init; }

        // 准备获取用户输入
        Scanner scanner = new Scanner(System.in);
        String token;

        // 设置Token
        System.out.print("请设置你的Token: ");
        while(true) {
            token = scanner.nextLine();
            String status = getStatus(token);
            if(status == null){
                break;
            }
            System.out.print("该Token已被占用，请重试: ");
        }
        this.token = token;
        String set = jedis.set(this.token, "\"alive\"");
        // 若设置失败，直接返回，退出程序
        if(!set.equals("OK")){
            return 501;
        }
        //设置默认时间为1min，防止用户突然退出一直占用Token
        jedis.expire(this.token, 60);

        // 设置生效时间，循环处理输入格式异常
        int seconds;
        System.out.print("请设置Token生效时间(单位: 分钟)(请在一分钟之内完成设置): ");
        while(true){
            String sec = scanner.nextLine();
            try{
                seconds = Integer.parseInt(sec);
            }catch (NumberFormatException e){
                System.out.print("输入格式有误，请重新输入: ");
                continue;
            }
            break;
        }

        long expired = jedis.expire(this.token, seconds*60);
        // 若设置时间返回不为1，即失败，返回502，交给Checker处理
        if(expired != 1){
            return 502;
        }

        System.out.println("设置成功!");
        return 200;
    }
    
    // 关闭连接
    public void close(){
        jedis.close();
    }

    // 刷新数据库
    public void flush(){
        jedis.flushDB();
    }
}
~~~

### Executor.java

> 执行类，处理`Listener`返回的不同状态

- shutdown()：关机`Runtime.getRuntime().exec("shutdown -s -t 10")`
- clean()/interrupted()：提示用户信息，然后`System.exit()`
- ipConfig()：执行`ipconfig`，获取控制台输出，截取字符串ip并返回
- getIp(String config)：截取字符串
- exec(String status)：集合以上功能，处理不同`status`

~~~java
package com;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Executor {

    // 关机
    private static void shutdown(){
        try{
            System.out.println("当前状态: \"shutdown\"");
            System.out.println("收到关机指令，已清除Token，十秒后将关闭计算机...");
            Runtime.getRuntime().exec("shutdown -s -t 10");
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("bye");
        System.exit(1);
    }

    // 异常中断
    private static void interrupted(){
        System.out.println("消息丢失，或Token已失效，即将退出程序...");
        System.exit(0);
    }

    // 获取ip地址
    private static String ipConfig(){
        try{
            System.out.println("当前状态: \"sending Ipv4\"");
            Process pro = Runtime.getRuntime().exec("ipconfig");
            InputStream in = pro.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            //读取缓存
            byte[] buffer = new byte[2084];
            int length = 0;
            while((length = in.read(buffer)) != -1) {
                bos.write(buffer, 0, length);//写入输出流
            }
            in.close();//读取完毕，关闭输入流
            String config = new String(bos.toByteArray());

            //获取ipv4地址字符串并返回
            return getIp(config);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    // 从ip信息中截取ipv4地址
    public static String getIp(String config){
        int index = config.indexOf("IPv4")+7;
        StringBuilder ip = new StringBuilder();
        boolean found = false;
        while(true){
            char c = config.charAt(index++);
            //System.out.print(c);

            //找到ip地址的起点，开始录入字符串
            if(c==':' && !found){
                found = true;
                continue;
            }

            //当在起点后，既不是数字，也不是'.'，也不是空格，说明已经过了结尾，直接退出
            if(found && !Character.isDigit(c) && c!='.' && c!=' ') {
                //System.out.println(c);
                break;
            }

            //录入ip地址
            if(found){
                ip.append(c);
            }

        }
        return "\"" + ip.toString().trim() + "\"";
    }

    // 机主清除Token
    private static void clean(){
        System.out.println("当前状态: \"Token has been cleaned\"");
        System.out.println("Token已被人为清除，即将退出程序");
        System.exit(1);
    }


    // 这里只告知服务端收到命令，重置操作给服务端执行
    public static void exec(String status, Listener listener){
        if(status == null){
            listener.close();
            interrupted();
        }else if(status.equals("\"shutdown\"")){
            //告知服务器已收到，继续执行之后操作
            listener.response("\"received\"");
            listener.close();
            shutdown();
        }else if(status.equals("\"ipconfig\"")){
            listener.response(ipConfig());
        }else if(status.equals("\"clean\"")){
            listener.response("\"received\"");
            listener.close();
            clean();
        }

        //System.out.print("即将退出程序...");
    }

}
~~~

### Checker.java

> 这个类很简单，就是根据set函数的返回值判断一些设置是否成功并作出相应提示，若为成功返回`false`并退出程序

~~~java
package com;

public class Checker {

    public static boolean checkSet(int set){
        if(set == 200){
            return true;
        }
        if(set == 501){
            System.out.println("Token设置失败，即将退出程序...");
        } else if(set == 502){
            System.out.println("生效期设置失败，或未在一分钟内完成操作，即将退出程序...");
        } else if(set == -1){
            System.out.println("请检查网络连接，即将退出程序...");
        }

        return false;
    }
}
~~~

### Main.java

- tip()：打印一些提示信息
- buffer()：线程休眠几秒，main函数中是一层while循环，listening函数中也是一层while循环，当listening收到指令退出循环后，main调用exec函数处理返回状态，这个时候要令外层循环等待一下，因为重置状态的操作在服务端执行，若直接继续执行，很有可能状态没有复原，重复执行上一条指令，这个地方设计有问题
- main()：用一层while套住listening，意在重复监听多条消息，而不是执行一次就退出程序，listenting是阻塞的，只有收到指令才会退出循环

~~~java
package com;

import java.util.concurrent.TimeUnit;

public class Main {

    private static void tip(){
        try{
            System.out.print("即将开始监听: 3 ");
            TimeUnit.SECONDS.sleep(1);
            System.out.print("2 ");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("1...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("正在监听，请勿关闭程序，或造成消息丢失");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private static void buffer(){
        try{
            TimeUnit.SECONDS.sleep(12);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws InterruptedException {
        Listener listener = new Listener();

        int set = listener.setToken();
        if(!Checker.checkSet(set)){ return; }

        // 打印提示信息
        tip();

        // 开始监听
        //noinspection InfiniteLoopStatement
        while(true){
            // listening是阻塞的，只有收到命令或Token失效才会继续执行
            String status = listener.listening();
            Executor.exec(status, listener);

            // 防止主程序继续循环，等一等服务端信息
            buffer();
        }
    }
}

~~~

## Server

> SpringBoot集成Redis

### 导入依赖

~~~xml
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
~~~

### config

配置自定义`RedisTemplate`，定义序列化规则，注入`Bean`

`RedisConfig.java`

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

### utils

注入自定义的`RedisTemplate`，封装`Redis`的一些基本功能，有待完善

`RedisUtil.java`

~~~java
package com.northboat.remotecontrollerserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;


// 待完善
@Component
@SuppressWarnings("all")
public class RedisUtil {

    private RedisTemplate myRedisTemplate;
    @Autowired
    public void setMyRedisTemplate(RedisTemplate myRedisTemplate){
        this.myRedisTemplate = myRedisTemplate;
    }


    //设置有效时间，单位秒
    public boolean expire(String key, long time){
        try{
            if(time > 0){
                myRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //获取剩余有效时间
    public long getExpire(String key){
        return myRedisTemplate.getExpire(key);
    }

    //判断键是否存在
    public boolean hasKey(String key){
        try{
            return myRedisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //批量删除键
    public void del(String... key){
        if(key != null && key.length > 0){
            if(key.length == 1){
                myRedisTemplate.delete(key[0]);
            } else {
                myRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //获取普通值
    public Object get(String key){
        return key == null ? null : myRedisTemplate.opsForValue().get(key);
    }

    //放入普通值
    public boolean set(String key, Object val){
        try{
            myRedisTemplate.opsForValue().set(key, val);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //放入普通缓存并设置时间
    public boolean set(String key, Object val, long time){
        try{
            if(time > 0){
                myRedisTemplate.opsForValue().set(key, val, time, TimeUnit.SECONDS);
            } else { // 若时间小于零直接调用普通设置的方法放入
                this.set(key, val);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //值增
    public long incr(String key, long delta){
        if(delta < 0){
            throw new RuntimeException("递增因子必须大于零");
        }
        return myRedisTemplate.opsForValue().increment(key, delta);
    }

    //值减
    public long decr(String key, long delta){
        if(delta < 0){
            throw new RuntimeException("递增因子必须大于零");
        }
        return myRedisTemplate.opsForValue().decrement(key, delta);
    }



    //============Map=============

    // 获取key表中itme对应的值
    public Object hget(String key, String item){
        return myRedisTemplate.opsForHash().get(key, item);
    }

    // 获取整个Hash表
    public Map hmget(String key){
        return myRedisTemplate.opsForHash().entries(key);
    }

    // 简单设置一个Hash
    public boolean hmset(String key, Map<String, Object> map){
        try{
            myRedisTemplate.opsForHash().putAll(key, map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 设置一个Hash，并设置生效时间，调用上面的设置key生效时间的方法
    public boolean hmset(String key, Map<String, Object> map, long time){
        try{
            myRedisTemplate.opsForHash().putAll(key, map);
            if(time > 0){
                this.expire(key, time);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }


    // 像一张Hash表中添加键值，若表不存在将创建
    public boolean hset(String key, String item, Object val){
        try{
            myRedisTemplate.opsForHash().put(key, item, val);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
~~~

### service

注入`RedisUtil`，使用`Redis`的一些基本api实现用户功能

`CommandService.java`：接口

~~~java
package com.northboat.remotecontrollerserver.service;

public interface CommandService {
    String shutdown(String token);

    String clean(String token);

    String ipconfig(String token);
}
~~~

`CommandServiceImpl.java`：功能实现

- 所有命令进来，首先要判断`Token`是否存在，若不存在，直接返回不存在或已失效信息(String)

- 第二，若`Token`存在，需要判断其值，也就是状态是否为`alive`，若不为`alive`，说明有其他命令正在执行过程中，直接返回冲突信息，注意这里再加一层是否为空的判断，防止是`shutdown/clean`命令执行结束清空了`Token`

- 两层判断后再进行业务处理，使用一个while循环去等待客户端反应，用一个很原始的计时器做简单的超时判断

  ~~~java
  long before = System.currentTimeMillis();
  while(true){
      long cur = System.currentTimeMillis();
      if(cur-before > 12000){
          return "超时";
      }
  }
  ~~~

具体的通信设计：

1. shutdown：网页端输入Token并通过判断后，设置`{Token: "shutdown"}`，开始等待客户端反应；（客户端每4s监听一次，会有适当延迟）客户端`get(token)`收到`shutdown`指令后，执行`shutdown`，再设置`{Token: "received"}`；服务端收到`received`，说明客户端已经执行了`shutdown`，删除`Token`，返回关机成功信息
2. clean：与shutdown类似，通过客户端发送的`received`判断是否接收到信息，收到才删除`Token`
3. ipconfig：服务端设置`{Token: "ipconfig"}`，等待相应；客户端收到`ipconfig`后查询ip地址，将ip作为值，即设置`{Token: IPv4}`，等待（buffer）服务端接收ip地址；当值不为`ipconfig`时说明客户端已经相应，接收`IPv4`地址并用`String`记录下来，重置`{Token: "alive}"`，继续下一轮监听

~~~java
package com.northboat.remotecontrollerserver.service.impl;

import com.northboat.remotecontrollerserver.service.CommandService;
import com.northboat.remotecontrollerserver.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Service
public class CommandServiceImpl implements CommandService {


    //注入RedisUtil，用@component修饰过
    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    @Override
    public String shutdown(String token) {
        // 先判断Token是否存在
        if(!redisUtil.hasKey(token)){
            return "Token不存在或已失效";
        }

        // 先判断是否为alive，若不为alive说明有其他指令作用，防止冲突直接返回
        if(!redisUtil.hasKey(token) || !redisUtil.get(token).equals("alive")){
            return "其他命令执行中，请稍后再试";
        }

        // 设置命令，若发送失败返回错误信息
        if(!redisUtil.set(token, "shutdown")){
            return "消息发送失败，服务器错误";
        }
        //等待监听器收到命令并回应
        //简单设置一个计时器，当超过十二秒，处理异常并返回超时信息
        long before = System.currentTimeMillis();
        while(!redisUtil.get(token).equals("received")){
            long cur = System.currentTimeMillis();
            if(cur-before > 12000){
                redisUtil.del(token);
                return "长时间未收到监听器反馈，或监听器停止运行，已自动清除本次Token";
            }
        }
        redisUtil.del(token);
        return "成功接收消息，你的计算机将在10后关闭";
    }

    @Override
    public String clean(String token) {
        // 先判断Token是否存在
        if(!redisUtil.hasKey(token)){
            return "Token不存在或已失效";
        }

        // 先判断是否为alive，若不为alive说明有其他指令作用，防止冲突直接返回
        if(!redisUtil.hasKey(token) || !redisUtil.get(token).equals("alive")){
            return "其他命令执行中，请稍后再试";
        }

        if(!redisUtil.set(token, "clean")){
            return "消息发送失败，服务器错误";
        }

        //等待监听器收到命令并回应
        //简单设置一个计时器，当超过十二秒，处理异常并返回超时信息
        long before = System.currentTimeMillis();
        while(!redisUtil.get(token).equals("received")){
            long cur = System.currentTimeMillis();
            if(cur-before > 12000){
                redisUtil.del(token);
                return "长时间未收到监听器反馈，或监听器已停止运行，已清除本次Token";
            }
        }
        redisUtil.del(token);
        return "Token已清除，计算机上的Listener即将关闭";
    }

    @Override
    public String ipconfig(String token) {
        // 先判断Token是否存在
        if(!redisUtil.hasKey(token)){
            return "Token不存在或已失效";
        }

        // 先判断是否为alive，若不为alive说明有其他指令作用，防止冲突直接返回
        if(!redisUtil.hasKey(token) || !redisUtil.get(token).equals("alive")){
            return "其他命令执行中，请稍后再试";
        }

        // System.out.println(redisUtil.get(token));
        // 消息发送失败，返回错误信息
        if(!redisUtil.set(token, "ipconfig")){
            return "消息发送失败，服务器错误";
        }

        long before = System.currentTimeMillis();
        while(redisUtil.get(token).equals("ipconfig")){
            long cur = System.currentTimeMillis();
            if(cur-before > 12000){
                redisUtil.del(token);
                return "长时间未收到监听器反馈，或监听器停止运行，已清除本次Token";
            }
        }
        String ip = redisUtil.get(token).toString();
        redisUtil.set(token, "alive");
        return ip;
    }
}
~~~

### controller

注入`CommandServiceImpl`，直接调用方法，返回对应字符串即可

~~~java
package com.northboat.remotecontrollerserver.controller;

import com.northboat.remotecontrollerserver.service.impl.CommandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/exec")
public class CommandController {

    private CommandServiceImpl commandService;
    @Autowired
    public void setCommandServiceImpl(CommandServiceImpl commandServiceImpl){
        this.commandService = commandServiceImpl;
    }


    @RequestMapping("/shutdown")
    public String shutdown(Model model, String token){
        String result = commandService.shutdown(token);
        System.out.println(token + "执行关机命令\t执行结果:" + result);
        model.addAttribute("result", result);
        return "index";
    }


    @RequestMapping("/ipconfig")
    public String ipConfig(Model model, String token){
        String result = commandService.ipconfig(token);
        System.out.println(token + "执行ipconfig命令\t执行结果:" + result);
        model.addAttribute("result", result);
        return "index";
    }

    @RequestMapping("/clean")
    public String clean(Model model, String token){
        String result = commandService.clean(token);
        System.out.println(token + "执行清除命令\t执行结果:" + result);
        model.addAttribute("result", result);
        return "index";
    }

}
~~~

### 前端

> 前端套用的该网站模板，我只能说确实是好人
>
> [HTML5 UP! Responsive HTML5 and CSS3 Site Templates](https://html5up.net/)

只有一个表单需要提交，即用户`Token`，用`thymeleaf`提交到相应接口即可`th:action="@{/exec/shutdown}"`

只有一个信息需要展示，即命令执行结果`result`，通过`@Controller`返回到`"index"`即可

另外附上`listener.jar`的下载链接，jar包放在`static`目录下即可

~~~html
<a href="/program/remote-controller-listener.jar" download="listener.jar">listener</a>
~~~

~~~html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

	<head>
		<title>Remote-Controller-Ⅱ</title>
		<meta charset="utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"/>
		<link rel="stylesheet" href="/assets/css/main.css"/>
	</head>
	<body class="is-preload">

        <!-- Header -->
        <div id="header">

            <a href="#result"><span class="logo icon fa-paper-plane"></span></a>
            <h1>Hi. This is My Remote Controller.</h1>
            <p>
                一个适用于windows的远程控制程序 <br/>Help control your PC with a
                <a href="/program/remote-controller-listener.jar" download>listener</a>
            </p>

        </div>

        <!-- Main -->
        <div id="main">

            <header class="major container medium">
                <h2>下载并运行Listener后
                <br />
                在本页面使用Token
                <br />
                控制你的PC</h2>

                <p>using u own token to control u pc<br />
                after running the listener.</p>

            </header>

            <div class="box alt container">
                <section class="feature left">

                    <a href="#result" class="image icon solid fa-signal"><img src="/images/pic01.jpg" alt="查看ip地址" /></a>
                    <div class="content">
                        <h3>get ip config</h3>
                        <p>获取计算机当前的IPv4地址</p>
                        <form th:action="@{/exec/ipconfig}" method="post">
                            <input name="token" type="text" placeholder="输入你设置的Token"><br>
                            <input type="submit" value="查询">
                        </form>
                    </div>

                </section>
                <section class="feature right">
                    <a href="#result" class="image icon solid fa-code"><img src="/images/pic02.jpg" alt="查看关闭状态"/></a>
                    <div class="content">
                        <h3>shutdown the pc</h3>
                        <p>关闭你的计算机</p>
                        <form th:action="@{/exec/shutdown}" method="post">
                            <input name="token" type="text" placeholder="输入你设置的Token"><br>
                            <input type="submit" value="关机">
                        </form>
                    </div>
                </section>
                <section class="feature left">
                    <a href="#result" class="image icon solid fa-mobile-alt"><img src="/images/pic03.jpg" alt="查看清除状态" /></a>
                    <div class="content">
                        <h3>clean the token</h3>
                        <p>清除你的Token</p>
                        <form th:action="@{/exec/clean}" method="post">
                            <input name="token" type="text" placeholder="输入你设置的Token"><br>
                            <input type="submit" value="清除">
                        </form>
                    </div>
                </section>
            </div>

            <footer id="result" class="major container medium">
                <h3>命令返回结果</h3>
                <h4 th:text="${result}"></h4>
            </footer>

        </div>

        <!-- Footer -->
        <div id="footer">
            <div class="container medium">

                <header class="major last">
                    <h2>Based On Redis</h2>
                </header>

                <p>reo~reo~reo~reo~reo~reo~reo~reo~reo~reo~reo~reo~</p>

               

                <ul class="icons">
                    <li><a href="http://39.106.160.174:8082/Remote-Controller/" class="icon brands fa-dribbble"><span class="label">Instagram</span></a></li>
                    <li><a href="https://github.com/NorthBoat" class="icon brands fa-github"><span class="label">Github</span></a></li>
                    <li><a href="https://northboat.github.io/Blog/" class="icon brands fa-instagram"><span class="label">Dribbble</span></a></li>
                </ul>

                <ul class="copyright">
                    <li>&copy; NorthBoat.</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
                </ul>

            </div>
        </div>

        <!-- Scripts -->
        <script src="/assets/js/jquery.min.js"></script>
        <script src="/assets/js/browser.min.js"></script>
        <script src="/assets/js/breakpoints.min.js"></script>
        <script src="/assets/js/util.js"></script>
        <script src="/assets/js/main.js"></script>
	</body>
</html>
~~~

## 部署

### 打包

对于`listener`，`File - Project Structure - Artifacts`，点击`+`号选择`jar-from mudoles with dependencies `包，选择`main`函数入口点击`OK`，然后与`File`同级，点击`Build-build artifacts`即可

对于`Server`，使用`Maven`插件打包即可，若前后多次打包，要先`clean`再`package`

### 运行

~~~bash
nohup java -jar Remote-Controller-Ⅱ.jar --server.port=8085 > log/remoteController1.log &
nohup java -jar Remote-Controller-Ⅱ.jar --server.port=8086 > log/remoteController2.log &
~~~

### 配置nginx

~~~bash
cd /usr/local/nginx/conf
vim nginx.conf
~~~

设置负载均衡

~~~
# 配置负载均衡
upstream RemoteController{
	# 邮件发送服务器资源
	server 127.0.0.1:8085 weight=1;
	server 127.0.0.1:8086 weight=1; 
}
~~~

配置`location`

~~~
server {
	listen       8084;
	server_name  localhost;
	location / {
		root   html;
		index  index.html index.htm;
		# 配置服务
		proxy_pass http://RemoteController;
	}

	error_page   500 502 503 504  /50x.html;
	location = /50x.html {
		root   html;
	}
}
~~~

开放端口`8084`

### jar转exe

使用工具`exe4j`和`inno setup`将`listener.jar`转成一个直接可执行的`windows`安装包`listener setup.exe`，安装后无需`jdk`环境可直接运行`listener.exe`











