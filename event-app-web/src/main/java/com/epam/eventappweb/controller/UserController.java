package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Iterator;

import static java.nio.file.Files.isReadable;


@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    private static String DIRECTORY = "\\\\EPRUPETW0518\\images\\users\\";
    private static String DEFAULT_USER_IMAGE_LINK = "\\\\EPRUPETW0518\\images\\users\\default_user.png";

    /**
     * method for getting info about logged user
     *
     * @param principal
     * @return userVO with username of logged user or with empty string if there is not any logged user
     */
    @RequestMapping(path = "/user/current", method = RequestMethod.GET)
    public UserVO getLoggedUserDetails(Principal principal) {
        LOGGER.info("getLoggedUserDetails started. Param: principal = {} ", principal);
        UserVO userVO;
        if (principal == null)
            userVO = UserVO.builder().username("").build();
        else userVO = UserVO.builder().username(principal.getName()).build();
        LOGGER.info("getLoggedUserDetails finished. Result: userVO = {} ", userVO);
        return userVO;
    }

    /**
     * Method for getting user details by his name
     * @param username - name of the user
     * @return UserVO object
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    public UserVO getUserByName(@RequestParam String username) {

        LOGGER.info("getUserByName started. Params userName: {}", username);
        User user = userService.getUserByUsername(username);
        UserVO userVO = UserVO.builder(user.getUsername(),user.getEmail())
                .bio(user.getBio())
                .city(user.getCity())
                .country(user.getCountry())
                .email(user.getEmail())
                .gender(user.getGender())
                .photo(user.getPhoto())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
        LOGGER.info("getUserByName finished. Returns user VO: {}", userVO);

        return userVO;
    }


    /**
     * Method for saving user image into the catalog and in SEC_USER table in db
     *
     * @param userName - username of user the picture belongs to.
     * @param request - Multipart request
     */
    @RequestMapping(value="/images/users/{userName}", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void handleImageUpload(@PathVariable String userName,
                                        MultipartHttpServletRequest request){

        LOGGER.info("handleImageUpload started. Request - {}", request);

        Path path = Paths.get(DIRECTORY).resolve(userName);

        Iterator<String> itr = request.getFileNames();
        String filename = "";
        MultipartFile file = null;
        if(itr.hasNext()) {
            String uploadedFile = itr.next();
            file = request.getFile(uploadedFile);
            filename = file.getOriginalFilename();
        }
LOGGER.info("Filename - " + filename);
        if (file != null && !file.isEmpty()) {

            try {
                if(!isReadable(path)) {
                    Files.createDirectory(path);
                }

                Files.write(path.resolve(userName),file.getBytes());
                userService.updateUserPhotoByUsername(userName, path.toString());
                LOGGER.info("handleImageUpload finished uploading file. Photo path is {}", path.toString());
            } catch (IOException e) {
                LOGGER.info("handleImageUpload finished. Failed to save an image", e);
            }

        } else {
            LOGGER.info("handleImageUpload finished. Failed to upload an image");
        }

    }

    /**
     * Method for getting userImage
     * @param userName
     * @return an image in bytes
     * @throws IOException
     */
    @RequestMapping(value="/images/users/{userName}", method=RequestMethod.GET, produces = "image/png")
    public byte[] getImage(@PathVariable String userName) throws IOException {

        LOGGER.info("getImage started. PathVariables: id - {}", userName);
        Path path = null;
        try {
            String photoLink = userService.getUserByUsername(userName).getPhoto();
            if(photoLink == null) {
                path = Paths.get(DEFAULT_USER_IMAGE_LINK);
            } else {
                path = Paths.get(photoLink).resolve(userName);
            }
            LOGGER.info("getImage finished. Path: {}", path.toString());
            return Files.readAllBytes(path);
        } catch (IOException e) {
            LOGGER.info("getImage finished. Failed to get am image with path: {}", path,  e);
        }

        return new byte[1];
    }

}
