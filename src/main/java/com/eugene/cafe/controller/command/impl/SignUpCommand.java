package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.AttributeName.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;

import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.dto.UserDto;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.eugene.cafe.model.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Optional;

public class SignUpCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SignUpCommand.class);

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        String name = request.getParameter(PARAM_NAME);
        String surname = request.getParameter(PARAM_SURNAME);
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);

        Locale locale = Locale.forLanguageTag((String) request.getSession().getAttribute(LOCALE));
        ResourceManager manager = new ResourceManager("message", locale);

        Router router;
        try {
            if (UserValidator.validateUser(name, surname, email, password)) {

                Optional<UserDto> user = userService.signUp(name, surname, email, password);
                if (user.isPresent()) {
                    request.getSession().setAttribute(USER, user.get());
                    request.getSession().setAttribute(ROLE, user.get().getRole().name());

                    router = new Router(MAIN_PAGE, Router.RouterType.REDIRECT);
                } else {
                    request.setAttribute(EMAIL_ALREADY_EXISTS, manager.getProperty(EMAIL_ALREADY_EXISTS));
                    router = new Router(SIGNUP_PAGE, Router.RouterType.FORWARD);
                }
            } else {
                request.setAttribute(INVALID_SIGN_UP_DATA, manager.getProperty(INVALID_SIGN_UP_DATA));
                router = new Router(SIGNUP_PAGE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("Unable to sign up", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
