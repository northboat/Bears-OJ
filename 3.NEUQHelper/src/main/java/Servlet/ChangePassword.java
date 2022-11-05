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

public class ChangePassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String table = req.getParameter("table");
        String nums = req.getParameter("username");
        String password = req.getParameter("password");
        String newPassword = req.getParameter("newPassword");
        boolean resetSuccessfully = false;
        Account acc = new Account(table, nums, password);
        if(table.equals("student")){
            try{
                Student stu = new Student(acc);
                resetSuccessfully = stu.change(newPassword);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        else if(table.equals("teacher")){
            try{
                Teacher tea = new Teacher(acc);
                resetSuccessfully = tea.change(newPassword);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(resetSuccessfully){
            resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
        }else{
            resp.sendRedirect("/NEUQHelper/hello/ChangePassword.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
