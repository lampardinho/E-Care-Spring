package com.tsystems.javaschool.ecare.servlets;

import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Kolia on 01.07.2015.
 */
@WebServlet(name = "LoginServlet")
public class LobbyServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //String isAdmin = request.getParameter("isAdmin");
        //out.println(isAdmin);

        UserService userService = UserService.getInstance();
        try
        {
            HttpSession session = request.getSession();

            User user = userService.findClient(email, password);
            session.setAttribute("user", user);


            if (user.getIsAdmin())
            {
                request.getRequestDispatcher("admin_lobby").include(request, response);
            } else
            {
                request.getRequestDispatcher("client_lobby").include(request, response);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {


    }
}
