package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.eugene.cafe.controller.command.AttributeName.MENU_ITEM_DELETED;
import static com.eugene.cafe.controller.command.RequestParameter.PARAM_ITEM_ID;

public class DeleteMenuItemCommand implements AjaxCommand {

    private static final Logger logger = LogManager.getLogger(DeleteMenuItemCommand.class);

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String itemIdParam = request.getParameter(PARAM_ITEM_ID);
        int itemId = Integer.parseInt(itemIdParam);

        try {
            if (menuService.deleteMenuItem(itemId)) {
                String jsonData = new Gson().toJson(MENU_ITEM_DELETED);
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
