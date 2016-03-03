package com.epam.eventapp.service.service;

/**
 * Service for saving images to the folder
 */
public interface ImageService {

    /**
     * Method for saving user photo on file disk
     * @param username name of the user
     * @param photo bytes array of the photo
     */
    String saveUserImage(String username, byte[] photo);

    /**
     * Method for getting user photo from disk
     * @param username name of the user
     * @param photolink link to the user folder
     * @return bytes of the photo
     */
    byte[] getUserPhoto(String photolink, String username);

}
