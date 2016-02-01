package com.epam.eventapp.service.service;

import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.model.EventPack;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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


    /**
     * Method for updating Event.
     * @param event - Event with updated data
     * @return Number of updated rows
     */
    int updateEvent(Event event);

    /**
     * Gets a list of events ordered by create time in desc mode. Size of the list is limited by the provided amount parameter.
     * @param eventTime creationTime of returned events is limited by eventTime.
     * @param amount max number of returned events
     * @return Page of Events
     */
    EventPack getEventsBeforeTime(LocalDateTime eventTime, int amount);
}
