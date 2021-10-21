package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GetMenuItemCountCommand implements AjaxCommand {

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int menuItemCount = menuService.getMenuItemCountByCategory(null);
            String jsonData = new Gson().toJson(menuItemCount);
            response.getWriter().write(jsonData);

        } catch (ServiceException e) {
            // todo: write log
            response.setStatus(400);
            response.getWriter().write(e.getMessage());
        }
    }
}
