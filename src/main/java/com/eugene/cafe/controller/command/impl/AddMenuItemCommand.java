package com.eugene.cafe.controller.command.impl;

import com.eugene.cafe.controller.command.Command;

import static com.eugene.cafe.controller.command.RequestAttribute.*;
import static com.eugene.cafe.controller.command.RequestParameter.*;

import static com.eugene.cafe.controller.command.PagePath.*;
import com.eugene.cafe.controller.command.Router;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.entity.User;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.manager.ResourceManager;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class AddMenuItemCommand implements Command {

    private static final MenuService menuService = new MenuServiceImpl();

    private static final String UPLOAD_LOCATION = "upload.location";
    private static final String MENU_ITEMS_DIR = "menu_items";

    @Override
    public Router execute(HttpServletRequest request) {

        String menuItemName = request.getParameter(PARAM_MENU_ITEM_NAME);
        String menuItemPrice = request.getParameter(PARAM_PRICE);
        String categoryId = request.getParameter(PARAM_CATEGORY_ID);
        System.out.println("Category id: " + categoryId);
        String menuItemDescription = request.getParameter(PARAM_DESCRIPTION);

        String uploadDir = request.getServletContext().getInitParameter(UPLOAD_LOCATION)
                + File.separator + MENU_ITEMS_DIR;
        File file = new File(uploadDir);
        if (!file.exists()) {
            file.mkdir();
        }

        Router router = new Router(ADMIN_DASHBOARD, Router.RouterType.FORWARD);
        try {
            String submittedFileName = request.getPart(PARAM_FILE).getSubmittedFileName();
            if (submittedFileName == null || submittedFileName.isEmpty()) {
                return router;
            }

            for (Part part : request.getParts()) {
                if (part.getSubmittedFileName() != null &&
                        part.getSubmittedFileName().equals(submittedFileName)) {

                    part.write(uploadDir + File.separator + submittedFileName);
                }
            }

            MenuItem.Builder builder = new MenuItem.Builder();
            builder.setName(menuItemName)
                    .setDescription(menuItemDescription)
                    .setPrice(Double.parseDouble(menuItemPrice))
                    .setCategoryId(Integer.parseInt(categoryId))
                    .setImagePath(submittedFileName);

            Locale locale = Locale.forLanguageTag((String) request.getSession(false).getAttribute(LOCALE));
            ResourceManager manager = new ResourceManager("message", locale);

            if (menuService.addMenuItem(builder.buildMenuItem())) {
                request.setAttribute(MENU_ITEM_ADDED, manager.getProperty(MENU_ITEM_ADDED));
            } else {
                request.setAttribute(MENU_ITEM_NOT_ADDED, manager.getProperty(MENU_ITEM_NOT_ADDED));
            }
        } catch (IOException | ServletException | ServiceException e) {
            // todo: write log
            request.getSession().setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
