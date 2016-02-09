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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * controller whick handles user creation process
 * @return retuns ResponseEntity object with status 201 (created)
 */
@RestController
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<?>  createUser(@RequestBody UserVO userVO, HttpServletRequest request) throws ServletException {

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

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
