<%@ page import="Controller.Student" %><%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/6/9
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
    <%--设置盒子位置--%>
    <style>
        .div-a{float:left;width:35%}
        .div-b{float:right;width:35%}
    </style>

    <style>
        .div-but1{float:left;width: 48%}
        .div-but2{float:right;width:48%}
    </style>
</head>

<body marginheight="125px">



<div style="text-align: center">
    <h1 style="text-align: center">${hello}${sessionScope.get("usr").getName()}</h1> <br><br>
    <form action="${pageContext.request.contextPath}/hello/Search" method="post" style="text-align: center">
        <input type="text" name="question"><input type="submit" value="Search">
    </form>
</div>


<div class="div-but1" style="text-align: right">
    <form action="${pageContext.request.contextPath}/hello/ReleaseQues.jsp" method="post">
            <input type="submit" value="发布问题">
    </form>
</div>
<div class="div-but2" style="text-align: left">
    <form action="${pageContext.request.contextPath}/hello/Logout">
        <input type="submit" value="注销账号">
    </form>
</div>
<br>


<div class="div-a">
    <form action="${pageContext.request.contextPath}/hello/Distribute" style="text-align: right" method="post">
        <h2>恰饭</h2><input type="submit" value="<h2>校内外美食推荐</h2>" name="option"><br><br>
        <h2>出行</h2><input type="submit" value="<h2>对缺德地图说NO</h2>" name="option"><br><br>
        <h2>文娱</h2><input type="submit" value="<h2>就是玩儿</h2>" name="option">
    </form>
</div>

<div class="div-b">
    <form action="${pageContext.request.contextPath}/hello/Distribute" style="text-align: left" method="post">
        <h2>生活</h2><input type="submit" value="<h2>住寝、修电脑...</h2>" name="option"><br><br>
        <h2>学习</h2><input type="submit" value="<h2>图书馆、实验室...</h2>" name="option"><br><br>
        <h2>体育</h2><input type="submit" value="<h2>社团活动、体育赛事...</h2>" name="option">
    </form>
</div>




</body>
</html>
