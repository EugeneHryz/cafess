package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
import static com.eugene.cafe.controller.command.PagePath.*;
import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.Client;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.ClientService;
import com.eugene.cafe.model.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Optional;

public class LogInCommand implements Command {

    private static final ClientService clientService = new ClientServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);

        Router router;
        try {
            Optional<Client> client = clientService.signIn(email, password);
            if (client.isPresent()) {
                // todo: save user and his role to the session
                router = new Router(MAIN_PAGE, Router.RouterType.FORWARD);
            } else {

                Locale locale = Locale.forLanguageTag((String) request.getSession().getAttribute(LOCALE));
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
