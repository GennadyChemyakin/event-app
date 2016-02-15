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

    /**
     * Method for updating Event by id.
     * @param event - Event with updated data
     * @return Number of updated rows
     */
    int updateEventById(Event event);

    /**
     * Method for adding new Event.
     * @param event - Event to add.
     * @param userName    - name of user who adds Event.
     */
    //TO DO change signature for this method.
    Event addEvent(Event event, String userName);

}
