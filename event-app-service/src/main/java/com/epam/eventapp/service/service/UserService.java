package com.epam.eventapp.service.service;

import com.epam.eventapp.service.domain.User;

import java.util.Optional;

/**
 * User service
 */
public interface UserService {
    /**
     * Method for getting User by username.
     *
     * @param username - username of needed User
     * @return Optional.of(user) if we've found user by username, otherwise Optional.empty()
     */
    Optional<User> findByUsername(String username);
}
