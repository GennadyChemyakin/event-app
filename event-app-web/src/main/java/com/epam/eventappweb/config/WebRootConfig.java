package com.epam.eventappweb.config;

import com.epam.eventapp.service.config.MethodSecurityConfig;
import com.epam.eventapp.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Root Config class
 */
@Configuration
@Import(value = {SecurityConfig.class, ServiceConfig.class, MethodSecurityConfig.class})
public class WebRootConfig {
}
