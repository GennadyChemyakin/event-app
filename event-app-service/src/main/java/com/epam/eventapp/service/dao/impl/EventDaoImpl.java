package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

/**
 * EventDAO implementation
 */
@Repository("EventDAO")
public class EventDAOImpl extends GenericDAO implements EventDAO {


    private final String GET_EVENT_BY_ID = "select e.id as e_id, e.name as e_name, e.description as e_description, e.country as e_country, e.city as e_city, e.address as e_address, " +
            "e.gps_latitude as e_gps_latitude, e.gps_longitude as e_gps_longitude, e.event_time as e_event_time, u.id as u_id, u.username as u_username, u.email as u_email, " +
            "u.name as u_name, u.surname as u_surname, u.country as u_country, u.city as u_city, " +
            "u.bio as u_bio from event e JOIN sec_user u on e.sec_user_id = u.id where e.id=:id";


    @Override
    public Optional<Event> findById(int id) {
        try {
            Event event = getNamedParameterJdbcTemplate().queryForObject(GET_EVENT_BY_ID, Collections.singletonMap("id", id),
                    ((resultSet, i) -> {
                        return Event.builder(User.builder(resultSet.getString("u_username"), resultSet.getString("u_email")).
                                name(resultSet.getString("u_name")).
                                id(resultSet.getInt("u_id")).
                                surname(resultSet.getString("u_surname")).
                                country(resultSet.getString("u_country")).
                                city(resultSet.getString("u_city")).
                                bio(resultSet.getClob("u_bio").toString()).build(), resultSet.getString("e_name")).
                                id(resultSet.getInt("e_id")).
                                description(resultSet.getClob("e_description").toString()).
                                country(resultSet.getString("e_country")).
                                city(resultSet.getString("e_city")).
                                location(resultSet.getString("e_address")).
                                gpsLatitude(resultSet.getDouble("e_gps_latitude")).
                                gpsLongitude(resultSet.getDouble("e_gps_longitude")).
                                timeStamp(resultSet.getTimestamp("e_event_time").toLocalDateTime()).build();
                    })
            );
            return Optional.of(event);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
