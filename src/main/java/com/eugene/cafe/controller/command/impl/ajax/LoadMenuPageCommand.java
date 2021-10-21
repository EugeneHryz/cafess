package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.eugene.cafe.controller.command.RequestParameter.PARAM_PAGE_NUMBER;

public class LoadMenuPageCommand implements AjaxCommand {

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);
        int pageNumber = Integer.parseInt(pageNumberParam);

        try {
            List<MenuItem> menuItems = menuService.getSubsetOfMenuItems(pageNumber, MenuItemSortOrder.PRICE_ASCENDING, null);
            String jsonData = new Gson().toJson(menuItems);
            response.getWriter().write(jsonData);

        } catch (ServiceException e) {
            // todo: write log
            response.setStatus(400);
            response.getWriter().write(e.getMessage());
        }
    }
}
