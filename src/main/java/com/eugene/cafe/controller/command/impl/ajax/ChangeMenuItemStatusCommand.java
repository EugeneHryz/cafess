package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static com.eugene.cafe.controller.command.RequestParameter.PARAM_ITEM_ID;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_MENU_ITEM_STATUS;

public class ChangeMenuItemStatusCommand implements AjaxCommand {

    private static final Logger logger = LogManager.getLogger(ChangeMenuItemStatusCommand.class);

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String itemIdParam = request.getParameter(PARAM_ITEM_ID);
        int itemId = Integer.parseInt(itemIdParam);
        String newStatusParam = request.getParameter(PARAM_MENU_ITEM_STATUS);
        boolean newStatus = Boolean.parseBoolean(newStatusParam);

        try {
            Optional<MenuItem> menuItem = menuService.changeMenuItemStatus(itemId, newStatus);
            if (menuItem.isPresent()) {

                String jsonData = new Gson().toJson(menuItem.get());
                try {
                    response.getWriter().write(jsonData);
                } catch (IOException e) {
                    logger.error("Error while writing a response body", e);
                }
            }
        } catch (ServiceException e) {
            logger.error("Unable archive menuItem with id: " + itemId, e);
            response.setStatus(500);
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException ioException) {
                logger.error("Error while writing a response body", e);
            }
        }
    }
}
