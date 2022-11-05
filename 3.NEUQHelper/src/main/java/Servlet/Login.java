package Servlet;

import Controller.Account;
import Controller.Admin;
import Controller.Student;
import Controller.Teacher;



import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //用页面信息构建登录账户信息
        String table = req.getParameter("table");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Account account = new Account(table, username, password);

        //进行登录判断
        if(table=="" || table==null || username=="" || username==null || password==null || password==""){
            System.out.println("请完善登录信息");
            resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
        }
        else if(table.equals("student")) {
            try{
                Student stu = new Student(account);
                ResultSet set = stu.login();
                if(set == null){
                    System.out.println("该账号未注册或不存在，请先注册或检查账号");
                    resp.sendRedirect("/NEUQHelper");
                }
                else{
                    //将用户信息存到session中
                    stu.setName(set.getString("name"));
                    stu.setMajor(set.getString("major"));
                    stu.setGender(set.getString("gender"));
                    stu.setGrade(set.getString("grade"));
                    HttpSession session = req.getSession();
                    session.setAttribute("usr", stu);
                    //session.setAttribute("name", stu.getName());
                    //获取时间，说上午下午好
                    long time = System.currentTimeMillis();
                    java.util.Date date = new Date(time);
                    int hours = date.getHours()+8;
                    if(hours>=5 && hours<11)
                        session.setAttribute("hello", "上午好，");
                    if(hours>=11 && hours<14)
                        session.setAttribute("hello", "中午好，");
                    if(hours>=14 && hours<18)
                        session.setAttribute("hello", "下午好，");
                    if(hours>=18 && hours<23)
                        session.setAttribute("hello", "晚上好，");
                    if(hours>=23 || hours<3)
                        session.setAttribute("hello", "夜深了快睡吧，");
                    if(hours>=3 && hours<5)
                        session.setAttribute("hello", "Are U OK?");
                    //将登录信息存到cookie中
                    Cookie c = new Cookie("isLogin", "yes");
                    resp.addCookie(c);
                    stu.exit();
                    resp.sendRedirect("/NEUQHelper/hello/MainPage.jsp");
                }
            }catch(SQLException e){
                System.out.println("SQL错误，请重试");
                e.printStackTrace();
                resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
            }
        }
        else if(table.equals("teacher")){
            try{
                Teacher tea = new Teacher(account);
                ResultSet set = tea.login();
                if(set == null) {
                    System.out.println("该账号未注册或不存在，请先注册或检查账号");
                    resp.sendRedirect("/NEUQHelper");
                }
                else{
                    //将老师信息存到session中
                    HttpSession session = req.getSession();
                    tea.setName(set.getString("name"));
                    tea.setMajor(set.getString("major"));
                    tea.setGender(set.getString("gender"));
                    tea.setPosition(set.getString("position"));
                    session.setAttribute("usr", tea);
                    //session.setAttribute("name", tea.getName());
                    //获取时间，说上午下午好
                    long time = System.currentTimeMillis();
                    java.util.Date date = new Date(time);
                    int hours = date.getHours()+8;
                    if(hours>=5 && hours<11)
                        session.setAttribute("hello", "上午好，");
                    if(hours>=11 && hours<14)
                        session.setAttribute("hello", "中午好，");
                    if(hours>=14 && hours<18)
                        session.setAttribute("hello", "下午好，");
                    if(hours>=18 && hours<23)
                        session.setAttribute("hello", "晚上好，");
                    if(hours>=23 || hours<3)
                        session.setAttribute("hello", "夜深了快睡吧，");
                    if(hours>=3 && hours<5)
                        session.setAttribute("hello", "Are U OK?");

                    //将登录信息存到cookie中
                    Cookie c = new Cookie("isLogin", "yes");
                    resp.addCookie(c);
                    tea.exit();
                    resp.sendRedirect("/NEUQHelper/hello/MainPage.jsp");
                }
            }catch(SQLException e) {
                System.out.println("SQL错误，请重试");
                e.printStackTrace();
                resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
            }
        }
        else if(table.equals("admin")){
            try{
                Admin admin = new Admin(username, password);
                ResultSet set = admin.login();
                if(set == null){
                    System.out.println("该账号未注册或不存在，请先注册或检查账号");
                    resp.sendRedirect("/NEUQHelper");
                }
                else{
                    req.getSession().setAttribute("admin", admin);
                    Cookie c = new Cookie("isLogin", "yes");
                    resp.addCookie(c);
                    admin.exit();
                    resp.sendRedirect("/NEUQHelper/hello/AdminPage.jsp");
                }
            }catch (SQLException e){
                System.out.println("SQL错误，请重试");
                e.printStackTrace();
                resp.sendRedirect("/NEUQHelper/hello/Login.jsp");
            }
        }
        //resp.sendRedirect("/NEUQHelper/hello");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
