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
    int createUser(User user);

}
