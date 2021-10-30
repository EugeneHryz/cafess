package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuSortOrder;

import java.util.List;
import java.util.Optional;

/**
 * menu service interface
 */
public interface MenuService {

    /**
     * add menu item
     *
     * @param name menu item name
     * @param price menu item price
     * @param categoryId menu item category id
     * @param description menu item description
     * @param imagePath relative path of the menu item image
     * @return true, if successful
     * @throws ServiceException if error
     */
    boolean addMenuItem(String name, String price, String categoryId, String description, String imagePath) throws ServiceException;

    /**
     * find menu item by id
     *
     * @param id menu item id
     * @return Optional MenuItem
     * @throws ServiceException if error
     */
    Optional<MenuItem> findMenuItemById(int id) throws ServiceException;

    /**
     * find category by id
     *
     * @param id category id
     * @return Optional Category
     * @throws ServiceException if error
     */
    Optional<Category> findCategoryById(int id) throws ServiceException;

    /**
     * get subset of active menu items by page
     *
     * @param pageNumber page number (1 - first page)
     * @param sortOrder sort order
     * @param category menu items category
     * @return list of menu items
     * @throws ServiceException if error
     */
    List<MenuItem> getSubsetOfActiveMenuItems(int pageNumber, MenuSortOrder sortOrder, Category category) throws ServiceException;

    /**
     * get subset of all menu items by page
     *
     * @param pageNumber page number
     * @param sortOrder sort order
     * @return list of menu items
     * @throws ServiceException if error
     */
    List<MenuItem> getSubsetOfAllMenuItems(int pageNumber, MenuSortOrder sortOrder) throws ServiceException;

    /**
     * get number of menu items by category
     *
     * @param category menu items category
     * @param active if false, all items will be counted; true - only active ones
     * @return number of menu items
     * @throws ServiceException if error
     */
    int getMenuItemCountByCategory(Category category, boolean active) throws ServiceException;

    /**
     * get all menu categories
     *
     * @return list of categories
     * @throws ServiceException if error
     */
    List<Category> getAllMenuCategories() throws ServiceException;

    /**
     * change menu item status (either archive or unarchive it)
     *
     * @param id menu item id
     * @param status new status
     * @return Optional MenuItem
     * @throws ServiceException if error
     */
    Optional<MenuItem> changeMenuItemStatus(int id, boolean status) throws ServiceException;
}
