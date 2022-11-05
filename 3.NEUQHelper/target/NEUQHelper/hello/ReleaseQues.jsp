<%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/6/20
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ReleaseAns</title>
</head>
<body marginheight="150px">

<div style="text-align: center">
    <h1>发布问题</h1>
</div>
<br>

<div style="text-align: center">
    <form action="/NEUQHelper/hello/ReleaseQues" method="post">
        <textarea name="ques"  cols="50" rows="10"></textarea><br><br>
        <%--单选问题类型--%>
        <br><input type="radio" name="quesType" value="DailyLife"> 生活
        <input type="radio" name="quesType" value="Study"> 学习
        <input type="radio" name="quesType" value="Eating"> 饮食
        <input type="radio" name="quesType" value="Outing"> 出行
        <input type="radio" name="quesType" value="Sports"> 体育
        <input type="radio" name="quesType" value="Entertainment"> 文娱<br><br>
        <input type="submit" value="发布">
    </form>
    <a href="/NEUQHelper/hello/MainPage.jsp"><h3>Go Back</h3></a>
</div>

</body>
</html>
