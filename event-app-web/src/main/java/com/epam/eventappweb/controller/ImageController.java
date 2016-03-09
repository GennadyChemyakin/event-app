package com.epam.eventappweb.controller;

import com.epam.eventapp.service.service.ImageService;
import com.epam.eventapp.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
     * @param userName - username of user the picture belongs to.
     * @param multipartFile  - Multipart file to load
     */
    @RequestMapping(value = "/image/user/{userName}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleImageUpload(@PathVariable String userName,
                                  @RequestParam("users_photo") MultipartFile multipartFile) throws IOException {

        LOGGER.info("handleImageUpload started. Params: username - {}, Mfile - {}", userName, multipartFile);

        String photoLink = imageService.saveUserImage(userName, multipartFile.getBytes());
        userService.updateUserPhotoByUsername(userName, photoLink);

        LOGGER.info("handleImageUpload finished.");
    }

    /**
     * Method for getting userImage
     * @param userName
     * @return an image in bytes
     */
    @RequestMapping(value = "/image/user/{userName}", method = RequestMethod.GET, produces = "image/png")
    public byte[] getImage(@PathVariable String userName) {

        LOGGER.info("getImage started. PathVariables: id - {}", userName);

        String photoLink = userService.getUserByUsername(userName).getPhoto();
        byte[] photo     = imageService.getUserPhoto(photoLink,userName);

        LOGGER.info("getImage finished. Return photo data size: {} bytes", photo.length);
        return photo;

    }

}
