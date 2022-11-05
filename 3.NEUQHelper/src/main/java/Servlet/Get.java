package Servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Get extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        req.setCharacterEncoding("utf-8");

        String str = req.getParameter("ques");
        System.out.println(str);

        //this.getInitParameterNames() 返回初始化参数
        //this.getServletConfig()   获得Servlet配置
        ServletContext context = this.getServletContext();   //servlet上下文容器
        String name = "熊舟桐";  //数据
        //将一个数据保存在ServletContext中，setAttribute：设置属性
        context.setAttribute("username", name);
        //Cookie
        /*
        //新建一个cookie，以字符串键值对的形式储存数据
        Cookie c = new Cookie("name", "NorthBoat");
        //设置cookie最大保存时间
        c.setMaxAge(24*60*60);
        //将cookie响应到客户端
        resp.addCookie(c);
        //获取客户端的cookies（多个）
        Cookie[] cookies = req.getCookies();
        //依次打印cookie信息
        if(cookies.length > 0){
            for (int i = 0; i < cookies.length; i++) {
                String name1 = cookies[i].getName();
                String value = cookies[i].getValue();
                resp.getWriter().println(name1 + ":" + value + "\n");
            }
        }*/




        //从servletContext中获取资源（web上下文容器，凌驾于web项目所有servlet之上）
        String name2 = (String)context.getAttribute("username");
        //从InitParameter中获取资源（定义在web.xml中）
        String param = context.getInitParameter("mysql");
        resp.getWriter().println(name2 + ":" + param);

        //加载静态资源
        Properties prop = new Properties();
        InputStream is = context.getResourceAsStream("../database.properties");
        prop.load(is);
        String table = prop.getProperty("student");

        //resp.sendRedirect("/NEUQHelper/hello/Login");
        //请求转发到 /hello
        //context.getRequestDispatcher("/hello").forward(req, resp);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
