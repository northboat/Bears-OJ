<%@ page import="Controller.Admin" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="Controller.Info" %>
<%@ page import="Controller.Student" %><%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/6/9
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AdminPage</title>

    <style>
        .div-stu{float:left;width:35%}
        .div-tea{float:right;width:35%}
    </style>
</head>
<body marginheight="100px">

<div style="text-align: center">
    <h1>欢迎进入管理员界面</h1>

    <%--搜索账号--%>
    <form action="${pageContext.request.contextPath}/hello/SearchUsr">
        <input type="text" name="usrInfo" placeholder="usrType:usrNums"><input type="submit" value="搜索用户" name="tab"><br>
    </form>

    <%--注销账号，跳转到Logout（servlet）清空session--%>
    <form action="${pageContext.request.contextPath}/hello/Logout">
        <input type="submit" value="注销管理员">
    </form>
</div>
<br>

<div style="text-align: center">
    <h3>新建账号</h3>
    <form action="${pageContext.request.contextPath}/hello/SetAcc" method="post">
        <input type="text" placeholder="nums:name" name="setInfo"><input type="submit" value="新建">
    </form>
</div>

<div class="div-stu" style="text-align: right">
    <h3><h2>学生信息</h2><br>
        <%
            Admin admin = (Admin) session.getAttribute("admin");
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            if(admin != null){
                Iterator<Info> stu = admin.showStuInfo();
                while(stu.hasNext()){
                    out.write(stu.next().toString());

        %>
        <%--重置按钮--%>
        <form action="${pageContext.request.contextPath}/hello/ResetAcc" method="post">
            <input type="submit" value="重置学生密码" name="tableInfo"><input type="text" name="resetInfo" placeholder="usrNums/usrName">
        </form>
        <br>

        <% }} %>
    </h3>
</div>

<div class="div-tea">
    <h3><h2>老师信息</h2><br>
        <%
            if(admin != null){
                Iterator<Info> tea = admin.showTeaInfo();
                while(tea.hasNext()){
                    out.write(tea.next().toString());
        %>
        <%--重置按钮--%>
        <form action="${pageContext.request.contextPath}/hello/ResetAcc" method="post">
            <input type="text" name="resetInfo" placeholder="usrNums/usrName"><input type="submit" value="重置老师密码" name="tableInfo">
        </form>
        <br>

        <%  }} %>
    </h3>
</div>



</body>
</html>
