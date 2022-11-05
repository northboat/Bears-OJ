package Servlet;

import Controller.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class ResetAccAfterSearch extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        Admin admin = (Admin)req.getSession().getAttribute("admin");
        HashMap<String, String> usr = (HashMap<String, String>)req.getSession().getAttribute("usr");
        String name = req.getParameter("resetInfo");
        String table = usr.get("身份");
        if(table.equals("学生"))
            table = "student";
        else
            table = "teacher";
        String nums = usr.get("账号");
        boolean resetSuccessfully = false;
        try{
            resetSuccessfully = admin.resetAccount(table, nums, name);
        }catch (SQLException e) {
            System.out.println("数据库错误");
        }
        if(resetSuccessfully){
            resp.sendRedirect("/NEUQHelper/hello/ResetSuccessfully.jsp");
        }else{
            resp.sendRedirect("/NEUQHelper/hello/ResetFailed.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
