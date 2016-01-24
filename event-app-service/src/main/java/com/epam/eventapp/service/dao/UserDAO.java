package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.User;

/**
 * DAO for user
 */
public interface UserDAO {

    /**
     * Method for creating user in the database.
     *
     * @param user - user object
     */
    void createUser(User user);

    /**
     * @return returns true if Username is already in the database
     */
    boolean isUserNameRegistered(String username);

    /**
     * @return returns true if email is already in the database
     */
    boolean isEmailRegistered(String email);

}
