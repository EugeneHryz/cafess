package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Optional;

public class LogInCommand implements Command {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);

        Router router;
        try {
            Optional<User> client = userService.signIn(email, password);
            if (client.isPresent()) {
                request.getSession().setAttribute(USER, client.get());
                request.getSession().setAttribute(ROLE, client.get().getRole().name());

                router = new Router(MAIN_PAGE, Router.RouterType.REDIRECT);
            } else {
                Locale locale = Locale.forLanguageTag((String) request.getSession(false).getAttribute(LOCALE));
                ResourceManager manager = new ResourceManager("message", locale);
                request.setAttribute(INVALID_LOGIN_OR_PASSWORD, manager.getProperty(INVALID_LOGIN_OR_PASSWORD));

                router = new Router(LOGIN_PAGE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
