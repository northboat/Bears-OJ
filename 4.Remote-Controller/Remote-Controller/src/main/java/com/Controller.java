package com;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Socket;

public class Controller extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String pwd = req.getParameter("pwd");
        if(pwd.equals("011026")){
            try{
                //向服务端发送信息，想办法把这个字符串存起来
                Socket socket = new Socket("39.106.160.174", 8088);
                //Socket socket = new Socket("localhost", 8088);
                socket.getOutputStream().write("shutdown".getBytes());
                socket.shutdownOutput();
                resp.sendRedirect("/Remote-Controller/ShutdownSuccessfully.jsp");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("密码错误");
            resp.sendRedirect("/Remote-Controller");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
