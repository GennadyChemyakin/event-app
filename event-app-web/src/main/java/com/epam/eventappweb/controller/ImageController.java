package com.epam.eventappweb.controller;

import com.epam.eventapp.service.service.ImageService;
import com.epam.eventapp.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static java.nio.file.Files.isReadable;

/**
 * Class for handling request concerning image transfer
 */
@RestController
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    /**
     * Method for saving user image into the catalog and in SEC_USER table in db
     *
     * @param userName      - username of user the picture belongs to.
     * @param multipartFile - Multipart file to load
     */
    @RequestMapping(value = "/image/user/{userName}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveUserImage(@PathVariable String userName,
                              @RequestParam("users_photo") MultipartFile multipartFile) throws IOException {

        LOGGER.info("saveUserImage started. Params: username: {}, multipartFile: {}", userName, multipartFile);

        String photoLink = imageService.saveUserImage(userName, multipartFile.getBytes());
        userService.updateUserPhotoByUsername(userName, photoLink);

        LOGGER.info("saveUserImage finished.");
    }

    /**
     * Method for getting userImage
     *
     * @param username - name of the user
     * @return an image in bytes
     */
    @RequestMapping(value = "/image/user/{username}", method = RequestMethod.GET, produces = {"image/png", "image/jpg", "image/jpeg"})
    public byte[] getUserImage(@PathVariable String username) {

        LOGGER.info("getUserImage started. Params: username: {}", username);

        String photoLink = userService.getUserByUsername(username).getPhoto().orElse(null);
        byte[] photo = imageService.getUserPhoto(photoLink, username);

        LOGGER.info("getUserImage finished. Returns bytes: {}", photo.length);
        return photo;

    }

}
