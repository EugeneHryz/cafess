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

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {

    private final CommandProvider provider = CommandProvider.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Config.set(request.getSession(), Config.FMT_LOCALE, Locale.forLanguageTag("en-US"));

        processRequest(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("in process request");
        String commandName = request.getParameter(RequestParameter.PARAM_COMMAND);

        System.out.println("command name: " + commandName);

        Command command = provider.getCommand(commandName);
        Router router = command.execute(request);

        switch (router.getType()) {
            case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
            case REDIRECT -> response.sendRedirect(router.getPage());
        }
    }
}