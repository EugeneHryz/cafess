package com.eugene.cafe.controller;

import java.io.*;

import com.eugene.cafe.command.Command;
import com.eugene.cafe.command.CommandProvider;
import com.eugene.cafe.command.RequestParameter;
import com.eugene.cafe.pool.ConnectionPool;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {

    private final CommandProvider provider = CommandProvider.getInstance();

    public void init() {
        ConnectionPool.getInstance();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(RequestParameter.PARAM_COMMAND);

        Command command = provider.getCommand(commandName);
        String path = command.execute(request);

        request.getRequestDispatcher(path).forward(request, response);

//        if (path != null) {
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
//            dispatcher.forward(request, response);
//        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        response.setContentType("text/html");
//
//        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

//    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//
//    }

    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}