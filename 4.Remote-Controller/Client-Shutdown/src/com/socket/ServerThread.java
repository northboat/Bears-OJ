package com.socket;

import com.sun.javaws.IconUtil;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{

    private boolean flag = false;

    private InputStream in = null;
    private OutputStream out = null;

    private Socket socket = null;

    private String command = null;

    public boolean getCondition(){
        return flag;
    }

    public ServerThread(Socket socket, String command){ this.socket = socket; this.command = command; }

    public String getCommand(){
        return command;
    }

    public void getMessage() throws IOException {

        in = socket.getInputStream();
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        byte[] buffer = new byte[2084];
        int len = 0;

        while((len = in.read(buffer)) != -1){
            byteOut.write(buffer, 0, len);
        }

        //不知道为什么，这个while循环之后，若byteOut为空，什么也打印不出来
        //System.out.println("进来了");

        flag = true;
        command = byteOut.toString();
        System.out.println("这里是服务器，接收到命令：" + command);
        byteOut.close();
        socket.shutdownInput();
    }

    public void sendMessage() throws IOException {
        out = socket.getOutputStream();
        out.write(command.getBytes("GBK"));
        //out.flush();
        socket.shutdownOutput();
    }


    @Override
    public void run() {
        try {
            //读取客户端信息
            if(command == null){
                getMessage();
            }else{
                sendMessage();
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("该程序靠此bug运行");
            //e.printStackTrace();
        } finally{
            //关闭资源
            try {
                if(out != null)
                    out.close();
                if(in != null)
                    in.close();
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
