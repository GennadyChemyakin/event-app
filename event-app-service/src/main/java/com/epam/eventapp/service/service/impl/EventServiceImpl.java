package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.model.EventPack;
import com.epam.eventapp.service.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * EventService implementation
 */
@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
    private static final int EVENTS_AMOUNT = 2;

    @Autowired
    private EventDAO eventDAO;

    @Override
    public Optional<Event> findById(int id) {
        LOGGER.debug("findById started: Params id = {}", id);
        Optional<Event> event = eventDAO.findById(id);
        LOGGER.debug("findById finished. Result: {}", event);
        return event;
    }

    @Override
    public int updateEvent(Event event) {
        LOGGER.debug("updateEvent started: Params event = {}", event);
        int updatedEntries = eventDAO.updateEventById(event);
        LOGGER.debug("findById finished. Result: {}", updatedEntries);
        return updatedEntries;
    }

    @Override
    public EventPack getEventsBeforeTime(LocalDateTime creationTime, String creationTimeQueryMode) {
        LOGGER.debug("getOrderedEvents started: Params creationTime = {}, amount = {}, creationTimeQueryMode = {}",
                creationTime, EVENTS_AMOUNT, creationTimeQueryMode);
        List<Event> eventList = eventDAO.getOrderedEvents(creationTime, EVENTS_AMOUNT, creationTimeQueryMode);
        EventPack eventPack = new EventPack(eventList, eventDAO.getNumberOfEvents());
        LOGGER.debug("getOrderedEvents started: Result: {}", eventPack);
        return eventPack;
    }
}
