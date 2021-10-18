package com.eugene.cafe.controller;

import java.io.*;

import com.eugene.cafe.controller.command.Command;
import com.eugene.cafe.controller.command.CommandProvider;
import com.eugene.cafe.controller.command.RequestParameter;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.controller.command.impl.DefaultCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "controller", urlPatterns = "/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 20)
public class Controller extends HttpServlet {

    private final CommandProvider provider = CommandProvider.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String commandName = request.getParameter(RequestParameter.PARAM_COMMAND);

        Command command = provider.getCommand(commandName);
        Router router = command.execute(request);

        switch (router.getType()) {
            case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
            case REDIRECT -> response.sendRedirect(router.getPage());
        }
    }
}