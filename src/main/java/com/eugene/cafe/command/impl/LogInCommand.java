package com.eugene.cafe.command.impl;

import com.eugene.cafe.command.Command;
import com.eugene.cafe.command.PagePath;
import com.eugene.cafe.command.RequestAttribute;
import com.eugene.cafe.command.RequestParameter;
import com.eugene.cafe.entity.Client;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.service.ClientService;
import com.eugene.cafe.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class LogInCommand implements Command {

    private final ClientService clientService = new ClientServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {

        String email = request.getParameter(RequestParameter.PARAM_EMAIL);
        String password = request.getParameter(RequestParameter.PARAM_PASSWORD);

        String path;
        Optional<Client> client;
        try {
            client = clientService.signIn(email, password);
            if (client.isEmpty()) {
                // todo: probably need to change later
                ResourceManager manager = new ResourceManager("messages");
                request.setAttribute(RequestAttribute.INVALID_LOGIN_OR_PASSWORD,
                        manager.getProperty(RequestAttribute.INVALID_LOGIN_OR_PASSWORD));

                path = PagePath.LOGIN_PAGE;
            } else {
                path = PagePath.MAIN_PAGE;
            }
        } catch (ServiceException e) {
            // todo: write log
            path = PagePath.TEST_ERROR_PAGE;
        }
        return path;
    }
}
