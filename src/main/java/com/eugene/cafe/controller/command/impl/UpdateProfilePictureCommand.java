package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;
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
import java.util.Arrays;
import java.util.Optional;

import static com.eugene.cafe.controller.command.PagePath.ERROR_PAGE;
import static com.eugene.cafe.controller.command.PagePath.PROFILE_SETTINGS_PAGE;
import static com.eugene.cafe.controller.command.RequestAttribute.EXCEPTION;
import static com.eugene.cafe.controller.command.RequestAttribute.USER;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_FILE;

public class UpdateProfilePictureCommand implements Command {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);
        int id = user.getId();
        String idAsString = Integer.toString(id);

        String uploadDir = request.getServletContext().getInitParameter("upload.location");
        File file = new File(uploadDir);
        if (!file.exists()) {
            file.mkdir();
        }

        Router router = new Router(PROFILE_SETTINGS_PAGE, Router.RouterType.FORWARD);
        try {
            String submittedFileName = request.getPart(PARAM_FILE).getSubmittedFileName();

            String userDir = uploadDir + File.separator + idAsString;
            File uploadUserDir = new File(userDir);
            if (!uploadUserDir.exists()) {
                uploadUserDir.mkdir();
            } else {
                File[] files = uploadUserDir.listFiles();
                if (files != null) {
                    Arrays.stream(files).forEach(File::delete);
                }
            }
            for (Part part : request.getParts()) {
                part.write(userDir + File.separator + submittedFileName);
            }

            Optional<User> updatedUser = userService.updateProfilePicture(id, idAsString + File.separator + submittedFileName);
            updatedUser.ifPresent(value -> request.getSession().setAttribute(USER, value));

        } catch (IOException | ServletException | ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        return router;
    }
}
