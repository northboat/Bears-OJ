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
