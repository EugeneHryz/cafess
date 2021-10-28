package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.eugene.cafe.controller.command.RequestParameter.PARAM_PAGE_NUMBER;

public class LoadUserPageCommand implements AjaxCommand {

    private static final Logger logger = LogManager.getLogger(LoadUserPageCommand.class);

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);

        try {
            List<User> users = userService.getSubsetOfUsers(pageNumberParam);
            String jsonData = new Gson().toJson(users);
            try {
                response.getWriter().write(jsonData);
            } catch (IOException e) {
                logger.error("Error while writing a response body", e);
            }
        } catch (ServiceException e) {
            logger.error("Unable to load user page command", e);
            response.setStatus(500);
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException ioException) {
                logger.error("Error while writing a response body", ioException);
            }
        }
    }
}
