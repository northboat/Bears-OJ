package Servlet;

import Controller.Account;
import Controller.Student;
import Controller.Teacher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String table = req.getParameter("table");
        String nums = req.getParameter("username");
        String password = req.getParameter("password");
        String verify = req.getParameter("verify");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        if(table=="" || table==null || nums=="" || nums==null || password=="" || password==null || verify == "" || verify==null){
            System.out.println("请完善注册信息");
            resp.sendRedirect("/NEUQHelper/hello/Register.jsp");
        }
        else if(!password.equals(verify)){
            System.out.println("两次密码输入不一致");
            resp.sendRedirect("/NEUQHelper/hello/Register.jsp");
        }
        else if(table.equals("student")){
            Account a = new Account(table, nums, password);
            Student stu = new Student(a, " ", " ", " ", " ");
            try{
                if(stu.register()){
                    resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
                }
                else{
                    System.out.println("注册失败，账号已被注册或不存在");
                    resp.sendRedirect("/NEUQHelper/hello/Register.jsp");
                }
            }catch (SQLException e){
                e.printStackTrace();
                resp.sendRedirect("/NEUQHelper/hello/Register.jsp");
            }
        }
        else if(table.equals("teacher")){
            Account a = new Account(table, nums, password);
            Teacher tea = new Teacher(a, " ", " ", " ", " ");
            try{
                if(tea.register()){
                    resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
                }
                else{
                    System.out.println("注册失败，账号已被注册或不存在");
                    resp.sendRedirect("/NEUQHelper/hello/Register.jsp");
                }
            }catch (SQLException e){
                e.printStackTrace();
                resp.sendRedirect("/NEUQHelper/hello/Register.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
