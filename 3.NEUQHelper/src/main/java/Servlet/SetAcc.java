package Servlet;

import Controller.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SetAcc extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //用cookie判断用户是否登录，若未登录，返回登录界面
        String isLogin = null;
        Cookie[] cookies = req.getCookies();
        for(Cookie c: cookies){
            if(c.getName().equals("isLogin")){
                isLogin = c.getValue();
            }
        }
        if(isLogin.equals("no") || isLogin==null){
            resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
        }
        else{
            boolean setSuccessfully = false;
            try{
                req.setCharacterEncoding("utf-8");
                resp.setCharacterEncoding("utf-8");
                String setInfo = req.getParameter("setInfo");
                if(!setInfo.equals("") && setInfo!=null && setInfo.contains(":")){
                    String nums = setInfo.substring(0, setInfo.indexOf(':'));
                    String name = setInfo.substring(nums.length()+1);
                    Admin admin = (Admin)req.getSession().getAttribute("admin");
                    setSuccessfully = admin.setAccount(nums, name);
                }
            }catch (SQLException e){
                e.printStackTrace();
                System.out.println("数据库错误");;
            }
            if(setSuccessfully){
                resp.sendRedirect("/NEUQHelper/hello/SetSuccessfully.jsp");
            }else{
                resp.sendRedirect("/NEUQHelper/hello/SetFailed.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
