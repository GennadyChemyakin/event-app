package com.epam.eventappweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Test controller
 */

@Controller
@RequestMapping
public class LoginController {

    @RequestMapping(value="/login", method=GET)
    public String getLoginPage() {
        return "login";
    }
}
