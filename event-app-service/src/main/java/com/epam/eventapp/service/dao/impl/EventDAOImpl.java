package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

/**
 * EventDAO implementation
 */
@Repository("EventDAO")
public class EventDAOImpl extends GenericDAO implements EventDAO {



    private static final String GET_EVENT_BY_ID = "select e.id as e_id, e.name as e_name, e.description as e_description, e.country as e_country, e.city as e_city, e.address as e_address, " +
            "e.gps_latitude as e_gps_latitude, e.gps_longitude as e_gps_longitude, e.event_time as e_event_time, u.id as u_id, u.username as u_username, u.email as u_email, " +
            "u.name as u_name, u.surname as u_surname, u.country as u_country, u.city as u_city, " +
            "u.bio as u_bio from event e JOIN sec_user u on e.sec_user_id = u.id where e.id=:id";
	private static final String UPDATE_EVENT_BY_ID = "UPDATE event SET name=:name, description=:description, country=:country," +
            " city=:city, address=:address, gps_latitude=:gps_latitude, gps_longitude=:gps_longitude, " +
            "event_time=:event_time WHERE id=:id";


    @Override
    public Optional<Event> findById(int id) {
        try {
            Event event = getNamedParameterJdbcTemplate().queryForObject(GET_EVENT_BY_ID, Collections.singletonMap("id", id),
                    ((resultSet, i) -> {
                        return Event.builder(resultSet.getString("e_name")).
                                user(User.builder(resultSet.getString("u_username"), resultSet.getString("u_email")).
                                        name(resultSet.getString("u_name")).
                                        id(resultSet.getInt("u_id")).
                                        surname(resultSet.getString("u_surname")).
                                        country(resultSet.getString("u_country")).
                                        city(resultSet.getString("u_city")).
                                        bio(resultSet.getString("u_bio")).build()).
                                id(resultSet.getInt("e_id")).
                                description(resultSet.getString("e_description")).
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

    @Override
    public int updateEventById(Event updatedEvent) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", updatedEvent.getId());
        namedParameters.addValue("name", updatedEvent.getName());
        namedParameters.addValue("description", updatedEvent.getDescription());
        namedParameters.addValue("country", updatedEvent.getCountry());
        namedParameters.addValue("city", updatedEvent.getCity());
        namedParameters.addValue("address", updatedEvent.getLocation());
        namedParameters.addValue("gps_latitude", updatedEvent.getGpsLatitude());
        namedParameters.addValue("gps_longitude", updatedEvent.getGpsLongitude());
        namedParameters.addValue("event_time", Timestamp.valueOf(updatedEvent.getTimeStamp()));
        return getNamedParameterJdbcTemplate().update(UPDATE_EVENT_BY_ID, namedParameters);
    }
}
