package com.eugene.cafe.controller.command.impl.ajax;

import com.eugene.cafe.controller.command.AjaxCommand;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuSortOrder;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.eugene.cafe.controller.command.RequestParameter.PARAM_PAGE_NUMBER;

public class LoadMenuPageCommand implements AjaxCommand {

    private static final Logger logger = LogManager.getLogger(LoadMenuPageCommand.class);

    private static final MenuService menuService = new MenuServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String pageNumberParam = request.getParameter(PARAM_PAGE_NUMBER);
        int pageNumber = Integer.parseInt(pageNumberParam);

        try {
            List<MenuItem> menuItems = menuService.getSubsetOfAllMenuItems(pageNumber, MenuSortOrder.PRICE_ASCENDING);
            String jsonData = new Gson().toJson(menuItems);
            try {
                response.getWriter().write(jsonData);
            } catch (IOException e) {
                logger.error("Error while writing a response body", e);
            }
        } catch (ServiceException e) {
            logger.error("Unable to load menu page command", e);
            response.setStatus(400);
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException ioException) {
                logger.error("Error while writing a response body", ioException);
            }
        }
    }
}
