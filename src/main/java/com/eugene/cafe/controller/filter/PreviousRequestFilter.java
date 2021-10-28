package com.eugene.cafe.controller.filter;

import static com.eugene.cafe.controller.command.AttributeName.*;

import com.eugene.cafe.controller.command.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class PreviousRequestFilter implements Filter {

    private static final String JSP = "jsp/";

    private static final String CONTROLLER = "controller";

    private static final String CHANGE_LOCALE = "command=change_locale";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String url = httpRequest.getRequestURL().toString();
        String contextPath = servletRequest.getServletContext().getContextPath();

        if (url.contains(JSP)) {
            String prevRequest = url.substring(url.indexOf(JSP));
            httpRequest.getSession().setAttribute(PREVIOUS_REQUEST, prevRequest);

        } else if (url.contains(CONTROLLER) && httpRequest.getQueryString() != null
                && !httpRequest.getQueryString().contains(CHANGE_LOCALE)) {

            String prevRequest = url.substring(url.indexOf(CONTROLLER)) + "?" + httpRequest.getQueryString();
            httpRequest.getSession().setAttribute(PREVIOUS_REQUEST, prevRequest);
        } else if (url.endsWith(contextPath + "/")) {

            httpRequest.getSession().setAttribute(PREVIOUS_REQUEST, PagePath.INDEX);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
