package com.eugene.cafe.model.service;

import com.eugene.cafe.entity.Client;
import com.eugene.cafe.exception.ServiceException;

import java.util.Optional;

public interface ClientService {

    Optional<Client> signIn(String email, String password) throws ServiceException;

    Optional<Client> signUp(String name, String surname, String email, String password) throws ServiceException;
}
