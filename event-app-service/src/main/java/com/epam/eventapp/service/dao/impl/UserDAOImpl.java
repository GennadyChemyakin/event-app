package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;

/**
 * Insert user into table
 * Get user id generated in the database
 */
@Repository
public class UserDAOImpl extends GenericDAO implements UserDAO {

    private final String CREATE_USER_QUERY = "INSERT INTO SEC_USER (id, username, password, email, name, surname, gender, photo," +
            "country, city, bio) VALUES(AUTHORITY_ID_SEQ.nextval, :username, :password, :email, :name, :surname, :gender, :photo," +
            ":country, :city, :bio)";

    private final String ADD_ROLE_TO_NEW_USER = "INSERT INTO SEC_USER_AUTHORITY (SEC_USER_ID,AUTHORITY_ID) VALUES (:id,"
            + "(SELECT ID FROM AUTHORITY WHERE AUTHORITY = 'ROLE_USER')";

    private final String SELECT_USER_BY_USERNAME = "SELECT count(*) FROM SEC_USER WHERE username = :username";

    private final String SELECT_USER_BY_EMAIL = "SELECT count(*) FROM SEC_USER WHERE email = :email";

    @Override
    @Transactional
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

            getNamedParameterJdbcTemplate().update(CREATE_USER_QUERY, ps, keyHolder, new String[]{"id"});
            user.builder(user.getUsername(), user.getEmail()).id(keyHolder.getKey().intValue())
                    .password("")
                    .build();

            getNamedParameterJdbcTemplate().update(ADD_ROLE_TO_NEW_USER, new MapSqlParameterSource()
                .addValue("id", user.getId()));

    }

    @Override
    public boolean isUserNameRegistered(String username) {
        Integer cnt = getNamedParameterJdbcTemplate().queryForObject(SELECT_USER_BY_USERNAME, new MapSqlParameterSource()
                .addValue("username", username),Integer.class);
        return cnt != null && cnt > 0;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        Integer cnt = getNamedParameterJdbcTemplate().queryForObject(SELECT_USER_BY_EMAIL, new MapSqlParameterSource()
                .addValue("email", email),Integer.class);
        return cnt != null && cnt > 0;
    }
}
