<%-- 
    Document   : RegisterJSP
    Created on : Mar 31, 2023, 9:53:59 PM
    Author     : Hussein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/register.css">

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
        <h1>Register</h1>
        <form method="post" action="register">
            Email: <input type="text" name="email" value="${email}"><br>
            First Name: <input type="text" name="firstname" value="${firstname}"><br>
            Last Name: <input type="text" name="lastname" value="${lastname}"><br>
            Password: <input type="password" name="password" value="${password}"><br>
            <input type="hidden" name="action" value="register" />
            <input type="submit" name="submit" value="Register">
        </form>&nbsp;
            <form method="get" action="login">
                <input type="hidden" name="action" value="cancel" />
                <input type="submit" name="submit" value="Cancel">
            </form>
        ${message}
    </body>
</html>
