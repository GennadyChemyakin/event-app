package com.epam.eventapp.service.service;

import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.domain.Event;

import java.time.LocalDateTime;
import java.util.List;
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
     * Gets events before or after the specified effective time.
     * If the provided mode is <BEFORE> the method will fetch events before the specified effective time,
     * if the mode is <AFTER> - after the specified effective time.
     * @param effectiveTime Effective time.
     * @param queryMode Specifies whether the method should fetch events before the effective date or after.
     * @return List of Events.
     */
    List<Event> getOrderedEvents(LocalDateTime effectiveTime, QueryMode queryMode);

    /**
     * Gets number of events before specified date.
     * @param before Specified date.
     * @return Number of events.
     */
    int getNumberOfNewEvents(LocalDateTime before);
}

