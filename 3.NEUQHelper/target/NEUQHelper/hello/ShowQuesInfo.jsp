<%@ page import="Dao.DBUtilsForQuesAndRes" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Searcher" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/6/20
  Time: 1:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ShowQuesInfo</title>
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
            String type = (String)session.getAttribute("type");
            Searcher s = new Searcher();
            List<String> questions = s.getQues(type);
            for(int i = questions.size()-1; i >= 0; i--){
                String q = questions.get(i);
                request.setAttribute("qInfo", q);
        %>
        <form action="${pageContext.request.contextPath}/hello/PointedDetails.jsp" method="post">
            <input type="submit" value="${qInfo}" name="qInfo" style="height: 70px;width: 350px">

        <%
                List<String> ans = s.getPointedAns(q.substring(0, q.indexOf(".")));
                session.setAttribute(q, ans);
                out.write("回答共计: " + ans.size() + "条");
        %>

        </form>
        <%
            }
        %>
    </h2>
</div>
</body>
</html>
