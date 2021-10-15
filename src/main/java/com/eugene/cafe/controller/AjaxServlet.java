package com.eugene.cafe.controller;

import static com.eugene.cafe.controller.command.RequestParameter.*;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.controller.command.AjaxCommandProvider;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.UserStatus;
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
import java.util.Optional;

@WebServlet(name="ajax", urlPatterns = "/ajax")
public class AjaxServlet extends HttpServlet {

    private static final AjaxCommandProvider commandProvider = AjaxCommandProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandName = req.getParameter(PARAM_COMMAND);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        AjaxCommand command = commandProvider.getCommand(commandName);
        command.execute(req, resp);
    }
}
