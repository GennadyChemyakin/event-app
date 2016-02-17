package com.epam.eventapp.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Service config
 */
@Configuration
@ComponentScan("com.epam.eventapp.service.service.impl")
@Import(value = {DataAccessConfig.class, MethodSecurityConfig.class})
public class ServiceConfig {
}
