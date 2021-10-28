package com.eugene.cafe.controller.filter;

import static com.eugene.cafe.controller.command.AttributeName.*;

import static com.eugene.cafe.controller.command.RequestParameter.*;

import com.eugene.cafe.controller.command.CommandType;
import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/controller", "/ajax"})
public class CommandFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String role = (String) httpRequest.getSession().getAttribute(ROLE);
        if (role == null) {
            role = UserRole.GUEST.name();
        }

        String commandName = httpRequest.getParameter(PARAM_COMMAND);
        CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
        if (commandType.isValidRole(role)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/" + ERROR_PAGE);
        }
    }
}
