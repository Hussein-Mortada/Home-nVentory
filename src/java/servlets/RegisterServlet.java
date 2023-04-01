/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

/**
 *
 * @author Hussein
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =request.getSession();
        if(session.getAttribute("email")!=null&&session.getAttribute("password")!=null){
            getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        }
        else{
         getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = (String) request.getParameter("action");
        if(action!=null&&action.equals("cancel")){
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
        else{
            String email = (String) request.getParameter("email");
            String firstName = (String) request.getParameter("firstname");
            String lastName = (String) request.getParameter("lastname");
            String password = (String) request.getParameter("password");
            if(email==null || email=="" ||firstName==null || firstName==""||lastName==null||lastName==""||password==null||password==""){
                request.setAttribute("message", "Please do not leave any fields empty");
                request.setAttribute("email", email);
                request.setAttribute("firstname", firstName);
                request.setAttribute("lastname", lastName);
                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            }
            UserService us = new UserService();
            RoleService rs = new RoleService();
            Role role = rs.get(2);
            try {
                String message = us.add(email, firstName, lastName, password, role);
                if(message.equals("User added.")){
                    request.setAttribute("message", message);
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
                else{
                    request.setAttribute("message", message);
                    getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
//            try{
//                User user = us.get(email);
//                if(user!=null){
//                request.setAttribute("message", "Email already in use");
//                request.setAttribute("email", email);
//                request.setAttribute("firstname", firstName);
//                request.setAttribute("lastname", lastName);
//                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
//                }
//                else{
//                    RoleService rs = new RoleService();
//                    Role role = rs.get(2);
//                    user = new User(email,true,firstName,lastName,password);
//                    user.setRole(role);
//                    us.add(email, firstName, lastName, password, role);
//                }
//            }
//
//            catch (Exception ex) {
//                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

}
