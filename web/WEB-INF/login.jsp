<%-- 
    Document   : LoginJSP
    Created on : Mar 31, 2023, 9:53:43 PM
    Author     : Hussein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/login.css">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home nVentory</title>
    </head>
    <body>
        <header>
            <h1>Home nVentory</h1>
        </header>
        <h1>Login</h1>
        <form method="post" action="login">
            Email: <input type="text" name="email" value="${email}"><br>
            Password: <input type="password" name="password" value="${password}"><br>
            <input type="hidden" name="action" value="login" />
            <input type="submit" name="submit" value="Log In">
        </form>
            <form method="get" action="register">
                <input type="hidden" name="action" value="register" />
                <input type="submit" name="submit" value="Sign Up">
            </form>
        ${message}
    </body>
</html>
