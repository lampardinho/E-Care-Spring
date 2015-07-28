package com.tsystems.javaschool.ecare.controllers;

import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@Controller
public class LobbyController
{
    @Autowired
    UserService userService;

    @RequestMapping(value = "/lobby", method = RequestMethod.POST)
    protected String login(HttpServletRequest req)
    {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String isAdmin = req.getParameter("isAdmin");

        try
        {
            HttpSession session = req.getSession();

            User user = userService.findClient(email, password);
            session.setAttribute("user", user);


            if (user.getIsAdmin() || isAdmin != null)
            {
                return "admin_lobby";
            } else
            {
                return "client_lobby";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return "login.jsp";
        }

    }


}
