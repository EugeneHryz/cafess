package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static com.eugene.cafe.controller.command.RequestParameter.PARAM_USER_ID;

public class BanUserCommand implements AjaxCommand {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userIdParam = request.getParameter(PARAM_USER_ID);
        int userId = Integer.parseInt(userIdParam);

        String jsonData;
        try {
            Optional<User> bannedUser = userService.changeUserStatus(userId, UserStatus.BANNED);

            if (bannedUser.isPresent()) {
                jsonData = new Gson().toJson(bannedUser.get());
                response.getWriter().write(jsonData);
            }
        } catch (ServiceException e) {
            // todo: write log
            response.setStatus(400);
            response.getWriter().write(e.getMessage());
        }
    }
}
