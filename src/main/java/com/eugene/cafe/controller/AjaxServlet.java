package com.eugene.cafe.controller;

import static com.eugene.cafe.controller.command.RequestParameter.*;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.UserService;
import com.eugene.cafe.model.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="ajax", urlPatterns = "/ajax")
public class AjaxServlet extends HttpServlet {

    private static final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command = req.getParameter(PARAM_COMMAND);

        String jsonData = null;
        if (command.equals("get_user_count")) {

            try {
                int userCount = userService.getUserCount();
                jsonData = new Gson().toJson(userCount);
            } catch (ServiceException e) {
                // todo: write log
            }

        } else if (command.equals("go_to_user_page")) {

            String pageNumberParam = req.getParameter(PARAM_PAGE_NUMBER);
            int pageNumber = Integer.parseInt(pageNumberParam);

            try {
                List<User> users = userService.getSubsetOfUsers(pageNumber);
                jsonData = new Gson().toJson(users);
            } catch (ServiceException e) {
                // todo: write log
            }
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonData);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
