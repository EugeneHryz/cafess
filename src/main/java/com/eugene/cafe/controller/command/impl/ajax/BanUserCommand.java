package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dto.UserDto;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static com.eugene.cafe.controller.command.RequestParameter.PARAM_USER_ID;

public class BanUserCommand implements AjaxCommand {

    private static final Logger logger = LogManager.getLogger(BanUserCommand.class);

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String userIdParam = request.getParameter(PARAM_USER_ID);
        int userId = Integer.parseInt(userIdParam);

        try {
            Optional<UserDto> bannedUser = userService.changeUserStatus(userId, UserStatus.BANNED);

            if (bannedUser.isPresent()) {
                String jsonData = new Gson().toJson(bannedUser.get());
                try {
                    response.getWriter().write(jsonData);
                } catch (IOException e) {
                    logger.error("Error while writing a response body", e);
                }
            }
        } catch (ServiceException e) {
            logger.error("Unable to ban user", e);
            response.setStatus(500);
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException ioException) {
                logger.error("Error while writing a response body", e);
            }
        }
    }
}
