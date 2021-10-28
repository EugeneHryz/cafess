package com.eugene.cafe.model.service.impl;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.DaoException;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.CategoryDao;
import com.eugene.cafe.model.dao.MenuItemDao;
import com.eugene.cafe.model.dao.MenuItemSortOrder;
import com.eugene.cafe.model.dao.TransactionHelper;
import com.eugene.cafe.model.dao.impl.CategoryDaoImpl;
import com.eugene.cafe.model.dao.impl.MenuItemDaoImpl;
import com.eugene.cafe.model.service.MenuService;
import com.eugene.cafe.model.validator.ParamValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MenuServiceImpl implements MenuService {

    private static final Logger logger = LogManager.getLogger(MenuServiceImpl.class);

    public static final int MENU_ITEMS_PER_PAGE = 8;

    @Override
    public boolean addMenuItem(String name, String price, String categoryId, String description, String imagePath) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final MenuItemDao menuItemDao = new MenuItemDaoImpl();

        boolean itemAdded = false;
        helper.init(menuItemDao);
        try {
            if (ParamValidator.validateMenuItem(name, price, description)) {

                MenuItem.Builder builder = new MenuItem.Builder();
                builder.setName(name)
                        .setPrice(Double.parseDouble(price))
                        .setCategoryId(Integer.parseInt(categoryId))
                        .setDescription(description)
                        .setImagePath(imagePath);

                itemAdded = menuItemDao.create(builder.buildMenuItem());
            }
        } catch (DaoException e) {
            logger.error("Unable to add new menu item", e);
            throw new ServiceException("Unable to add new menu item", e);
        } finally {
            helper.end();
        }
        return itemAdded;
    }

    @Override
    public Optional<MenuItem> findMenuItemById(int id) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final MenuItemDao menuItemDao = new MenuItemDaoImpl();

        helper.init(menuItemDao);
        Optional<MenuItem> menuItem;
        try {
            menuItem = menuItemDao.findById(id);
        } catch (DaoException e) {
            logger.error("Unable to find menu item by id", e);
            throw new ServiceException("Unable to find menu item by id", e);
        } finally {
            helper.end();
        }
        return menuItem;
    }

    @Override
    public Optional<Category> findCategoryById(int id) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final CategoryDao categoryDao = new CategoryDaoImpl();

        helper.init(categoryDao);
        Optional<Category> category;
        try {
            category = categoryDao.findById(id);
        } catch (DaoException e) {
            logger.error("Unable to find category by id", e);
            throw new ServiceException("Unable to find category by id", e);
        } finally {
            helper.end();
        }
        return category;
    }

    @Override
    public List<MenuItem> getSubsetOfMenuItems(int pageNumber, MenuItemSortOrder sortOrder, Category category) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final MenuItemDao menuItemDao = new MenuItemDaoImpl();

        helper.init(menuItemDao);
        List<MenuItem> menuItems;
        int offset = MENU_ITEMS_PER_PAGE * (pageNumber - 1);
        try {
            if (category == null) {
                menuItems = menuItemDao.getSubsetOfMenuItems(MENU_ITEMS_PER_PAGE, offset, sortOrder);
            } else {
                menuItems = menuItemDao.getSubsetOfMenuItemsByCategory(MENU_ITEMS_PER_PAGE, offset, sortOrder, category);
            }
        } catch (DaoException e) {
            logger.error("Unable to get a subset of menu items", e);
            throw new ServiceException("Unable to get a subset of menu items", e);
        } finally {
            helper.end();
        }
        return menuItems;
    }

    @Override
    public int getMenuItemCountByCategory(Category category) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final MenuItemDao menuItemDao = new MenuItemDaoImpl();

        helper.init(menuItemDao);
        int count;
        try {
            if (category == null) {
                count = menuItemDao.getCount();
            } else {
                count = menuItemDao.getCountByCategory(category);
            }
        } catch (DaoException e) {
            logger.error("Unable to get menu item count", e);
            throw new ServiceException("Unable to get menu item count", e);
        } finally {
            helper.end();
        }
        return count;
    }

    @Override
    public List<Category> getAllMenuCategories() throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final CategoryDao categoryDao = new CategoryDaoImpl();

        helper.init(categoryDao);
        List<Category> categories;
        try {
            categories = categoryDao.findAll();
        } catch (DaoException e) {
            logger.error("Unable to get all menu categories", e);
            throw new ServiceException("Unable to get all menu categories", e);
        } finally {
            helper.end();
        }
        return categories;
    }

    @Override
    public boolean deleteMenuItem(int itemId) throws ServiceException {

        final TransactionHelper helper = new TransactionHelper();
        final MenuItemDao menuItemDao = new MenuItemDaoImpl();

        helper.init(menuItemDao);
        boolean itemDeleted;
        try {
            itemDeleted = menuItemDao.deleteById(itemId);
        } catch (DaoException e) {
            logger.error("Failed to delete menu item with id: " + itemId, e);
            throw new ServiceException("Failed to delete menu item with id: " + itemId, e);
        } finally {
            helper.end();
        }
        return itemDeleted;
    }
}
