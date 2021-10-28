package com.eugene.cafe.controller.filter;

import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.AttributeName.*;

import com.eugene.cafe.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/user/*", "/jsp/admin/*"})
public class CheckRoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String role = (String) httpRequest.getSession().getAttribute(ROLE);
        String url = httpRequest.getRequestURL().toString();

        if (role != null && (url.contains(role.toLowerCase()) || role.equals(UserRole.ADMIN.name()))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/" + ERROR_PAGE);
        }
    }
}
