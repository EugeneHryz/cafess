package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.DaoException;

import java.util.List;

/**
 * MenuItemDao abstract class
 */
public abstract class MenuItemDao extends AbstractDao<MenuItem> {

    /**
     * get list of menu items with specified limit, offset and sort order
     *
     * @param limit limit of menu items in the subset
     * @param offset offset from the beginning of the result set
     * @param sortOrder menu items sort order
     * @param active if true, only active items (status = 0) will be returned; false - all items (active + archived)
     * @return list of menu items
     * @throws DaoException if error
     */
    public abstract List<MenuItem> getSubsetOfMenuItems(int limit, int offset, MenuSortOrder sortOrder, boolean active) throws DaoException;

    /**
     * get list of menu items with specified limit, offset, sort order and category
     *
     * @param limit limit of menu items in the subset
     * @param offset offset from the beginning of the result set
     * @param sortOrder menu items sort order
     * @param category category of menu items
     * @return list of menu items
     * @throws DaoException if error
     */
    public abstract List<MenuItem> getSubsetOfActiveMenuItemsByCategory(int limit, int offset, MenuSortOrder sortOrder, Category category) throws DaoException;

    /**
     * count number of menu items
     *
     * @param active if false, all menu items will be counted; true - only active ones
     * @return number of menu items
     * @throws DaoException if error
     */
    public abstract int getCount(boolean active) throws DaoException;

    /**
     * count number of menu items by category
     *
     * @param category category of menu items
     * @param active if false, all menu items will be counted; true - only active ones
     * @return number of menu items
     * @throws DaoException if error
     */
    public abstract int getCountByCategory(Category category, boolean active) throws DaoException;
}
