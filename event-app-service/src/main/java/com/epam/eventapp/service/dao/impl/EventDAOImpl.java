package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.Collections;
import java.util.Optional;

/**
 * EventDAO implementation
 */
@Repository("EventDAO")
public class EventDAOImpl extends GenericDAO implements EventDAO {

    private final String GET_EVENT_BY_ID = "select e.id, e.name, e.description, e.country, e.city, e.address, " +
            "e.gps_latitude, e.gps_longitude, e.timestamp, u.id, u.username, u.email, u.name, u.surname, u.country, u.city, " +
            "u.bio  from event AS e JOIN sec_user AS u on event.user_id = sec_user.id where event.id=:id";
    private final String UPDATE_EVENT_BY_ID = "UPDATE event SET name=:name, description=:description, country=:country," +
            " city=:city, address=:address, gps_latitude=:gps_latitude, gps_longitude=:gps_longitude, " +
            "event_time=:event_time WHERE id=:id";

    @Override
    public Optional<Event> findById(int id) {
        try {
            Event event = getNamedParameterJdbcTemplate().queryForObject(GET_EVENT_BY_ID, Collections.singletonMap("id", id),
                    ((resultSet, i) -> {
                        return Event.builder(User.builder(resultSet.getString("u.username"), resultSet.getString("u.email")).
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
            return Optional.of(event);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int updateEventById(Event updatedEvent) {
        NamedParameterJdbcTemplate eventDB = getNamedParameterJdbcTemplate();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", updatedEvent.getId());
        namedParameters.addValue("name", updatedEvent.getName());
        namedParameters.addValue("description", updatedEvent.getDescription());
        namedParameters.addValue("country", updatedEvent.getCountry());
        namedParameters.addValue("city", updatedEvent.getCity());
        namedParameters.addValue("address", updatedEvent.getLocation());
        namedParameters.addValue("gps_latitude", updatedEvent.getGpsLatitude());
        namedParameters.addValue("gps_longitude", updatedEvent.getGpsLongitude());
        namedParameters.addValue("event_time", updatedEvent.getTimeStamp());
        int updatedRows = eventDB.update(UPDATE_EVENT_BY_ID, namedParameters);
        return updatedRows;
    }
}
