package com.epam.eventappweb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration for SpringMVC.
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.eventappweb.controller")
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*.html").addResourceLocations("/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
