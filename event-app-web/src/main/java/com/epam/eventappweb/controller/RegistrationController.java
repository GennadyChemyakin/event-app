package com.epam.eventappweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Test controller
 */

@Controller
@RequestMapping
public class RegistrationController {

    @RequestMapping(value = "/register",  method=GET)
    public String getRegistrationPage() {
        return "register";
    }

    @RequestMapping(value = "/",  method=GET)
    public String getFrontPage() {
        return "home";
    }

}
