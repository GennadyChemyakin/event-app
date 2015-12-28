package com.epam.eventapp.service.service;

import com.epam.eventapp.service.domain.Event;

import java.util.Optional;

/**
 * Event service
 */
public interface EventService {

    /**
     * Method for getting Event by id.
     *
     * @param id - id of needed Event
     * @return Optional.of(event) if we've found event by id, otherwise Optional.empty()
     */
    Optional<Event> findById(int id);
}
