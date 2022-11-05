package Servlet;

import Controller.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ResetAcc extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String CTable = req.getParameter("tableInfo").substring(2, 4);
        String table = "student";
        if(CTable.equals("老师")){
            table = "teacher";
        }
        String resetInfo = req.getParameter("resetInfo");
        boolean resetSuccessfully = false;
        if(resetInfo != null && !resetInfo.equals("") && resetInfo.contains("/")){
            String nums = resetInfo.substring(0, resetInfo.indexOf('/'));
            String name = resetInfo.substring(nums.length()+1);
            Admin admin = (Admin) req.getSession().getAttribute("admin");
            try{
                resetSuccessfully = admin.resetAccount(table, nums, name);
            }catch (SQLException e){
                System.out.println("数据库错误");
            }
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
