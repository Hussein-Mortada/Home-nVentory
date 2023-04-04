/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import models.Role;
import models.User;
import services.CategoryService;
import services.ItemService;
import services.RoleService;
import services.UserService;

/**
 *
 * @author Hussein
 */
public class AdminServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =request.getSession();
        User user = (User)session.getAttribute("user");
         if(user==null){
            response.sendRedirect("login");
        } if(user!=null&&user.getRole().getRoleName().equals("regular user")){
            ItemService is = new ItemService();
            List<Item> itemlist = is.getAll(user);
            CategoryService cs = new CategoryService();
            try{
                List<Category> categorylist = cs.getAll();
                session.setAttribute("categorylist", categorylist);
            }
            catch(Exception ex){
            }
            session.setAttribute("itemlist", itemlist);
            getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        }
        if(user!=null&&user.getRole().getRoleName().equals("system admin")){
            getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        }
      
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                HttpSession session =request.getSession();
                User user = (User)session.getAttribute("user");
                UserService us = new UserService();
                ItemService is = new ItemService();
                CategoryService cs = new CategoryService();
                RoleService rs = new RoleService();
            String action = (String) request.getParameter("action");
            if(action==null || action==""){
                response.sendRedirect("admin");
            }
            if(action.equals("logout")){
                session.invalidate();
                response.sendRedirect("login");
            }
            else if(action.equals("cancel user")){
                session.setAttribute("categoryview", null);
                session.setAttribute("userview", 1);
                request.setAttribute("message", "Cancelled");
                getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            }
            else if(action.equals("cancel category")){
                session.setAttribute("categoryview", 1);
                session.setAttribute("userview", null);
                request.setAttribute("message", "Cancelled");
                getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            }
            else if(action.equals("view all users")){
                session.setAttribute("categoryview", null);
                session.setAttribute("userview", 1);
                    try {
                        List<User> users = us.getAll();
                        session.setAttribute("userlist", users);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            }
            else if(action.equals("view user items")){
                request.setAttribute("viewuseritem", 1);
                String email = request.getParameter("email");
                    try {
                        User user2view = us.get(email);
                        List<Item> useritems = is.getAll(user2view);
                        request.setAttribute("useritemlist", useritems);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            }
            else if(action.equals("edit user")){
                request.setAttribute("edituser", 1);
                String email = request.getParameter("email");
                    try {
                        User user2 = us.get(email);
                        request.setAttribute("user2", user2);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            }
             else if(action.equals("save user")){
                request.setAttribute("edituser", null);
                String email = request.getParameter("email");
                String firstname = request.getParameter("firstname");
                String  lastname= request.getParameter("lastname");
                String password = request.getParameter("password");
                String active = request.getParameter("active");
                    try {
                        User u2 = us.get(email);
                        if(firstname==null || firstname==""||lastname==null||lastname==""||password==null||password==""){
                            request.setAttribute("message","No fields can be left empty" );
                        }
                        String roleid = request.getParameter("role");
                        Role role;
                        if(roleid.equals("1")){
                            role = rs.get(1);
                        }
                        else{
                            role=rs.get(2);
                        }
                        System.out.println(u2.getFirstName());
                        u2.setFirstName(firstname);
                        System.out.println(u2.getFirstName());
                        u2.setLastName(lastname);
                        u2.setPassword(password);
                        u2.setRole(role);
                        if(active.equals("1")){
                            u2.setActive(true);
                        }
                        else{
                            u2.setActive(false);
                        }
                        us.update(u2);
                        UserDB udb = new UserDB();
                        udb.update(u2);
                        List<User> userlist = us.getAll();
                        session.setAttribute("userlist", userlist);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
            }
             else if (action.equals("add user")){
                String email = (String) request.getParameter("email");
                String firstName = (String) request.getParameter("firstname");
                String lastName = (String) request.getParameter("lastname");
                String password = (String) request.getParameter("password");
                String roleid = request.getParameter("role");
                Role role;
                if(roleid.equals("1")){
                    role = rs.get(1);
                }
                 else{
                    role=rs.get(2);
                }
                if(email==null || email=="" ||firstName==null || firstName==""||lastName==null||lastName==""||password==null||password==""){
                request.setAttribute("message", "Please do not leave any fields empty");
                request.setAttribute("email", email);
                request.setAttribute("firstname", firstName);
                request.setAttribute("lastname", lastName);
                getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                }
                try {
                String message = us.add(email, firstName, lastName, password, role);
                if(message.equals("User added.")){
                    request.setAttribute("message", message);
                    List<User> users = us.getAll();
                    session.setAttribute("userlist", users);
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                }
                else{
                    request.setAttribute("message", message);
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                }
                } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
             else if (action.equals("view all categories")){
                 session.setAttribute("categoryview", 1);
                 session.setAttribute("userview", null);
                    try {
                        List<Category> categorylist = cs.getAll();
                        session.setAttribute("categorylist", categorylist);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                 
             }
             else if(action.equals("edit category")){
                 request.setAttribute("editcategory", 1);
                 String catID = request.getParameter("categoryid");
                 Integer id = Integer.parseInt(catID);
                 Category cat= cs.get(id);
                 session.setAttribute("category", cat);
                 getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
             }
             else if(action.equals("save category")){
                 String categoryname = request.getParameter("category");
                 Category cat = (Category) session.getAttribute("category");
                 if(categoryname!=null&&categoryname!=""){
                     cat.setCategoryName(categoryname);
                     try {
                         cs.update(cat);
                         request.setAttribute("message", "category updated");
                     } catch (Exception ex) {
                         Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                     }
                      try {
                        List<Category> categorylist = cs.getAll();
                        session.setAttribute("categorylist", categorylist);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                 }
                 else{
                     request.setAttribute("message", "invalid category name");
                     getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                 }
             }
             else if(action.equals("add category")){
                String categoryname = request.getParameter("category");
                if(categoryname!=null&&categoryname!=""){
                    Category cat = new Category();
                    cat.setCategoryName(categoryname);
                    try {
                        cs.add(cat);
                        List<Category> categorylist = cs.getAll();
                        session.setAttribute("categorylist", categorylist);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     request.setAttribute("message", "category added");
                    getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                }
             }
             else if (action.equals("delete user")){
                 String email = request.getParameter("email");
                 if(user.getEmail().equals(email)){
                     request.setAttribute("message", "Cannot delete yourself");
                     getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                 }
                    try {
                        User u2 = us.get(email);
                        us.delete(user, u2);
                        List<User> users = us.getAll();
                        session.setAttribute("userlist", users);
                        request.setAttribute("message", "User Deleted");
                        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                    } catch (Exception ex) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 
             }
    }

}
