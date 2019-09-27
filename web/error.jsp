<%--
  Created by IntelliJ IDEA.
  User: riccardild
  Date: 2019-03-17
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Login Failed</title>
    </head>
    <body>
        <%try {
            if(session.getAttribute("error").equals("LogIn")) {%>
                Log In failed. Please try again.<%
            }
            else if(session.getAttribute("error").equals("SignUp")) {%>
                Sign Up failed. Please try again.<%
            }
        } catch (NullPointerException ex) {%>
            Something went wrong. Please Try Again.<%
        }%>
        <a href="${pageContext.request.contextPath}/">Click here</a> to try again.
    </body>
</html>
