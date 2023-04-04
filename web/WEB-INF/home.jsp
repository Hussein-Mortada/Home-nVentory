<%-- 
    Document   : HomeJSP
    Created on : Mar 31, 2023, 9:54:05 PM
    Author     : Hussein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="css/home.css">

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home nVentory</title>
    </head>
    <header>
        <form action="home" method="post">
            <input type="submit" value="Log Out">
            <input type="hidden" name="action" value="logout">
        </form>
        <h1>Home nVentory</h1>
        <form action="home" method="post">
            <input type="submit" value="My Profile">
            <input type="hidden" name="action" value="profile">
        </form>
    </header>
    <body>
        <h1>Hello ${user.getFirstName()} , ${user.getLastName()}</h1>
        <c:if test="${empty itemlist}">No items yet. Please add some items!</c:if>
        <c:if test="${not empty itemlist}">
            <table border="1">
                <th>Item Name</th><th>Item Price</th><th>Item Category</th><th>Edit</th><th>Delete</th>
            <c:forEach items="${itemlist}" var="item">
                <tr>
                    <td>${item.getItemName()}</td>
                    <td>$${item.getPrice()}</td>
                    <td>${item.getCategory().getCategoryName()}</td>
                    <td><form action="home" method="post"><input type="submit" value="Edit Item"><input type="hidden" name="action" value="edit"><input type="hidden" name="itemId" value="${item.getItemId()}"></form></td>
                    <td><form action="home" method="post"><input type="submit" value="Delete Item"><input type="hidden" name="action" value="delete"><input type="hidden" name="itemId" value="${item.getItemId()}"></form></td>
                    
                </tr>
            </c:forEach>
            </table>
        </c:if>
        
        <c:if test="${itemID eq null}">
            <h2>Add Item</h2>
            <form action="" method="post">
                Item Name <input type="text" name="itemname" value="${itemname}"><br>
                Price: <input type="number" name="price" value="${itemprice}"><br>
                Category:
                <select name="category">
                    <c:forEach items="${categorylist}" var="category">
                    <option value="${category.getCategoryId()}">${category.getCategoryName()}</option>
                    </c:forEach>
                </select><br>
                <input type="submit" value="Add Item">
                <input type="hidden" name="action" value="additem">
            </form>
        </c:if>
                
        <c:if test="${itemID ne null}">
            <h2>Edit Item</h2>
            <form action="" method="post">
                Item Name <input type="text" name="itemname" value="${itemname}"><br>
                Price: <input type="number" name="price" value="${itemprice}"><br>
                Category:
                <select name="category">
                    <c:forEach items="${categorylist}" var="category">
                    <option value="${category.getCategoryId()}">${category.getCategoryName()}</option>
                    </c:forEach>
                </select><br>
                <input type="submit" value="Save Item">
                <input type="hidden" name="action" value="edititem">
            </form>
        </c:if>
        ${message}
        
        
        
    </body>
</html>
