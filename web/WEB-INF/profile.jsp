<%-- 
    Document   : profile
    Created on : Apr 2, 2023, 7:04:36 PM
    Author     : Hussein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="css/profile.css">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home nVentory</title>
    </head>
    <body>
    <header>
        <form action="profile" method="post">
            <input type="submit" value="Log Out">
            <input type="hidden" name="action" value="logout">
        </form>
        <h1>Home nVentory</h1>
        <form action="profile" method="post">
            <input type="submit" value="Home">
            <input type="hidden" name="action" value="home">
        </form>
    </header>
        <h1>${user.getFirstName()} , ${user.getLastName()}'s Profile Page</h1>
        <form action="profile" method="post">
            Email: <input type="text" name="email" value="${user.getEmail()}" disabled="true"><br>
            First name: <input type="text" name="firstname" value="${user.getFirstName()}"><br>
            Last name: <input type="text" name="lastname" value="${user.getLastName()}"><br>
            Password: <input type="password" name="password" value="${user.getPassword()}"><br>
            <input type="submit" value="Save Changes">
            <input type="hidden" name="action" value="save">
        </form>
            
            <form action="profile" method="post">
                <input type="submit" value="Disable Account">
                <input type="hidden" name="action" value="disable">
            </form>
            <c:if test="${disable ne null}">
                <h2>Are you sure you want to disable your account?</h2>
                <p>Only an administrator can re-enable your account.</p>
                <form action="profile" method="post">
                    <input type="submit" value="Confirm">
                    <input type="hidden" name="action" value="confirm">
                </form>
                <form action="profile" method="post">
                    <input type="submit" value="Cancel">
                    <input type="hidden" name="action" value="cancel">
                </form>
            </c:if>
                <h2>${message}</h2>  
    </body>
</html>
