package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.InvalidIdentifierException;
import com.epam.eventapp.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * User service implementation
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDao;

    @Override
    public void createUser(User user) {

        LOGGER.debug("createUser started: Params user = {}", user);

        if (userDao.isEmailRegistered(user.getEmail())) {
            final String msg = String.format("User with email: %s is already in the database.", user.getEmail());
            LOGGER.error(msg);
            throw new InvalidIdentifierException(msg);
        }

        if (userDao.isUserNameRegistered(user.getUsername())) {
            final String msg = String.format("User with username: %s is already in the database.", user.getUsername());
            LOGGER.error(msg);
            throw new InvalidIdentifierException(msg);
        }

        userDao.createUser(user);

        LOGGER.debug("createUser finished: Params user = {}", user);

    }

    @Override
    public User getUserByUsername(String username) {
        LOGGER.debug("getUserByUsername started: Params username = {}", username);
        User user = userDao.getUserByUsername(username);
        LOGGER.debug("getUserByUsername finished: Result user = {}", user);
        return user;
    }

    @Override
    public int updateUserPhotoByUsername(String username, String photoURL) {
        LOGGER.debug("updateUserPhotoByUsername started. Params: username: {}, photoURL: {}", username, photoURL);
        int updatedRow = userDao.updateUserPhotoURL(username,photoURL);
        LOGGER.debug("updateUserPhotoByUsername finished, rows updated: {}", updatedRow);
        return updatedRow;
    }

}
