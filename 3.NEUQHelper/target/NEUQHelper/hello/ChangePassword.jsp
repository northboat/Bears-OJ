<%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/6/21
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ChangePassword</title>
</head>
<body style="text-align:center" marginheight="150px">
<form action="${pageContext.request.contextPath}/hello/ChangePassword" method="post">
    <h1>修改密码</h1><br>
    <%--单选注册身份--%>
    <br><input type="radio" name="table" value="student"> Student
    <input type="radio" name="table" value="teacher"> Teacher <br><br>

    <%--用户名--%>
    <h4>Username:</h4><input type="text" name="username"> <br><br>

    <%--密码--%>
    <h4>Password:</h4><input type="password" name="password"> <br><br>
    <%--确认密码--%>
    <h4>New password:</h4><input type="password" name="newPassword"> <br><br>


    <%--提交按钮--%>
    <br> <input type="submit" value="Change"><br><br>
    <a href="${pageContext.request.contextPath}/">go back</a>
</form>

</body>
</html>
