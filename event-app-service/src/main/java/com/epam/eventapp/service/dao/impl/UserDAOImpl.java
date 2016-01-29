package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.UserNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;

/**
 * Insert user into table
 * Get user id generated in the database
 */
@Repository
public class UserDAOImpl extends GenericDAO implements UserDAO {

    private final String CREATE_USER_QUERY = "INSERT INTO SEC_USER (id, username, password, email, name, surname, gender, photo," +
            "country, city, bio) VALUES(AUTHORITY_ID_SEQ.nextval, :username, :password, :email, :name, :surname, :gender, :photo," +
            ":country, :city, :bio)";

    private final String GET_USER_BY_USERNAME = "select id, username, email, name, surname," +
            "country, city, bio, gender, photo from sec_user where username = :username";

    @Override
    public int createUser(User user) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource ps = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail())
                .addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("gender", user.getGender())
                .addValue("photo", user.getPhoto())
                .addValue("country", user.getCountry())
                .addValue("city", user.getCountry())
                .addValue("bio", user.getBio());

        int rows = getNamedParameterJdbcTemplate().update(CREATE_USER_QUERY, ps, keyHolder, new String[] {"id"});
        user.builder(user.getUsername(), user.getEmail()).id(keyHolder.getKey().intValue())
                .password("")
                .build();
        return rows;

    }

    @Override
    public User getUserByUsername(String username) {
        try {
            User user = getNamedParameterJdbcTemplate().queryForObject(GET_USER_BY_USERNAME, Collections.singletonMap("username", username),
                    ((resultSet, i) -> User.builder(resultSet.getString("username"), resultSet.getString("email")).
                            id(resultSet.getInt("id")).
                            country(resultSet.getString("country")).
                            city(resultSet.getString("city")).
                            bio(resultSet.getString("bio")).
                            name(resultSet.getString("name")).
                            surname(resultSet.getString("surname")).
                            gender(resultSet.getString("gender")).
                            photo(resultSet.getBytes("photo")).build()));
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("can't find user by username = " + username);
        }
    }
}
