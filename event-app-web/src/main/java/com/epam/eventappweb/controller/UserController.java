package com.epam.eventappweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 *
 */
@RestController
public class UserController {

    @RequestMapping(path = "/user/current", method = RequestMethod.GET)
    public String getLoggedUserDetails(Principal principal) {
        if (principal == null)
            return null;
        else return principal.getName();
    }
}
