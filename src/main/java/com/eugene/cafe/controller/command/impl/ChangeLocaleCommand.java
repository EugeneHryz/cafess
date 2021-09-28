package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import com.eugene.cafe.controller.command.RequestAttribute;
import com.eugene.cafe.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

public class ChangeLocaleCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {

        String locale = request.getParameter(PARAM_LOCALE);

        request.getSession().setAttribute(RequestAttribute.LOCALE, locale);
        String previousRequest = (String) request.getSession().getAttribute(RequestAttribute.PREVIOUS_REQUEST);

        Router router = new Router(previousRequest, Router.RouterType.REDIRECT);

        return router;
    }
}
