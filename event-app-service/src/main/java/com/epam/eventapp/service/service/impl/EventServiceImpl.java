package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * EventService implementation
 */
@Service("EventService")
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventDAO eventDAO;

    @Override
    public Optional<Event> findById(int id) {
        logger.debug("findById from EventServiceImpl method has been started: id =" + id);
        Optional<Event> event = eventDAO.findById(id);
        logger.debug("findById from EventServiceImpl method has been finished. Return Optional<event>: " +
                "event.isPresent() = " + event.isPresent() +
                "; event id = " + event.map(Event::getId).map(String::valueOf).orElse("null") +
                "; event name = " + event.map(Event::getName).orElse("null"));
        return event;
    }
}
