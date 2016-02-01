package com.epam.eventapp.service.service;

import com.epam.eventapp.service.domain.User;

/**
 * User service
 */
public interface UserService {

    /**
     * Method for saving new user into database
     * @param user - user to save into db
     */
    void createUser(User user);

}
