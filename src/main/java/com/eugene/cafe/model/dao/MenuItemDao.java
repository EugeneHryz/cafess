package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.DaoException;

import java.util.List;

public abstract class MenuItemDao extends AbstractDao<MenuItem> {

    public abstract List<MenuItem> getSubsetOfMenuItems(int limit, int offset, MenuSortOrder sortOrder, boolean active) throws DaoException;

    public abstract List<MenuItem> getSubsetOfActiveMenuItemsByCategory(int limit, int offset, MenuSortOrder sortOrder, Category category) throws DaoException;

    public abstract int getCount(boolean active) throws DaoException;

    public abstract int getCountByCategory(Category category, boolean active) throws DaoException;

}
