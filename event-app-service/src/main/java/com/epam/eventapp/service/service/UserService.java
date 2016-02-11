package com.epam.eventapp.service.service;

import com.epam.eventapp.service.domain.User;

import java.util.Optional;

/**
 * User service
 */
public interface UserService {

    /**
     * Method for saving new user into database
     * @param user - user to save into db
     */
    void createUser(User user);

    /**
     * Method for saving new user into database
     * @param username String value of the user name
     * @return  returns Optional<User> - user to save into db
     */
    Optional<User> getUserByUserName(String username);

}
