package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.DaoException;

import java.util.List;

public abstract class MenuItemDao extends AbstractDao<MenuItem> {

    public abstract List<MenuItem> getSubsetOfMenuItems(int limit, int offset, MenuItemSortOrder sortOrder) throws DaoException;

    public abstract List<MenuItem> getSubsetOfMenuItemsByCategory(int limit, int offset, MenuItemSortOrder sortOrder, Category category) throws DaoException;

    public abstract int getCount() throws DaoException;

    public abstract int getCountByCategory(Category category) throws DaoException;

}
