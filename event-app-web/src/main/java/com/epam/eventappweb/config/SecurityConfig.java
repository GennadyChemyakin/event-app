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
 * Created by gennady on 28.11.15.
 * Configuration for Spring Security. Extends WebSecurityConfigurerAdapter to override method for configuration
 * user store and access to variety of URLs.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //dataSource bean has to be described in spring db config class
    @Autowired
    private DataSource dataSource;

    /**
     * Method for configuring data store options.
     * Configure Spring Security to authenticate against a JDBC user store.
     * use MD5 encoding
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username, password, enabled " +
                "from sec_user where username = ?").authoritiesByUsernameQuery("select username, authority" +
                "from sec_user join authority on sec_user.id = authority.sec_user_id where sec_user.username = ?")
                .passwordEncoder(new Md5PasswordEncoder());
    }

    /**
     * Method for configuring access to variety of URLs.
     * in future we will need to add URLs that needed to be available by authorized user
     * and add log page
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
