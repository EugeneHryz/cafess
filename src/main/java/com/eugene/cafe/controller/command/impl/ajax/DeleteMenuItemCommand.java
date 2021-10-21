package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.UserStatus;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import static com.eugene.cafe.controller.command.RequestAttribute.LOCALE;
import static com.eugene.cafe.controller.command.RequestAttribute.MENU_ITEM_DELETED;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_ITEM_ID;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_USER_ID;

public class DeleteMenuItemCommand implements AjaxCommand {

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String itemIdParam = request.getParameter(PARAM_ITEM_ID);
        int itemId = Integer.parseInt(itemIdParam);

        try {
            if (menuService.deleteMenuItem(itemId)) {
                String jsonData = new Gson().toJson(MENU_ITEM_DELETED);
                response.getWriter().write(jsonData);
            }
        } catch (ServiceException e) {
            // todo: write log
            response.setStatus(400);
            response.getWriter().write(e.getMessage());
        }
    }
}
