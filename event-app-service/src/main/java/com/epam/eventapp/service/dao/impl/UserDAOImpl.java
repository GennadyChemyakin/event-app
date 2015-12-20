package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Insert user into table
 * Get user id generated in the database
 */
public class UserDAOImpl extends GenericDAO implements UserDAO {

    private final String CREATE_USER = "INSERT INTO SEC_USER (username, password, email, name, surname, gender, photo," +
            "country, city, bio) VALUES(:username, :password, :email, :name, :surname, :gender, :photo," +
            ":country, :city, :bio)";

    @Override
    public boolean createUser(User user) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource ps = new MapSqlParameterSource()
                                    .addValue("username", user.getUsername())
                                    .addValue("password", user.getPassword())
                                    .addValue("email"   , user.getEmail())
                                    .addValue("name"    , user.getName())
                                    .addValue("surname" , user.getSurname())
                                    .addValue("gender"  , user.getGender())
                                    .addValue("photo"   , user.getPhoto())
                                    .addValue("country" , user.getCountry())
                                    .addValue("city"    , user.getCountry())
                                    .addValue("bio"     , user.getBio());

            getNamedParameterJdbcTemplate().update(CREATE_USER, ps, keyHolder);
            user.builder(user.getUsername(), user.getEmail()).id(keyHolder.getKey().intValue()).build();
            return true;
        } catch (Exception e) {
            return  false;
        }

    }
}
