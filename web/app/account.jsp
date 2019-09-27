<%--
  Created by IntelliJ IDEA.
  User: riccardild
  Date: 2019-03-16
  Time: 02:09
  To change this template use File | Settings | File Templates.
--%>
<%@page import="model.User"%>
<%@ page import="model.UserSet" %>
<%@page errorPage ="/signIn.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Account</title>
    </head>
    <body>
        <h1>User Account Page</h1>
        <%User user = (User) session.getAttribute("user");%>
        <b>Welcome <%=user.getEmail() + ", member since " + user.getSignUpDate()%></b><br>

        <table style="border: 1px solid black;width:100%;text-align: center;table-layout: fixed">
            <thead>
                <tr>
                    <th>User Email</th>
                    <th>Last Log In</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><%=user.getEmail()%></td>
                    <td><%=user.getLastSignIn()%></td>
                </tr>
            </tbody>
        </table><br>

        <table style="border: 1px solid black;width:100%;text-align: center;table-layout: fixed">
            <thead>
            <tr>
                <th>Key</th>
                <th>Value</th>
            </tr>
            </thead>
            <tbody>
            <%for(UserSet u : user.getUserSet()) {%>
                <tr>
                    <td><%=u.getKey()%></td>
                    <td><%=u.getValue()%></td>
                </tr><%
            }%>
            </tbody>
        </table><br>

        <form method="post" action="${pageContext.request.contextPath}/" style="text-align:center;">
            <fieldset>
                <legend>Change my Key/Value pairs</legend>
                <label>
                    <input type="text" name="Key" placeholder="Key" required>
                </label><br>
                <label>
                    <input type="text" name="Value" placeholder="Value" required>
                </label><br>
                <input type="hidden" name="email" value="<%=user.getEmail()%>"/>
                <input type="submit" name="action" value="Add" style="width:60px">
                <input type="submit" name="action" value="Remove" style="width:60px">
                <br><small style="color: crimson">
                <%try {
                    if(session.getAttribute("error").equals("AddRemove")) {%>
                        Operation failed.<%
                    }
                } catch (NullPointerException ignored) {

                }%></small>
            </fieldset>
        </form>

        <form method="post" action="${pageContext.request.contextPath}/" style="text-align:center;">
            <fieldset>
                <legend>Account Settings</legend>
                <label>
                    <input type="checkbox" name="check" value="delete">
                </label> Delete User on Logout<br><br>
                <input type="hidden" name="email" value="<%=user.getEmail()%>"/>
                <button type="submit" name="action" value="Logout" style="width:60px">Log Out</button>
            </fieldset>
        </form>

    </body>
</html>
