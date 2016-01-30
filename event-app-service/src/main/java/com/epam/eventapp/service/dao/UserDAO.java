package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.User;
import org.springframework.stereotype.Repository;

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

    /**
     * method for getting user by username
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}
