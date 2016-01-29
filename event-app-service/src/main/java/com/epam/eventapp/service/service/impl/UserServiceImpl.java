package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User service implementation
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDao;

    @Override
    public int createUser(User user) {

        LOGGER.debug("Start saving user into database: Params user = {}", user);
        int rows = userDao.createUser(user);
        LOGGER.debug("Has saved user into database: Params user = {}", user);

        return rows;
    }

    @Override
    public User getUserByUsername(String username) {
        LOGGER.debug("getUserByUsername started: Params username = {}", username);
        User user = userDao.getUserByUsername(username);
        LOGGER.debug("getUserByUsername finished: Result user = {}", user);
        return user;
    }

}
