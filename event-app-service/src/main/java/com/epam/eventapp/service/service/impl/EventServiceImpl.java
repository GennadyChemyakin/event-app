package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * EventService implementation
 */
@Service("EventService")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;

    @Override
    public Optional<Event> findById(int id) {
        return eventDAO.findById(id);
    }
}
