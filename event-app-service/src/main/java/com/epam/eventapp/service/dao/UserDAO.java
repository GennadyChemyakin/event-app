package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.User;

import java.util.Optional;

/**
 * DAO for User
 */
public interface UserDAO {
    /**
     * Method for getting User by username.
     *
     * @param username - username of needed User
     * @return Optional.of(user) if we've found event by username, otherwise Optional.empty()
     */
    Optional<User> findByUsername(String username);
}
