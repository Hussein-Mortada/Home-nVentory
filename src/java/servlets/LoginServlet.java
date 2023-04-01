/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author Hussein
 */
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =request.getSession();
        if(session.getAttribute("email")!=null&&session.getAttribute("password")!=null){
            getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        }
        else{
         getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
      
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = (String) request.getParameter("action");
        if(action!=null &&action.equals("register")){
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
        else if (action!=null &&action.equals("login")){
            String email=request.getParameter("email");
            String password=request.getParameter("password");
            if(email==null || email.equals("")||password==null||password.equals("")){
                request.setAttribute("message", "Invalid Login.");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
            else{
                UserService us = new UserService();
                try{
                    User user = us.get(email);

                if(user==null){
                    request.setAttribute("message", "Invalid Login.");
                    request.setAttribute("email",email);
                    request.setAttribute("password","");
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
                else if(!user.getActive()){
                    request.setAttribute("message", "User is inactive. Please message an administrator to reactivate your account");
                }
                else if(!user.getPassword().equals(password)){
                    request.setAttribute("message", "Wrong password");
                }
                else{
                  HttpSession session =request.getSession();
                  session.setAttribute("email", email);
                  session.setAttribute("password", password);
                  getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                    }
                }
                catch(Exception ex){

                }
        }
      }
    }

}
