package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GetUserCountCommand implements AjaxCommand {

    private static final Logger logger = LogManager.getLogger(GetUserCountCommand.class);

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            int userCount = userService.getUserCount();
            String jsonData = new Gson().toJson(userCount);
            try {
                response.getWriter().write(jsonData);
            } catch (IOException e) {
                logger.error("Error while writing a response body", e);
            }
        } catch (ServiceException e) {
            logger.error("Unable to get user count", e);
            response.setStatus(500);
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException ioException) {
                logger.error("Error while writing a response body", ioException);
            }
        }
    }
}
