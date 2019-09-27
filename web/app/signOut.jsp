<%--
  Created by IntelliJ IDEA.
  User: riccardild
  Date: 2019-03-16
  Time: 02:10
  To change this template use File | Settings | File Templates.
--%>
<%@page import="model.User"%>
<%@page errorPage="/signIn.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta http-equiv="Refresh" content="3;${pageContext.request.contextPath}/">
    <title> Signed Out</title>
</head>
<body>
    <%User user = (User) session.getAttribute("user");
    try {
        if(session.getAttribute("check").equals("delete")) {%>
            User <b><%=user.getEmail()%></b> has been successfully removed. <a href="${pageContext.request.contextPath}/">Click here</a> to return to the Login or Sign Up page.<%
        }
    } catch (NullPointerException ex) {%>
            User <b><%=user.getEmail()%></b> has successfully logged out. <a href="${pageContext.request.contextPath}/">Click here</a> to login again.<%
    }%>
</body>
</html>
