package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;

import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Client;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.ClientService;
import com.eugene.cafe.model.service.impl.ClientServiceImpl;
import com.eugene.cafe.model.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Optional;

public class SignUpCommand implements Command {

    private static final ClientService clientService = new ClientServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String name = request.getParameter(PARAM_NAME);
        String surname = request.getParameter(PARAM_SURNAME);
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);

        System.out.println("name: " + name);
        System.out.println("surname: " + surname);
        System.out.println("email: " + email);
        System.out.println("password: " + password);

        Router router;
        Locale locale = Locale.forLanguageTag((String) request.getSession().getAttribute(LOCALE));
        try {
            ResourceManager manager = new ResourceManager("message", locale);

            if (UserValidator.validateUser(name, surname, email, password)) {
                Optional<Client> client = clientService.signUp(name, surname, email, password);
                if (client.isPresent()) {
                    // todo: save user and his role to the session
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
