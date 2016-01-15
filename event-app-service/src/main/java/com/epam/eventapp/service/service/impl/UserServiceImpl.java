package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * UserService implementation
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public Optional<User> findByUsername(String username) {
        LOGGER.debug("findByUsername started: Params username = {}", username);
        Optional<User> user = userDAO.findByUsername(username);
        LOGGER.debug("findByUsername finished. Result: {}", user);
        return user;
    }
}
