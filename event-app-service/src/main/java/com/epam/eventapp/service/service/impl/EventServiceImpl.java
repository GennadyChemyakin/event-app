package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * EventService implementation
 */
@Transactional
@Service("eventService")
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
    private static final int EVENTS_AMOUNT = 10;

    @Autowired
    private EventDAO eventDAO;

    @Override
    public Optional<Event> findById(int id) {
        LOGGER.debug("findById started: Params id = {}", id);
        Optional<Event> event = eventDAO.findById(id);
        LOGGER.debug("findById finished. Result: {}", event);
        return event;
    }

    @PreAuthorize("@eventService.findById(#event.getId()).get().getUser().getUsername() == authentication.name")
    @Override
    public int updateEvent(Event event) {
        LOGGER.debug("updateEvent started: Params event = {}", event);
        int updatedEntries = eventDAO.updateEvent(event);
        LOGGER.debug("findById finished. Result: {}", updatedEntries);
        return updatedEntries;
    }

    @Override
    public Event createEvent(Event event, String userName) {
        LOGGER.debug("createEvent started: Params event = {}, userName = {}", event, userName);
        Event newEvent = eventDAO.addEvent(event, userName);
        LOGGER.debug("createEvent finished.");
        return newEvent;
    }

    @Override
    public List<Event> getOrderedEvents(LocalDateTime specifiedTime, QueryMode queryMode) {
        LOGGER.debug("getOrderedEvents started: Params specifiedTime = {}, queryMode = {}, amount = {}",
                specifiedTime, queryMode, EVENTS_AMOUNT);
        List<Event> eventList = eventDAO.getOrderedEvents(specifiedTime, EVENTS_AMOUNT, queryMode);
        LOGGER.debug("getOrderedEvents finished. Result: {}", eventList);
        return eventList;
    }


    @Override
    public int getNumberOfNewEvents(LocalDateTime before) {
        LOGGER.debug("getNumberOfNewEvents started: Params before = {}", before);
        int numberOfNewEvents = eventDAO.getNumberOfNewEvents(before);
        LOGGER.debug("getNumberOfNewEvents finished. Result: {}", numberOfNewEvents);
        return numberOfNewEvents;
    }
}
