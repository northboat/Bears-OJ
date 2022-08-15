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
