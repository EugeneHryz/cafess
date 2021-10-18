package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;

import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class EditUserProfileCommand implements Command {

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
            editedUser.ifPresent(value -> request.getSession().setAttribute(USER, value));

        } catch (ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
