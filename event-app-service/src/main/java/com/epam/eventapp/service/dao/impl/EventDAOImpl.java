package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.EventNotCreatedException;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.zone.ZoneRules;
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

    private static final String ADD_EVENT = "INSERT INTO EVENT (id, name, description, country, city, address, gps_latitude, gps_longitude," +
            "create_time, event_time, sec_user_id) VALUES(EVENT_ID_SEQ.nextval, :name, :description, :country, :city, :address, :gps_latitude, :gps_longitude," +
            ":create_time, :event_time, (SELECT ID FROM SEC_USER WHERE username = :username))";


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
    public Event addEvent(Event event, String userName) {

        Instant now = Instant.now();

        SqlParameterSource ps = new MapSqlParameterSource()
                .addValue("name"            , event.getName())
                .addValue("country"         , event.getCountry())
                .addValue("city"            , event.getCity())
                .addValue("description"     , event.getDescription())
                .addValue("gps_latitude"    , event.getGpsLatitude())
                .addValue("gps_longitude"   , event.getGpsLongitude())
                .addValue("address"         , event.getLocation())
                .addValue("event_time"      , event.getTimeStamp() != null ? Timestamp.valueOf(event.getTimeStamp()) : null)
                .addValue("create_time"     , Timestamp.from(now))
                .addValue("username"        , userName);

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getNamedParameterJdbcTemplate().update(ADD_EVENT, ps, keyHolder, new String[]{"id"});

            event = Event.builder(event.getName())
                    .id(keyHolder.getKey().intValue())
                    .country(event.getCountry())
                    .city(event.getCity())
                    .description(event.getDescription())
                    .gpsLatitude(event.getGpsLatitude())
                    .gpsLongitude(event.getGpsLongitude())
                    .timeStamp(event.getTimeStamp())
                    .location(event.getLocation())
                    .build();

            return event;
        } catch(DataAccessException ex) {
            final String msg = String.format("Failed to create event. Date: %s, User name: %s, Event: %s ", now, userName, event);
            throw new EventNotCreatedException(msg);
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
