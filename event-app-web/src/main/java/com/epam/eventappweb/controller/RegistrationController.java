package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * controller whick handles user creation process
 * @return returns ResponseEntity object with status 201 (created)
 */
@RestController
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    /**
     * Method for registering new user in the db. Returns status 204 on success.
     * @param userVO user model
     * @param request
     * @throws ServletException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes="application/json")
    public void createUser(@RequestBody UserVO userVO, HttpServletRequest request) throws ServletException {
        LOGGER.info("createUser started. Param: userVO = {};", userVO);
        User user = User.builder(userVO.getUsername(), userVO.getEmail())
                .password(new Md5PasswordEncoder().encodePassword(userVO.getPassword(),""))
                .name(userVO.getName())
                .surname(userVO.getSurname())
                .gender(userVO.getGender())
                .photo(userVO.getPhoto())
                .city(userVO.getCity())
                .country(userVO.getCountry())
                .bio(userVO.getBio())
                .build();

        userService.createUser(user);

        request.login(userVO.getUsername(), userVO.getPassword());
        LOGGER.info("createUser finished.");

    }

}
