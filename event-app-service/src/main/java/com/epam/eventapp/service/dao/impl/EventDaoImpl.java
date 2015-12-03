package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;

/**
 * EventDAO implementation
 */
@Repository("EventDAO")
public class EventDAOImpl extends GenericDAO implements EventDAO {

    private NamedParameterJdbcOperations jdbcTemplate = getNamedParameterJdbcTemplate();

    private final String queryForGettingEvent = "select e.id, e.name, e.description, e.country, e.city, e.address, " +
            "e.gps_latitude, e.gps_longitude, e.timestamp, u.id, u.username, u.email, u.name, u.surname, u.country, u.city, " +
            "u.bio  from event AS e JOIN sec_user AS u on event.user_id = sec_user.id where event.id=:id";


    @Override
    public Event findById(int id) {
        return jdbcTemplate.queryForObject(queryForGettingEvent, Collections.singletonMap("id", id),
                ((resultSet, i) -> {
                    return new Event.EventBuilder(new User.UserBuilder(resultSet.getString("u.username"), resultSet.getString("u.email")).
                            name(resultSet.getString("u.name")).
                            id(resultSet.getInt("u.id")).
                            surname(resultSet.getString("u.surname")).
                            country(resultSet.getString("u.country")).
                            city(resultSet.getString("u.city")).
                            bio(resultSet.getString("u.bio")).build(), resultSet.getString("e.name")).
                            id(resultSet.getInt("e.id")).
                            description(resultSet.getString("e.description")).
                            country(resultSet.getString("e.country")).
                            city(resultSet.getString("e.city")).
                            location(resultSet.getString("e.address")).
                            gpsLatitude(resultSet.getDouble("e.gps_latitude")).
                            gpsLongitude(resultSet.getDouble("e.gps_longitude")).
                            timeStamp(resultSet.getTimestamp("e.timestamp").toLocalDateTime()).build();
                })
        );
    }
}
