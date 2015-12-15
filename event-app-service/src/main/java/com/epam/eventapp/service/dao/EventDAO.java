package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.Event;


import java.util.Optional;

/**
 * DAO for Event
 */
public interface EventDAO {

    /**
     * Method for getting Event by id.
     *
     * @param id - id of needed Event
     * @return Optional.of(event) if we've found event by id, otherwise Optional.empty()
     */
    Optional<Event> findById(int id);

}
