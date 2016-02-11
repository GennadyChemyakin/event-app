package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.User;

import java.util.Optional;

/**
 * interface for user DAO
 * methods for checking that fields username and email are unique in db
 * method for creating new user
 */
public interface UserDAO {

    /**
     * Method for creating user in the database.
     *
     * @param user - user object
     */
    void createUser(User user);

    /**
     * Method for checking that UserName is in database.
     * @Param username - string with username
     * @return returns true if Username is already in the database
     */
    boolean isUserNameRegistered(String username);

    /**
     * Method for checking that Email is in database.
     * @Param email - string with user email
     * @return returns true if email is already in the database
     */
    boolean isEmailRegistered(String email);

    /**
     * Method for getting user details by his username.
     * @Param username - string with user user name
     * @return returns optional of user object
     */
    Optional<User> getUserByUserName(String userName);
}
