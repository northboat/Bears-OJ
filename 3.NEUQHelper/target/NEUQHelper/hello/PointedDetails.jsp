<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/6/20
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PointedDetails</title>
</head>
<head>
    <title>Details</title>
    <style>
        .div-a{float:left;width: 38%}
        .div-b{float:right;width: 40%}
    </style>
</head>
<body marginheight="150px">

<div style="text-align:center">
    <a href="${pageContext.request.contextPath}/hello/ShowQuesInfo.jsp"><h2>Go Back</h2></a>
</div>
<br><br><br>

<br>
<div class="div-a" style="text-align: right">
    <h2>
        <%
            request.setCharacterEncoding("utf-8");
            String ques = request.getParameter("qInfo");
            String n = ques.substring(0, ques.indexOf("."));
            session.setAttribute("n", n);
            if(ques!=null){
                List<String> ans = (List<String>) session.getAttribute(ques);
                out.write("问题: " + ques + "<br><br><br>");
                for(int i = 0; i < ans.size(); i++){
                    out.write("回答" + (i+1) + ": " + ans.get(i) + "<br><br>");
                }
            }
        %>
    </h2>
</div>

<div class="div-b" style="text-align: left">
    <br>
    <form action="${pageContext.request.contextPath}/hello/ReleaseAns">
        <textarea name="ans"  cols="50" rows="10"></textarea><br><br>
        <input type="submit" value="发布回答">
    </form>
</div>

</body>
</html>
