package com.eugene.cafe.dao.impl;

import com.eugene.cafe.dao.AbstractDao;
import com.eugene.cafe.entity.Review;
import com.eugene.cafe.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eugene.cafe.dao.DatabaseColumnLabels.*;

public class ReviewDao extends AbstractDao<Review> {

    private static final Logger logger = LogManager.getLogger(ReviewDao.class);

    private static final String SQL_CREATE_REVIEW = "INSERT INTO reviews(rating, comment, date) " +
            "VALUES(?, ?, ?)";

    private static final String SQL_FIND_REVIEW_BY_ID = "SELECT id, rating, comment, date FROM " +
            "reviews WHERE reviews.id = ?";

    private static final String SQL_FIND_ALL_REVIEWS = "SELECT id, rating, comment, date FROM reviews";

    private static final String SQL_UPDATE_REVIEW = "UPDATE reviews SET rating = ?, comment = ?, date = ? " +
            "WHERE reviews.id = ?";

    private static final String SQL_DELETE_REVIEW_BY_ID = "DELETE FROM reviews WHERE reviews.id = ?";

    @Override
    public boolean create(Review entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ReviewDao");
            throw new DaoException("Database connection is not set for ReviewDao");
        }

        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_REVIEW)) {
            statement.setInt(1, entity.getRating());
            statement.setString(2, entity.getComment());
            statement.setTimestamp(3, entity.getDate());

            if (statement.executeUpdate() > 0) {
                created = true;
            }

        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return created;
    }

    @Override
    public Optional<Review> findById(int id) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ReviewDao");
            throw new DaoException("Database connection is not set for ReviewDao");
        }

        Optional<Review> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_REVIEW_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Review review = buildReview(resultSet);
                result = Optional.of(review);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return result;
    }

    @Override
    public List<Review> findAll() throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ReviewDao");
            throw new DaoException("Database connection is not set for ReviewDao");
        }

        List<Review> reviews = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_REVIEWS);

            while (resultSet.next()) {
                Review review = buildReview(resultSet);
                reviews.add(review);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return reviews;
    }

    @Override
    public Optional<Review> update(Review entity) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ReviewDao");
            throw new DaoException("Database connection is not set for ReviewDao");
        }

        Optional<Review> updated = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_REVIEW)) {
            statement.setShort(1, entity.getRating());
            statement.setString(2, entity.getComment());
            statement.setTimestamp(3, entity.getDate());
            statement.setInt(4, entity.getId());

            if (statement.executeUpdate() > 0) {
                updated = Optional.of(entity);
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return updated;
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        if (connection == null) {
            logger.error("Database connection is not set for ReviewDao");
            throw new DaoException("Database connection is not set for ReviewDao");
        }

        boolean deleted = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_REVIEW_BY_ID)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            logger.error("Database error occurred " + e);
            throw new DaoException("Database error occurred", e);
        }
        return deleted;
    }

    private Review buildReview(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt(REVIEWS_ID);
        short rating = resultSet.getShort(REVIEWS_RATING);
        String comment = resultSet.getString(REVIEWS_COMMENT);
        Timestamp date = resultSet.getTimestamp(REVIEWS_DATE);

        Review review = new Review(rating, comment, date);
        review.setId(id);
        return review;
    }
}
