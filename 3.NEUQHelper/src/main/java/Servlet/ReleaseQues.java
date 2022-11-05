package Servlet;

import Controller.Releaser;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ReleaseQues extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String ques = req.getParameter("ques");
        String type = req.getParameter("quesType");
        boolean releaseSuccessfully = false;
        try{
            Releaser r = new Releaser();
            releaseSuccessfully = r.releaseQues(ques, type);
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(releaseSuccessfully){
            resp.sendRedirect("ReleaseSuccessfully.jsp");
        }
        else{
            resp.sendRedirect("ReleaseFailed.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
