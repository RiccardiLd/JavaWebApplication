<%--
  Created by IntelliJ IDEA.
  User: riccardild
  Date: 2019-03-16
  Time: 02:06
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title> Sign in or Sign up</title>
</head>
<body>

    <form method="post" action="${pageContext.request.contextPath}/" style="text-align:center;">
        <fieldset>
            <legend>Log In</legend>
            <label>
                <input type="text" name="email" placeholder="Email" required>
            </label><br>
            <label>
            <input type="password" name="password" placeholder="Password" required>
            </label><br>
            <button type="submit" name="action" value="signIn" style="width:60px">Log In</button>
        </fieldset>
    </form>

    <form method="post" action="${pageContext.request.contextPath}/" style="text-align:center;">
        <fieldset>
            <legend>Sign Up</legend>
            <label>
                <input type="text" name="email" placeholder="Email" required>
            </label><br>
            <label>
                <input type="password" name="password" placeholder="Password" required>
            </label><br>
            <label>
                <input type="password" name="passwordConfirmation" placeholder="Confirm Password" required>
            </label><br>
            <button type="submit" name="action" value="signUp" style="width:60px">Sign Up</button>
        </fieldset>
    </form>

</body>
</html>
