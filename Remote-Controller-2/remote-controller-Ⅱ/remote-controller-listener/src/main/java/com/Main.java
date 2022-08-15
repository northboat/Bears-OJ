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
