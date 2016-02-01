package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * EventDAO implementation
 */
@Repository("EventDAO")
public class EventDAOImpl extends GenericDAO implements EventDAO {



    private static final String GET_EVENT_BY_ID = "select e.id as e_id, e.name as e_name, e.description, e.country as e_country, " +
            "e.city as e_city, e.address, e.gps_latitude, e.gps_longitude, e.event_time, e.create_time, u.id as u_id, " +
            "u.username, u.email, u.name as u_name, u.surname, u.country as u_country, u.city as u_city, " +
            "u.bio from event e JOIN sec_user u on e.sec_user_id = u.id where e.id=:id";
	private static final String UPDATE_EVENT_BY_ID = "UPDATE event SET name=:name, description=:description, country=:country," +
            " city=:city, address=:address, gps_latitude=:gps_latitude, gps_longitude=:gps_longitude, " +
            "event_time=:event_time WHERE id=:id";

    private static final String GET_FIXED_NUMBER_OF_EVENTS_BEFORE_TIME_ORDERED_BY_CREATION_TIME_DESCENDING =
            "SELECT event_alias.*, rownum rnum FROM (SELECT e.id as e_id, e.name as e_name, e.description, " +
                    "e.country as e_country, e.city as e_city, e.address, e.gps_latitude, e.gps_longitude, e.event_time, " +
                    "e.create_time, u.id as u_id, u.username, u.email, u.name as u_name, u.surname, " +
                    "u.country as u_country, u.city as u_city, u.bio FROM event e JOIN sec_user u on e.sec_user_id = u.id " +
                    "WHERE e.create_time < :creation_time ORDER BY e.create_time DESC) event_alias WHERE rownum <= :amount";
    private static final String GET_NUMBER_OF_EVENTS = "SELECT COUNT(*) FROM event";


    @Override
    public Optional<Event> findById(int id) {
        try {
            Event event = getNamedParameterJdbcTemplate().queryForObject(GET_EVENT_BY_ID, Collections.singletonMap("id", id),
                    ((resultSet, i) -> {
                        return Event.builder(resultSet.getString("e_name")).
                                user(User.builder(resultSet.getString("username"), resultSet.getString("email")).
                                        name(resultSet.getString("u_name")).
                                        id(resultSet.getInt("u_id")).
                                        surname(resultSet.getString("surname")).
                                        country(resultSet.getString("u_country")).
                                        city(resultSet.getString("u_city")).
                                        bio(resultSet.getString("bio")).build()).
                                id(resultSet.getInt("e_id")).
                                description(resultSet.getString("description")).
                                country(resultSet.getString("e_country")).
                                city(resultSet.getString("e_city")).
                                location(resultSet.getString("address")).
                                gpsLatitude(resultSet.getDouble("gps_latitude")).
                                gpsLongitude(resultSet.getDouble("gps_longitude")).
                                eventTime(resultSet.getTimestamp("event_time").toLocalDateTime()).
                                creationTime(resultSet.getTimestamp("create_time").toLocalDateTime()).build();
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
        namedParameters.addValue("event_time", Timestamp.valueOf(updatedEvent.getEventTime()));
        return getNamedParameterJdbcTemplate().update(UPDATE_EVENT_BY_ID, namedParameters);
    }

    @Override
    public List<Event> getEventsBeforeTime(LocalDateTime creationTime, int amount) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("creation_time", Timestamp.valueOf(creationTime));
        params.addValue("amount", amount);

        List<Event> eventList = getNamedParameterJdbcTemplate().query(GET_FIXED_NUMBER_OF_EVENTS_BEFORE_TIME_ORDERED_BY_CREATION_TIME_DESCENDING, params,
                    ((resultSet, i) -> {
                        return Event.builder(resultSet.getString("e_name")).
                                user(User.builder(resultSet.getString("username"), resultSet.getString("email")).
                                        name(resultSet.getString("u_name")).
                                        id(resultSet.getInt("u_id")).
                                        surname(resultSet.getString("surname")).
                                        country(resultSet.getString("u_country")).
                                        city(resultSet.getString("u_city")).
                                        bio(resultSet.getString("bio")).build()).
                                id(resultSet.getInt("e_id")).
                                description(resultSet.getString("description")).
                                country(resultSet.getString("e_country")).
                                city(resultSet.getString("e_city")).
                                location(resultSet.getString("address")).
                                gpsLatitude(resultSet.getDouble("gps_latitude")).
                                gpsLongitude(resultSet.getDouble("gps_longitude")).
                                eventTime(resultSet.getTimestamp("event_time").toLocalDateTime()).
                                creationTime(resultSet.getTimestamp("create_time").toLocalDateTime()).build();
                    })
            );
        return eventList;
    }

    @Override
    public int getNumberOfEvents() {
        return getNamedParameterJdbcTemplate().queryForObject(GET_NUMBER_OF_EVENTS, new MapSqlParameterSource(), Integer.class);
    }
}
