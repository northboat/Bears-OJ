package Servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Distribute extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String modular = req.getParameter("option");
        //用cookie判断用户是否登录，若未登录，返回登录界面
        String isLogin = null;
        Cookie[] cookies = req.getCookies();
        for(Cookie c: cookies){
            if(c.getName().equals("isLogin")){
                isLogin = c.getValue();
            }
        }
        //判断是否登录，排除直接通过url进入的用户
        if(modular == null || isLogin.equals("no") || isLogin == null){
            System.out.println("请先登录");
            resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
        }
        else{
            if(modular.equals("<h2>校内外美食推荐</h2>")){
                req.getSession().setAttribute("type", "Eating");
            }
            else if(modular.equals("<h2>对缺德地图说NO</h2>")){
                req.getSession().setAttribute("type", "Outing");
            }
            else if(modular.equals("<h2>就是玩儿</h2>")){
                req.getSession().setAttribute("type", "Entertainment");
            }
            else if(modular.equals("<h2>住寝、修电脑...</h2>")){
                req.getSession().setAttribute("type", "DailyLife");
            }
            else if(modular.equals("<h2>图书馆、实验室...</h2>")){
                req.getSession().setAttribute("type", "Study");
            }
            else if(modular.equals("<h2>社团活动、体育赛事...</h2>")){
                req.getSession().setAttribute("type", "Sports");
            }
            resp.sendRedirect("/NEUQHelper/hello/ShowQuesInfo.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
