package com.tsystems.javaschool.ecare.filters;

import com.tsystems.javaschool.ecare.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Kolia on 12.07.2015.
 */
@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter
{
    public void destroy()
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null)
        {
            httpRes.sendRedirect("login.jsp");
            //System.out.println("redirect");
        } else
        {
            chain.doFilter(request, response);
            //System.out.println("filter");
        }
    }

    public void init(FilterConfig config) throws ServletException
    {

    }

}
