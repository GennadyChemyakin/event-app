package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.exceptions.ImageNotReadFromDiskException;
import com.epam.eventapp.service.exceptions.ImageNotSavedToDiskException;
import com.epam.eventapp.service.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of ImageService
 */
@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    Environment environment;

    @Override
    public String saveUserImage(String username, byte[] photo) {
        LOGGER.info("saveUserImage started. Params: username - {}, photo {} bytes", username, photo.length);
        try {
            Path path = Paths.get(environment.getRequiredProperty("USER_IMAGE_PATH")).resolve(username);
            if(!Files.isReadable(path)) {
                Files.createDirectory(path);
            }
            Files.write(path.resolve(username), photo);
            LOGGER.info("saveUserImage finished. Returns: photoLink - {}", path.getParent());
            return path.toString();
        } catch (IOException e) {
            throw new ImageNotSavedToDiskException(e.getMessage());
        }
    }

    @Override
    public byte[] getUserPhoto(String photoLink,  String username) {

        LOGGER.info("getUserPhoto started. Params: photoLink - {}, username {}", photoLink, username);

        Path path;
        if (photoLink == null) {
            path = Paths.get(environment.getRequiredProperty("DEFAULT_USER_IMAGE_PATH"));
        } else {
            path = Paths.get(photoLink).resolve(username);
        }

        try {
            byte[] photo = Files.readAllBytes(path);
            LOGGER.info("getUserPhoto finished. Returns: photo data size - {} bytes", photo.length);
            return photo;
        } catch (IOException e) {
            throw new ImageNotReadFromDiskException(e.getMessage());
        }

    }

}
