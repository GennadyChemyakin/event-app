package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDao;

    @Override
    public int createUser(User user) {

        if (userDao.isEmailRegistered(user.getEmail())) {
            LOGGER.info("User with email: " + user.getEmail() + " is already in database", user);
            return 0;
        }

        if (userDao.isUserNameRegistered(user.getUsername())) {
            LOGGER.info("User with username: " + user.getUsername() + " is already in database", user);
            return 0;
        }

        LOGGER.debug("Start saving user into database: Params user = {}", user);
        int rows = userDao.createUser(user);
        LOGGER.debug("Has saved user into database: Params user = {}", user);

        return rows;
    }
}
