package com.socket;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Server {

    private static int count = -1;

    //存用户ip以及密码
    private Map<String, String> clients = new HashMap<>();

    public static void shutdown(){

    }

    //防止无限循环警告
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        try {
            // 创建服务端socket
            ServerSocket serverSocket = new ServerSocket(8088);
            // 创建客户端socket
            Socket socket;
            //储存命令
            Queue<String> command = new ArrayDeque<>();
            Map<String, String> strMap = new HashMap<>(32);
            //循环监听等待客户端的连接
            while(true){

                // 监听客户端
                socket = serverSocket.accept();
                System.out.println(command.size());
                ServerThread thread = new ServerThread(socket, command.poll());

                thread.start();
                TimeUnit.SECONDS.sleep(2);

                if(thread.getCommand() != null && thread.getCondition()){
                    command.offer(thread.getCommand());
                    System.out.println(command);
                }

                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP：" + address.getHostAddress() + ":" + socket.getPort());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
