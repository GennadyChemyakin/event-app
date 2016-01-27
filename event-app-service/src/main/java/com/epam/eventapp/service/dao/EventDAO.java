package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.Event;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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

    /**
     * Method for updating Event by id.
     * @param event - Event with updated data
     * @return Number of updated rows
     */
    int updateEventById(Event event);

    /**
     * Method for getting List of all Events sorted by their timestamp in descending order
     * @return Optional.of(List of Events) if we've found any events, otherwise Optional.empty()
     */
    List<Event> getEventListFixedSizeBeforeTimeOrderedByCreationTimeDesc(LocalDateTime eventTime, int amount);

    /**
     * Method for getting number of all events in database
     * @return Number of all events in database
     */
    int getNumberOfEvents();
}
