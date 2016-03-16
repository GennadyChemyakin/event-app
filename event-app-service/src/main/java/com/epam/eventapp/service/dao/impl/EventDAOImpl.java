package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.exceptions.ObjectNotCreatedException;
import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * EventDAO implementation
 */
@Repository("EventDAO")
public class EventDAOImpl extends GenericDAO implements EventDAO {

/*
    private static final String GET_EVENT_BY_ID = "select e.id as e_id, e.name as e_name, e.description as e_description, e.country as e_country, e.city as e_city, e.address as e_address, " +
            "e.gps_latitude as e_gps_latitude, e.gps_longitude as e_gps_longitude, e.event_time as e_event_time, u.id as u_id, u.username as u_username, u.email as u_email, " +
            "u.name as u_name, u.surname as u_surname, u.country as u_country, u.city as u_city, " +
            "u.bio as u_bio from event e JOIN sec_user u on e.sec_user_id = u.id where e.id=:id";*/

    private static final String ADD_EVENT = "INSERT INTO EVENT (id, name, description, country, city, address, gps_latitude, gps_longitude," +
            "create_time, event_time, sec_user_id) VALUES(EVENT_ID_SEQ.nextval, :name, :description, :country, :city, :address, :gps_latitude, :gps_longitude," +
            ":create_time, :event_time, (SELECT ID FROM SEC_USER WHERE username = :username))";

    private static final String GET_EVENT_BY_ID = "select e.id as e_id, e.name as e_name, e.description, e.country as e_country, " +
            "e.city as e_city, e.address, e.gps_latitude, e.gps_longitude, e.event_time, e.create_time, u.id as u_id, " +
            "u.username, u.email, u.name as u_name, u.surname, u.country as u_country, u.city as u_city, " +
            "u.bio from event e JOIN sec_user u on e.sec_user_id = u.id where e.id=:id";
    private static final String UPDATE_EVENT_BY_ID = "UPDATE event SET name=:name, description=:description, country=:country," +
            " city=:city, address=:address, gps_latitude=:gps_latitude, gps_longitude=:gps_longitude, " +
            "event_time=:event_time WHERE id=:id";

    private static final String SELECT_EVENT =
            "SELECT event_alias.*, rownum rnum FROM (SELECT e.id as e_id, e.name as e_name, e.description, " +
                    "e.country as e_country, e.city as e_city, e.address, e.gps_latitude, e.gps_longitude, e.event_time, " +
                    "e.create_time, u.id as u_id, u.username, u.email, u.name as u_name, u.surname, " +
                    "u.country as u_country, u.city as u_city, u.bio FROM event e JOIN sec_user u on e.sec_user_id = u.id ";
    private static final String WHERE_CREATION_TIME_BEFORE =
            "WHERE e.create_time < :creation_time ORDER BY e.create_time DESC) event_alias WHERE rownum <= :amount";
    private static final String WHERE_CREATION_TIME_AFTER =
            "WHERE e.create_time > :creation_time ORDER BY e.create_time DESC) event_alias WHERE rownum <= :amount";

    private static final String GET_NUMBER_OF_EVENTS = "SELECT COUNT(*) FROM event WHERE event.create_time > :creation_time";

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
                                eventTime(resultSet.getTimestamp("event_time") == null ? null : resultSet.getTimestamp("event_time").toLocalDateTime()).
                                creationTime(resultSet.getTimestamp("create_time").toLocalDateTime()).build();
                    })
            );
            return Optional.of(event);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Event addEvent(Event event, String userName) {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        SqlParameterSource ps = new MapSqlParameterSource()
                .addValue("name", event.getName())
                .addValue("country", event.getCountry().orElse(null))
                .addValue("city", event.getCity().orElse(null))
                .addValue("description", event.getDescription().orElse(null))
                .addValue("gps_latitude", event.getGpsLatitude())
                .addValue("gps_longitude", event.getGpsLongitude())
                .addValue("address", event.getLocation().orElse(null))
                .addValue("event_time", event.getEventTime().isPresent() ? Timestamp.valueOf(event.getEventTime().get()) : null)
                .addValue("create_time", Timestamp.valueOf(now))
                .addValue("username", userName);

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getNamedParameterJdbcTemplate().update(ADD_EVENT, ps, keyHolder, new String[]{"id"});

            event = Event.builder(event.getName())
                    .id(keyHolder.getKey().intValue())
                    .country(event.getCountry().orElse(null))
                    .city(event.getCity().orElse(null))
                    .description(event.getDescription().orElse(null))
                    .gpsLatitude(event.getGpsLatitude())
                    .gpsLongitude(event.getGpsLongitude())
                    .eventTime(event.getEventTime().orElse(null))
                    .location(event.getLocation().orElse(null))
                    .build();

            return event;
        } catch (DataAccessException ex) {
            final String msg = String.format("Failed to create event. Date: %s, User name: %s, Event: %s ", now, userName, event);
            throw new ObjectNotCreatedException(msg);
        }

    }

    @Override
    public int updateEvent(Event updatedEvent) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", updatedEvent.getId());
        namedParameters.addValue("name", updatedEvent.getName());
        namedParameters.addValue("description", updatedEvent.getDescription().orElse(null));
        namedParameters.addValue("country", updatedEvent.getCountry().orElse(null));
        namedParameters.addValue("city", updatedEvent.getCity().orElse(null));
        namedParameters.addValue("address", updatedEvent.getLocation().orElse(null));
        namedParameters.addValue("gps_latitude", updatedEvent.getGpsLatitude());
        namedParameters.addValue("gps_longitude", updatedEvent.getGpsLongitude());
        namedParameters.addValue("event_time",
                updatedEvent.getEventTime().isPresent() ? Timestamp.valueOf(updatedEvent.getEventTime().get()) : null);
        return getNamedParameterJdbcTemplate().update(UPDATE_EVENT_BY_ID, namedParameters);
    }

    @Override
    public List<Event> getOrderedEvents(LocalDateTime effectiveTime, int amount, QueryMode queryMode) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("amount", amount);
        String sqlQuery = SELECT_EVENT;
        params.addValue("creation_time", Timestamp.valueOf(effectiveTime));
        switch (queryMode) {
            case BEFORE:
                sqlQuery += WHERE_CREATION_TIME_BEFORE;
                break;
            case AFTER:
                sqlQuery += WHERE_CREATION_TIME_AFTER;
                break;
            default: {
                throw new IllegalArgumentException("Unsupported query mode " + queryMode);
            }
        }

        List<Event> eventList = getNamedParameterJdbcTemplate().query(sqlQuery, params, ((resultSet, i) -> {
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
                            eventTime((resultSet.getTimestamp("event_time") == null) ? null : resultSet.getTimestamp("event_time").toLocalDateTime()).
                            creationTime(resultSet.getTimestamp("create_time").toLocalDateTime()).build();
                })
        );
        return eventList;
    }

    @Override
    public int getNumberOfNewEvents(LocalDateTime before) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("creation_time", Timestamp.valueOf(before));
        return getNamedParameterJdbcTemplate().queryForObject(GET_NUMBER_OF_EVENTS, params, Integer.class);
    }
}
