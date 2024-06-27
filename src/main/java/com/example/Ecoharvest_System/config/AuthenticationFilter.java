package com.example.Ecoharvest_System.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String uri = request.getRequestURI();

        // Skip authentication for login and public pages
        if (uri.equals("/") || uri.startsWith("/login") || uri.startsWith("/public")) {
            chain.doFilter(request, response);
            return;
        }

        // Check if the user is logged in
        if (request.getSession().getAttribute("loggedInUser") == null) {
            response.sendRedirect("/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
