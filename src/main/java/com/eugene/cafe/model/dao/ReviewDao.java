package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.Review;
import com.eugene.cafe.exception.DaoException;

import java.util.Optional;

/**
 * ReviewDao abstract class
 */
public abstract class ReviewDao extends AbstractDao<Review> {

    /**
     * find review for specified order
     *
     * @param orderId order id
     * @return optional Review
     * @throws DaoException if error
     */
    public abstract Optional<Review> findOrderReview(int orderId) throws DaoException;
}
