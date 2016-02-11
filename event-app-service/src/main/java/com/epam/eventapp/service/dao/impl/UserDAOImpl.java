package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.UserNotCreatedException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Optional;

/**
 * Insert user into table
 * Get user id generated in the database
 */
@Repository
public class UserDAOImpl extends GenericDAO implements UserDAO {

    private static final String CREATE_USER_QUERY = "INSERT INTO SEC_USER (id, username, password, email, name, surname, gender, photo," +
            "country, city, bio) VALUES(SEC_USER_ID_SEQ.nextval, :username, :password, :email, :name, :surname, :gender, :photo," +
            ":country, :city, :bio)";

    private static final String ADD_ROLE_TO_NEW_USER = "INSERT INTO SEC_USER_AUTHORITY (SEC_USER_ID,AUTHORITY_ID) VALUES (:id,"
            + "(SELECT ID FROM AUTHORITY WHERE AUTHORITY = 'ROLE_USER'))";

    private static final String СOUNT_USER_BY_USERNAME = "SELECT count(*) FROM SEC_USER WHERE username = :username";

    private static final String СOUNT_USER_BY_EMAIL = "SELECT count(*) FROM SEC_USER WHERE email = :email";

    private static final String GET_USER_BY_USERNAME = "SELECT username, email, name, surname, gender, photo, country, city, bio " +
            "FROM SEC_USER WHERE username = :username";

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

          try {

              getNamedParameterJdbcTemplate().update(CREATE_USER_QUERY, ps, keyHolder, new String[]{"id"});

              user.builder(user.getUsername(), user.getEmail()).id(keyHolder.getKey().intValue())
                      .password("")
                      .build();

              getNamedParameterJdbcTemplate().update(ADD_ROLE_TO_NEW_USER, new MapSqlParameterSource()
                      .addValue("id", keyHolder.getKey().intValue()));

          } catch(DataAccessException ex) {
              final String msg = String.format("Failed to connect to database and create user: %s ", user);
              throw new UserNotCreatedException(msg);
          }

    }

    @Override
    public boolean isUserNameRegistered(String username) {
        try {
            Integer cnt = getNamedParameterJdbcTemplate().queryForObject(СOUNT_USER_BY_USERNAME, new MapSqlParameterSource()
                    .addValue("username", username), Integer.class);
            return cnt > 0;
        } catch (DataAccessException ex) {
            return false;
        }

    }

    @Override
    public boolean isEmailRegistered(String email) {
        try {
            Integer cnt = getNamedParameterJdbcTemplate().queryForObject(СOUNT_USER_BY_EMAIL, new MapSqlParameterSource()
                    .addValue("email", email), Integer.class);
            return cnt > 0;
        } catch (DataAccessException ex) {
            return false;
        }

    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        try {
            User user = getNamedParameterJdbcTemplate().queryForObject(GET_USER_BY_USERNAME, Collections.singletonMap("username", userName),
                    ((resultSet, i) -> {
                        return User.builder(resultSet.getString("username"), resultSet.getString("email"))
                                .bio(resultSet.getString("bio"))
                                .city(resultSet.getString("city"))
                                .country(resultSet.getString("country"))
                                .photo(resultSet.getBytes("photo"))
                                .surname(resultSet.getString("surname"))
                                .name(resultSet.getString("name"))
                                .gender(resultSet.getString("gender"))
                                .build();
                    }));

            return Optional.of(user);
        } catch (Exception ex) {
            final String msg = String.format("failed to find user with username %s", userName);
            throw new UserNotCreatedException(msg);
        }

    }
}
