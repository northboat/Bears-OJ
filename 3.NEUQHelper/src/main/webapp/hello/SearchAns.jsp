<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/6/14
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SearchAns</title>
    <style>
        .div-q{float:left;width:67%}
    </style>
</head>
<body marginheight="125px">

<div style="text-align:center">
    <a href="${pageContext.request.contextPath}/hello/MainPage.jsp"><h1>Go Back Home</h1></a>
</div>
<br><br>


<div class="div-q" style="text-align: right">
    <h2>
        <%
            List<String> keys = (List<String>) session.getAttribute("keys");
            for(int i = keys.size()-1; i >= 0; i--){
                String key = keys.get(i);
                request.setAttribute("q", key);
        %>
        <form action="${pageContext.request.contextPath}/hello/Details.jsp" method="post">
            <input type="submit" value="${q}" name="q" style="height: 70px;width: 350px">

        <%
                List<String> ans = (List<String>)session.getAttribute(key);
                out.write("回答共计: " + ans.size() + "条");
        %>
        </form>
        <%  }%>
    </h2>
</div>

</body>
</html>
