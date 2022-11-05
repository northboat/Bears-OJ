package Servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;


public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie c = new Cookie("isLogin", "no");
        resp.addCookie(c);
        //注销session，服务器将自动new一个全新的session（信息全无）
        req.getSession().invalidate();
        req.getSession();
        resp.sendRedirect("/NEUQHelper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
