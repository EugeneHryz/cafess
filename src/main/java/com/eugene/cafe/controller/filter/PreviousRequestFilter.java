package com.eugene.cafe.controller.filter;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = {"*.jsp"})
public class PreviousRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String uri = httpRequest.getRequestURI();
        httpRequest.getSession().setAttribute(PREVIOUS_REQUEST, uri);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
