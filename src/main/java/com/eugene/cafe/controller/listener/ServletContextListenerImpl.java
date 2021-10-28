package com.eugene.cafe.controller.listener;

import static com.eugene.cafe.controller.command.AttributeName.*;
import com.eugene.cafe.entity.Category;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.pool.ConnectionPool;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.service.impl.MenuServiceImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ServletContextListenerImpl.class);

    private final MenuService menuService = new MenuServiceImpl();

    private List<Category> menuCategories;
    {
        try {
            menuCategories = menuService.getAllMenuCategories();
        } catch (ServiceException e) {
            logger.error("Failed to get all menu categories", e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();

        sce.getServletContext().setAttribute(MENU_CATEGORIES_LIST, menuCategories);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
