<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/6/17
  Time: 0:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ShowInfo</title>
</head>
<body marginheight="225px">
<div style="text-align: center">
    <h2>
        <%
            HashMap<String, String> usrInfo = (HashMap<String, String>) session.getAttribute("usr");
            if(usrInfo != null) {
                for (String key : usrInfo.keySet()) {
                    out.write(key + ":" + usrInfo.get(key) + "<br><br>");
                }
            }
        %>
    </h2>
    <%--重置按钮--%>
    <form action="${pageContext.request.contextPath}/hello/ResetAccAfterSearch" method="post">
        <input type="text" name="resetInfo" placeholder="usrName"><input type="submit" value="重置密码"><br>
    </form>
    <a href="${pageContext.request.contextPath}/hello/AdminPage.jsp">go back</a>
</div>
</body>
</html>
