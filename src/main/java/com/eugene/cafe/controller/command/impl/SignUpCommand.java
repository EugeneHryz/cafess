package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;

import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.eugene.cafe.model.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Optional;

public class SignUpCommand implements Command {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String name = request.getParameter(PARAM_NAME);
        String surname = request.getParameter(PARAM_SURNAME);
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);

        Router router;
        Locale locale = Locale.forLanguageTag((String) request.getSession().getAttribute(LOCALE));
        try {
            ResourceManager manager = new ResourceManager("message", locale);

            if (UserValidator.validateUser(name, surname, email, password)) {
                Optional<User> client = userService.signUp(name, surname, email, password);
                if (client.isPresent()) {
                    request.getSession().setAttribute(USER, client.get());
                    request.getSession().setAttribute(ROLE, client.get().getRole());

                    router = new Router(MAIN_PAGE, Router.RouterType.FORWARD);
                } else {
                    request.setAttribute(LOGIN_ALREADY_EXISTS, manager.getProperty(LOGIN_ALREADY_EXISTS));

                    router = new Router(SIGNUP_PAGE, Router.RouterType.FORWARD);
                }
            } else {
                request.setAttribute(INVALID_SIGN_UP_DATA, manager.getProperty(INVALID_SIGN_UP_DATA));

                router = new Router(SIGNUP_PAGE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
