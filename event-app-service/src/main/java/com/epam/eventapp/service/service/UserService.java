package com.epam.eventapp.service.service;

import com.epam.eventapp.service.domain.User;


public interface UserService {

    /**
     * Method for saving new user into database
     * @param user - user to save into db
     */
    int createUser(User user);
}
