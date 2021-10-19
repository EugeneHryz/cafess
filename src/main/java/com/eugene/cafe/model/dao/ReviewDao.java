package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.Review;
import com.eugene.cafe.exception.DaoException;

import java.util.Optional;

public abstract class ReviewDao extends AbstractDao<Review> {

    public abstract Optional<Review> findOrderReview(int orderId) throws DaoException;
}
