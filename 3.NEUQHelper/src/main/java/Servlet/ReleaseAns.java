package Servlet;

import Controller.Releaser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ReleaseAns extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        String ans = req.getParameter("ans");
        boolean releaseSuccessfully = false;

        if(!ans.equals("") || ans!=null){
            String num = (String)req.getSession().getAttribute("n");
            System.out.println(ans + " " + num);
            try{
                Releaser r = new Releaser();
                releaseSuccessfully = r.releaseAns(num, ans);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(releaseSuccessfully){
            resp.sendRedirect("ReleaseSuccessfully.jsp");
        }else{
            resp.sendRedirect("ReleaseFailed.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
