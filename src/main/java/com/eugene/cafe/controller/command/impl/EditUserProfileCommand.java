package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.AttributeName.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;

import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Optional;

public class EditUserProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditUserProfileCommand.class);

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);
        int id = user.getId();
        String name = request.getParameter(PARAM_NAME);
        String surname = request.getParameter(PARAM_SURNAME);
        String email = request.getParameter(PARAM_EMAIL);

        Router router = new Router(PROFILE_SETTINGS_PAGE, Router.RouterType.FORWARD);
        try {
            Optional<User> editedUser = userService.editProfile(id, name, surname, email);
            if (editedUser.isPresent()) {
                request.getSession().setAttribute(USER, editedUser.get());
            } else {
                Locale locale = Locale.forLanguageTag((String) request.getSession(false).getAttribute(LOCALE));
                ResourceManager manager = new ResourceManager("message", locale);

                request.setAttribute(EDIT_PROFILE_FAIL, manager.getProperty(EDIT_PROFILE_FAIL));
            }
        } catch (ServiceException e) {
            logger.error("Unable to edit user profile", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
