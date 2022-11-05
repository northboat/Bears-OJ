package com.socket;


import com.utils.exec;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {
    public static Socket socket;
    public static InputStream is;
    public static BufferedReader reader;
    public static boolean flag = true;

    public static void exec() throws IOException {
        /*Scanner scanner = new Scanner(System.in);
        String pwd = scanner.nextLine();
        scanner.close();*/

        try{
            // 和服务器创建连接并获取命令
            while(true) {
                System.out.println("hahaha");
                socket = new Socket("39.106.160.174", 8088);
                //socket = new Socket("localhost", 8088);
                socket.setSoTimeout(3000);

                //发送密码
                /*socket.getOutputStream().write(pwd.getBytes());
                socket.shutdownOutput();*/

                // 从服务器接收的信息
                is = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                String info = null;
                if ((info = reader.readLine()) != null) {
                    //System.out.println(info);
                    if (info.equals("shutdown")) {
                        break;
                    }
                }
            }
            reader.close();
            is.close();
            socket.close();
        }catch (SocketTimeoutException e){
            exec();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            exec();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }  finally {
            if(flag){
                System.out.println("shutdown");
                //exec.shutdown();
            }else{
                System.out.println("请检查网络连接");
            }
        }
    }
}
