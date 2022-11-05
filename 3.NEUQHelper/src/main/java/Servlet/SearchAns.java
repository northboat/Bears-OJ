package Servlet;


import Controller.Searcher;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAns extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        //判断是否登录
        String isLogin = null;
        Cookie[] cookies = req.getCookies();
        for(Cookie c: cookies){
            if(c.getName().equals("isLogin")){
                isLogin = c.getValue();
            }
        }
        if(isLogin.equals("no")){
            resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
        }
        else{
            try{
                Searcher searcher = new Searcher();
                String question = req.getParameter("question");
                HashMap<String, List<String>> map = searcher.getAns(question);
                //如果没有在库中搜到答案，跳转到指定界面
                if(map == null){
                    resp.sendRedirect("/NEUQHelper/hello/NoAns.jsp");
                }
                else{
                    List<String> keys = new ArrayList<>();
                    HttpSession session = req.getSession();
                    for(String key: map.keySet()){
                        List<String> ans = map.get(key);
                        //用session存问题及答案，在Search.jsp上打印输出
                        //context.setAttribute("question"+ques.substring(0,':'), ques);
                        session.setAttribute(key, ans);
                        keys.add(key);
                    }
                    session.setAttribute("keys", keys);
                    resp.sendRedirect("/NEUQHelper/hello/SearchAns.jsp");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
