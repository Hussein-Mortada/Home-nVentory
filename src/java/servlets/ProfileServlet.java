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
import models.User;
import services.CategoryService;
import services.ItemService;
import services.UserService;

/**
 *
 * @author Hussein
 */
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =request.getSession();
        User user = (User)session.getAttribute("user");
        if(user==null){
            response.sendRedirect("login");
        }
        if(user!=null&&user.getRole().getRoleName().equals("regular user")){
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
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
        String action = (String)request.getParameter("action");
        if(action==null || action.equals("")){
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        }
        if(action!=null && !action.equals("") && action.equals("logout")){
            session.invalidate();
            response.sendRedirect("login");
        }
        if(action!=null && !action.equals("") && action.equals("home")){
            response.sendRedirect("home");
        }
        if(action!=null && !action.equals("") && action.equals("save")){
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String password = request.getParameter("password");
            if(firstname==null || firstname.equals("")||lastname==null||lastname.equals("")||password==null||password.equals("")){
                request.setAttribute("message", "Invalid Parameters.  Please do not leave any fields empty");
                getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
            }
            else{
                user.setFirstName(firstname);
                user.setLastName(lastname);
                user.setPassword(password);
                UserService us = new UserService();
                UserDB udb = new UserDB();
                try {
                    udb.update(user);
                } catch (Exception ex) {
                    Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("message", "Update Successful");
                session.setAttribute("user", user);
                getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
            }
        }
        if(action!=null && !action.equals("") && action.equals("disable")){
            request.setAttribute("disable", "1");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        }
        if(action!=null && !action.equals("") && action.equals("confirm")){
            user.setActive(false);
            UserService us = new UserService();
            try {
                    us.update(user);
                } catch (Exception ex) {
                    Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            session.invalidate();
            request.setAttribute("message", "User Disabled");
             getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        if(action!=null && !action.equals("") && action.equals("cancel")){
            request.setAttribute("message", "Cancelled");
            getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        }
    }

}
