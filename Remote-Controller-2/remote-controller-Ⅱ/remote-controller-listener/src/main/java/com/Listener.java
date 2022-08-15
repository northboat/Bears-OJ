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




    public static void main(String[] args) {
        Listener listener = new Listener();
        listener.initRedis();
        listener.flush();
    }

    public void test(){
//        String str = jedis.set("1", "hahaha");
//        long r1 = jedis.expire("1", 3);
//        long r2 = jedis.del("1");
//        long r3 = jedis.del("2");
//        long r4 = jedis.del("3");
//        jedis.del("4");
//        System.out.println(str + " " + r1 + " " + r2 + " " + r3 + " " + r4);


        Transaction multi = jedis.multi();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sad", "NorthBoat");
        jsonObject.put("hello", "hahaha");

        String jsonString = jsonObject.toString();
        try{
            multi.set("user1", jsonString);
            multi.set("user2", jsonString);
            int i = 1/0;
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
}
