package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.UserView;
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

@RestController
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<?>  createUser(@RequestBody UserView userView) {

        User user = User.builder(userView.getUsername(), userView.getEmail())
                .password(new Md5PasswordEncoder().encodePassword(userView.getPassword(),""))
                .name(userView.getName())
                .surname(userView.getSurname())
                .gender(userView.getGender())
                .photo(userView.getPhoto())
                .city(userView.getCity())
                .country(userView.getCountry())
                .bio(userView.getBio())
                .build();

        userService.createUser(user);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
