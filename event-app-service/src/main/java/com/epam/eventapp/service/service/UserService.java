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

    /**
     * method for getting user by username
     * @param username
     * @return user object if we found user by username
     */
    User getUserByUsername(String username);

    /**
     * Method for updating user photo
     * @param userName identifier of user
     * @param photo photo link
     * @return numbers of updated rows
     */
    int updateUserPhotoByUsername(String userName, String photo);

}
