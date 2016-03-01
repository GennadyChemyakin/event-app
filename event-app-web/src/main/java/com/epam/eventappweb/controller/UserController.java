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

import java.security.Principal;


@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
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

}
