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


/**
 * Controller which handles requests concerning user details
 */
@RestController
public class UserDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
    public UserVO getUserByName(@RequestParam String name) {

        LOGGER.debug("getUserByName started. Params userName: {}", name);

        User user = userService.getUserByUserName(name).get();
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

        LOGGER.debug("getUserByName finished. Returns user VO: {}", userVO);

        return userVO;
    }

}
