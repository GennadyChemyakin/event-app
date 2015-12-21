package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Insert user into table
 * Get user id generated in the database
 */
@Repository
public class UserDAOImpl extends GenericDAO implements UserDAO {

    private final String CREATE_USER_QUERY = "INSERT INTO SEC_USER (username, password, email, name, surname, gender, photo," +
            "country, city, bio) VALUES(:username, :password, :email, :name, :surname, :gender, :photo," +
            ":country, :city, :bio)";

    @Override
    public void createUser(User user) {

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

            getNamedParameterJdbcTemplate().update(CREATE_USER_QUERY, ps, keyHolder);
            user.builder("", user.getEmail()).id(keyHolder.getKey().intValue())
                    .password("")
                    .build();

    }
}
