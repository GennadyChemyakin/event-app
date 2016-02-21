package com.epam.eventappweb.controller;

import com.epam.eventappweb.model.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.Iterator;


@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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


    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public HttpStatus handleImageUpload(MultipartHttpServletRequest request){

        LOGGER.info("handleImageUpload started.");

        Iterator<String> itr = request.getFileNames();
        String filename = "";
        MultipartFile file = null;
        if(itr.hasNext()) {
            String uploadedFile = itr.next();
            file = request.getFile(uploadedFile);
            filename = file.getOriginalFilename();
        }

        if (file != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                   BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(filename)));
                stream.write(bytes);
                stream.close();
                LOGGER.info("handleImageUpload finished uploading file with name- {}",filename);
                return HttpStatus.CREATED;
            } catch (Exception e) {
                LOGGER.info("handleImageUpload finished. Failed to read an image");
                return HttpStatus.NOT_ACCEPTABLE;
            }
        } else {
            LOGGER.info("handleImageUpload finished. Failed to upload an image");
            return HttpStatus.NO_CONTENT;
        }

    }


}
