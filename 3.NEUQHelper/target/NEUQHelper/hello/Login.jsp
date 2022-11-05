<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body style="text-align:center" marginheight="195px">



<form action="${pageContext.request.contextPath}/hello/Login" method="post">

    <h1>Login</h1><br><br>
    <%--单选登录身份--%>
    <br><input type="radio" name="table" value="student"> Student
        <input type="radio" name="table" value="teacher"> Teacher
        <input type="radio" name="table" value="admin"> Admin<br><br>


    <%--用户名--%>
    Username <input type="text" name="username"><br><br>

    <%--密码--%>
    Password   <input type="password" name="password"><br>


    <%--提交按钮--%>
    <br> <input type="submit" value="Login"><br><br>

    <%--修改密码、忘记密码、返回--%>
    <a href="ChangePassword.jsp">  <h5>change password</h5></a>
    <a href="ForgetPassword.jsp" s>  <h5>forget password?</h5></a>
    <a href="${pageContext.request.contextPath}/"><h4>Go Back</h4></a>

</form>

</body>
</html>