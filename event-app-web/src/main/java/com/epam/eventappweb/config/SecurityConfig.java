package com.epam.eventappweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Configuration for Spring Security. Extends WebSecurityConfigurerAdapter to override method for configuration
 * user store and access to variety of URLs.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String USERS_BY_USER_NAME = "select username, password, 1 from sec_user where username = ?";

    private final String AUTHORITIES_BY_USERNAME = "select username, authority from sec_user " +
            "join sec_user_authority on sec_user.id = sec_user_authority.sec_user_id " +
            "join authority on sec_user_authority.authority_id = authority.id " +
            "where sec_user.username = ?";


    //dataSource bean has to be described in spring db config class
    @Autowired
    private DataSource dataSource;

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
                .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME);
                //.passwordEncoder(new Md5PasswordEncoder());
    }

    /**
     * Method for configuring access to variety of URLs.
     * Right now it redirects to default spring login page from any url
     * in future we will need to add URLs that needed to be available by authorized user
     * and add custom log page
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .anyRequest().authenticated();

    }
}
