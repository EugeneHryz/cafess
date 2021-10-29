package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.Category;
import com.eugene.cafe.entity.MenuItem;
import com.eugene.cafe.exception.ServiceException;
import com.eugene.cafe.model.dao.MenuSortOrder;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    boolean addMenuItem(String name, String price, String categoryId, String description, String imagePath) throws ServiceException;

    Optional<MenuItem> findMenuItemById(int id) throws ServiceException;

    Optional<Category> findCategoryById(int id) throws ServiceException;

    List<MenuItem> getSubsetOfActiveMenuItems(int pageNumber, MenuSortOrder sortOrder, Category category) throws ServiceException;

    List<MenuItem> getSubsetOfAllMenuItems(int pageNumber, MenuSortOrder sortOrder) throws ServiceException;

    int getMenuItemCountByCategory(Category category, boolean active) throws ServiceException;

    List<Category> getAllMenuCategories() throws ServiceException;

    Optional<MenuItem> changeMenuItemStatus(int id, boolean status) throws ServiceException;
}
