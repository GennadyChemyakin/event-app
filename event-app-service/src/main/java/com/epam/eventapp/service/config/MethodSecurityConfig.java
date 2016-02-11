package com.epam.eventapp.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import javax.sql.DataSource;

/**
 * configuration for method security
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private DataSource dataSource;


    private final static String USERS_BY_USER_NAME = "select username, password, 1 from sec_user where username = ?";

    private final static String AUTHORITIES_BY_USERNAME = "select username, authority from sec_user " +
            "join sec_user_authority on sec_user.id = sec_user_authority.sec_user_id " +
            "join authority on sec_user_authority.authority_id = authority.id " +
            "where sec_user.username = ?";

    /**
     * Method for configuring data store options.
     * Configure Spring Security to authenticate against a JDBC user store.
     * use MD5 encoding
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(USERS_BY_USER_NAME)
                .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME)
                .passwordEncoder(new Md5PasswordEncoder());
    }
}
