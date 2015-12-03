package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.Event;

/**
 * DAO for Event
 */
public interface EventDAO {

    /**
     * method for getting Event by id
     *
     * @param id - Event id
     * @return
     */
    Event findById(int id);

}
