package com.epam.eventappweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test controller
 */

@RestController
public class WelcomeController {

    @RequestMapping("/Welcome")
    public String message() {
        String msg = new String("Welcome");
        return msg;
    }

    @RequestMapping("/hello/{name}")
    public String sayHelloAgain(@PathVariable String name) {
        String msg = new String("Hello " + name);
        return msg;
    }
}
