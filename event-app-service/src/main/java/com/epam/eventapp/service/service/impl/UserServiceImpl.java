package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.EmailAlreadyExistsException;
import com.epam.eventapp.service.exceptions.UserNameAlreadyExistsException;
import com.epam.eventapp.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service implementation
 */
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
            throw new EmailAlreadyExistsException(msg);
        }

        if (userDao.isUserNameRegistered(user.getUsername())) {
            final String msg = String.format("User with username: %s is already in the database.", user.getUsername());
            LOGGER.error(msg);
            throw new UserNameAlreadyExistsException(msg);
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
    public void updateUserPhotoByUsername(String userName, String photo) {
        LOGGER.debug("updateUserPhotoByUsername started: Params username = {}, photo = {}", userName, photo);
        userDao.updateUserPhoto(userName,photo);
        LOGGER.debug("updateUserPhotoByUsername finished");
    }

}
