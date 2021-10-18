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

public class UnbanUserCommand implements AjaxCommand {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userIdParam = request.getParameter(PARAM_USER_ID);
        int userId = Integer.parseInt(userIdParam);

        try {
            Optional<User> unbannedUser = userService.changeUserStatus(userId, UserStatus.NOT_ACTIVATED);

            if (unbannedUser.isPresent()) {
                String jsonData = new Gson().toJson(unbannedUser.get());
                response.getWriter().write(jsonData);
            }
        } catch (ServiceException e) {
            // todo: write log
            response.setStatus(400);
            response.getWriter().write(e.getMessage());
        }
    }
}
