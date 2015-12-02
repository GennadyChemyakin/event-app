package com.epam.eventappservices.dao.impl;

import com.epam.eventappservices.dao.EventDao;
import com.epam.eventappservices.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

/**
 * EventDao Repository
 */
@Repository("EventDao")
public class EventDaoImpl implements EventDao {


    private NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public EventDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Event findById(int id) {
        final String query = "select * from event JOIN sec_user on event.user_id = sec_user.id where event.id=:id";
        return jdbcOperations.queryForObject(query, Collections.singletonMap("id", id),
                ((resultSet, i) -> {
                    return new Event(resultSet.getInt("event.id"),
                            new User(resultSet.getInt("sec_user.id"),
                                    resultSet.getString("sec_user.username"),
                                    resultSet.getString("sec_user.password"),
                                    resultSet.getString("sec_user.email"),
                                    resultSet.getString("sec_user.name"),
                                    resultSet.getString("sec_user.surname"),
                                    resultSet.getString("sec_user.gender"),
                                    resultSet.getBytes("sec_user.photo"),
                                    resultSet.getString("sec_user.country"),
                                    resultSet.getString("sec_user.city"),
                                    resultSet.getString("sec_user.bio")),
                            resultSet.getString("event.name"),
                            resultSet.getString("event.description"),
                            resultSet.getString("event.country"),
                            resultSet.getString("event.city"),
                            resultSet.getString("event.address"),
                            resultSet.getDouble("event.gps_latitude"),
                            resultSet.getDouble("event.gps_longitude"),
                            LocalDateTime.ofInstant(resultSet.getDate("event.timestamp").toInstant(), ZoneId.systemDefault())
                    );
                }));
    }
}
