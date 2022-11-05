package Servlet;

import Controller.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class SearchUsr extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Admin admin = (Admin)req.getSession().getAttribute("admin");
        String usrInfo = req.getParameter("usrInfo");
        HashMap<String, String> usr = null;
        try{
            String table = usrInfo.substring(0, usrInfo.indexOf(':'));
            String nums = usrInfo.substring(table.length()+1);
            usr = admin.searchUser(table, nums);
        }catch (SQLException e){
            System.out.println("数据库错误");
        }catch (StringIndexOutOfBoundsException se){
            System.out.println("管理员输入格式错误");
        }
        if(usr == null){
            resp.sendRedirect("/NEUQHelper/hello/NoUsr.jsp");
        }else{
            req.getSession().setAttribute("usr", usr);
            resp.sendRedirect("/NEUQHelper/hello/ShowUsrInfo.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
