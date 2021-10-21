package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.eugene.cafe.controller.command.RequestParameter.PARAM_PAGE_NUMBER;

public class LoadUserPageCommand implements AjaxCommand {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);

        try {
            List<User> users = userService.getSubsetOfUsers(pageNumberParam);
            String jsonData = new Gson().toJson(users);
            response.getWriter().write(jsonData);

        } catch (ServiceException e) {
            // todo: write log
            response.setStatus(400);
            response.getWriter().write(e.getMessage());
        }
    }
}
