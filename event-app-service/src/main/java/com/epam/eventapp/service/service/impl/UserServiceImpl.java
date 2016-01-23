package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.EmailExistsInTheDatabase;
import com.epam.eventapp.service.exceptions.UserNameExistsInTheDatabase;
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
    public void createUser(User user) {
        LOGGER.debug("createUser started: Params user = {}", user);

        if (userDao.isEmailRegistered(user.getEmail())) {
            LOGGER.debug("User with email: " + user.getEmail() + " is already in database", user);
            throw new EmailExistsInTheDatabase();
        }

        if (userDao.isUserNameRegistered(user.getUsername())) {
            LOGGER.debug("User with username: " + user.getUsername() + " is already in database", user);
            throw new UserNameExistsInTheDatabase();
        }

        userDao.createUser(user);
        LOGGER.debug("createUser finished: Params user = {}", user);
    }
}
