package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import com.eugene.cafe.controller.command.AttributeName;
import com.eugene.cafe.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {

        String locale = request.getParameter(PARAM_LOCALE);

        request.getSession().setAttribute(AttributeName.LOCALE, locale);
        String previousRequest = (String) request.getSession().getAttribute(AttributeName.PREVIOUS_REQUEST);

        Router router = new Router(previousRequest, Router.RouterType.REDIRECT);
        return router;
    }
}
