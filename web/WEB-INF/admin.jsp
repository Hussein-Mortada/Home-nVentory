<%-- 
    Document   : admin
    Created on : Apr 2, 2023, 7:43:47 PM
    Author     : Hussein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <header>
        <form action="profile" method="post">
            <input type="submit" value="Log Out">
            <input type="hidden" name="action" value="logout">
        </form>
        <h1>Home nVentory</h1>
        <h3>Administrator ${user.getFirstName()}</h3>
    </header>
    <body>
        <h2>Welcome.  What would you like to do:</h2><br>
        <form action="admin" method="post">
            <input type="submit" value="View All Users">
            <input type="hidden" name="action" value="view all users">
        </form>
        <form action="admin" method="post">
            <input type="submit" value="View All Categories">
            <input type="hidden" name="action" value="view all categories">
        </form>
        <c:if test="${categoryview ne null}">
            <c:if test="${not empty categorylist}">
                <table border="1">
                <th>Category Name</th>
            <c:forEach items="${categorylist}" var="category">
                <tr>
                    <td>${category.getCategoryName()}</td>
                    <td><form action="admin" method="post"><input type="submit" value="Edit Category"><input type="hidden" name="action" value="edit category"><input type="hidden" name="categoryid" value="${category.getCategoryId()}"></form></td>
                </tr>
            </c:forEach>
            </table>
            </c:if>
        </c:if>
        <c:if test="${editcategory eq null && categoryview ne null}">
            <h2>Add Category</h2>
            <form method="post" action="admin">
                Category Name:<input type="text" name="category" value="${category.getCategoryName()}">
                <input type="hidden" name="action" value="add category" />
                <input type="submit" name="submit" value="Add Category">
            </form>
        </c:if>
            <c:if test="${editcategory ne null && categoryview ne null}">
            <h2>Edit Category</h2>
            <form method="post" action="admin">
                Category Name:<input type="text" name="category" value="${category.getCategoryName()}">
                <input type="hidden" name="action" value="save category" />
                <input type="submit" name="submit" value="Save Category">
            </form>
                <form method="post" action="admin">
            <input type="submit" name="submit" value="Cancel Edit">
            <input type="hidden" name="action" value="cancel category" />
        </form>
        </c:if>

        
        <c:if test="${userview ne null}">
            <c:if test="${not empty userlist}">
            <table border="1">
                <th>User Email</th><th>User First Name</th><th>User Last Name</th><th>User Password</th><th>Role</th><th>Active</th><th>Edit</th><th>View Items</th><th>Delete User</th>
            <c:forEach items="${userlist}" var="user">
                <tr>
                    <td>${user.getEmail()}</td>
                    <td>${user.getFirstName()}</td>
                    <td>${user.getLastName()}</td>
                    <td>${user.getPassword()}</td>
                    <td>${user.getRole().getRoleName()}</td>
                    <td><select name="active" disabled="true">
                <option value="1">Active</option>
                <option value="2" ${user.getActive() == false ? 'selected' : ''}>Inactive</option>
                </select></td>
                    <td><form action="admin" method="post"><input type="submit" value="Edit User"><input type="hidden" name="action" value="edit user"><input type="hidden" name="email" value="${user.getEmail()}"></form></td>
                    <td><form action="admin" method="post"><input type="submit" value="View User Items"><input type="hidden" name="action" value="view user items"><input type="hidden" name="email" value="${user.getEmail()}"></form></td>
                    <td><form action="admin" method="post"><input type="submit" value="Delete User"><input type="hidden" name="action" value="delete user"><input type="hidden" name="email" value="${user.getEmail()}"></form></td>
                </tr>
            </c:forEach>
            </table>
        </c:if>
        </c:if>
        <c:if test="${viewuseritem ne null}">
            <c:if test="${not empty useritemlist}">
                <table border="1">
                    <th>Owner email</th><th>Item Name</th><th>Item Price</th>
                <c:forEach items="${useritemlist}" var="item">
                <tr><td>${item.getOwner().getEmail()}</td>
                     <td>${item.getItemName()}</td>  
                     <td>${item.getPrice()}</td></tr>
                
                </c:forEach>
                </table>
            </c:if>
            <c:if test="${empty useritemlist}">
                <p>User does not have any items in their inventory</p>
            </c:if>
        </c:if>
        <c:if test="${edituser eq null && userview ne null}">
            <h2>Add User</h2>
            <form method="post" action="admin">
            Email: <input type="text" name="email" value="${email}"><br>
            First Name: <input type="text" name="firstname" value="${firstname}"><br>
            Last Name: <input type="text" name="lastname" value="${lastname}"><br>
            Password: <input type="password" name="password" value="${password}"><br>
            Role:<select name="role">
                <option value="1">System Admin</option>
                <option value="2">Regular User</option><br>
            <input type="hidden" name="action" value="add user" />
            <input type="submit" name="submit" value="Add user">
        </form>
        </c:if>
        <c:if test="${edituser ne null && userview ne null}">
            <h2>Edit User</h2>
            <form method="post" action="admin">
                Email: <input type="text" name="emailread" value="${user2.getEmail()}" disabled="true"><br>
            First Name: <input type="text" name="firstname" value="${user2.getFirstName()}"><br>
            Last Name: <input type="text" name="lastname" value="${user2.getLastName()}"><br>
            Password: <input type="password" name="password" value="${user2.getPassword()}"><br>
            Role:<select name="role">
                <option value="1">System Admin</option>
                <option value="2" ${user2.getRole().getRoleName() == 'regular user'? 'selected' : ''}>Regular User</option>
                </select><br>
                <select name="active">
                <option value="1">Active</option>
                <option value="2" ${user2.getActive() == false ? 'selected' : ''}>Inactive</option><BR>
                </select><br>
            <input type="hidden" name="action" value="save user" />
            <input type="submit" name="submit" value="Confirm Edit">
            <input type="hidden" name="email" value="${user2.getEmail()}">
        </form>
        <form method="post" action="admin">
            <input type="submit" name="submit" value="Cancel Edit">
            <input type="hidden" name="action" value="cancel user" />
        </form>
        </c:if>
            ${message}
    </body>
</html>
