package com.eugene.cafe.model.dao;

import com.eugene.cafe.entity.Client;
import com.eugene.cafe.exception.DaoException;

import java.util.Optional;

public abstract class ClientDao extends AbstractDao<Client> {

    public abstract Optional<Client> findClientByEmail(String email) throws DaoException;
}
