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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.eugene.cafe.controller.command.PagePath.ERROR_PAGE;
import static com.eugene.cafe.controller.command.PagePath.PROFILE_SETTINGS_PAGE;
import static com.eugene.cafe.controller.command.AttributeName.EXCEPTION;
import static com.eugene.cafe.controller.command.AttributeName.USER;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_FILE;

public class UpdateProfilePictureCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateProfilePictureCommand.class);

    private static final UserService userService = new UserServiceImpl();

    private static final String UPLOAD_LOCATION = "upload.location";
    private static final String USERS_DIR = "users";

    @Override
    public Router execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(USER);
        int id = user.getId();
        String idAsString = Integer.toString(id);

        String uploadDir = request.getServletContext().getInitParameter(UPLOAD_LOCATION)
                + File.separator + USERS_DIR;
        File file = new File(uploadDir);
        if (!file.exists()) {
            file.mkdir();
        }

        Router router = new Router(PROFILE_SETTINGS_PAGE, Router.RouterType.FORWARD);
        try {
            String submittedFileName = request.getPart(PARAM_FILE).getSubmittedFileName();
            if (submittedFileName == null || submittedFileName.isEmpty()) {
                return router;
            }

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
            logger.error("Unable to update profile picture", e);
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
