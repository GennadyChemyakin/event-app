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
     * Gets a list of events ordered by create time in desc mode. Size of the list is limited by amount.
     * Depending on creationTimeQueryMode creationTime of all these events will be less than oldestEventCreationTime
     * or more than oldestEventCreationTime.
     * @param newestEventCreationTime creationTime of the newest event in the list of events.
     * @param oldestEventCreationTime creationTime of the oldest event in the list of events.
     * @param amount max number of returned events
     * @param creationTimeQueryMode mode for creationTime of Event for SQL query, should be either "LESS_THAN" or "MORE_THAN"
     * @return Page of Events
     */
    List<Event> getOrderedEvents(LocalDateTime newestEventCreationTime, LocalDateTime oldestEventCreationTime,
                                 int amount, String creationTimeQueryMode);

    /**
     * Method for getting number of all events in database
     * @return Number of all events in database
     */
    int getNumberOfNewEvents(LocalDateTime newestEventCreationTime);
}
