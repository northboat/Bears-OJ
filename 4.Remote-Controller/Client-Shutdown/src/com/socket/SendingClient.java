package com.socket;


import java.net.Socket;

public class SendingClient {
    public static void main(String[] args) {
        try{
            //向服务端发送信息，想办法把这个字符串存起来/Socket socket = new Socket("39.106.160.174", 8088);
            //Socket socket = new Socket("39.106.160.174", 8088);
            Socket socket = new Socket("localhost", 8088);
            socket.getOutputStream().write("shutdown".getBytes());
            socket.shutdownOutput();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
