package com.epam.eventappweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Configuration for SpringMVC.
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.eventappweb")
public class WebAppConfig {

}
