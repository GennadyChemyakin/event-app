package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

/**
 * UserDAO implementation
 */
@Repository("UserDAO")
public class UserDAOImpl extends GenericDAO implements UserDAO {

    private static final String GET_USER_BY_USERNAME = "select id, username, email, name, surname, gender, photo, country, city, bio from sec_user where username=:username";

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            User user = getNamedParameterJdbcTemplate().queryForObject(GET_USER_BY_USERNAME, Collections.singletonMap("username", username),
                    ((resultSet, i) -> {
                        return User.builder(resultSet.getString("username"), resultSet.getString("email")).
                                id(resultSet.getInt("id")).
                                name(resultSet.getString("name")).
                                surname(resultSet.getString("surname")).
                                gender(resultSet.getString("gender")).
                                country(resultSet.getString("country")).
                                city(resultSet.getString("city")).
                                bio(resultSet.getString("bio")).build();
                    })
            );
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
