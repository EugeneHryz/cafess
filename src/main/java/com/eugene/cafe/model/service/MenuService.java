package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuItemSortOrder;

import java.util.List;

public interface MenuService {

    boolean addMenuItem(MenuItem newMenuItem) throws ServiceException;

    List<MenuItem> getSubsetOfMenuItems(int pageNumber, MenuItemSortOrder sortOrder, Category category) throws ServiceException;

    int getMenuItemCountByCategory(Category category) throws ServiceException;

    List<Category> getAllMenuCategories() throws ServiceException;
}