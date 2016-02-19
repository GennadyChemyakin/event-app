package com.epam.eventappweb.controller;

import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.service.EventService;
import com.epam.eventappweb.exceptions.EventNotFoundException;
import com.epam.eventappweb.exceptions.EventNotUpdatedException;
import com.epam.eventappweb.model.EventPackVO;
import com.epam.eventappweb.model.EventPreviewVO;
import com.epam.eventappweb.model.EventVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Rest controller. Return ResponseEntity with event as body and HttpStatus code OK if we've found event by id,
 * otherwise return ResponseEntity with HttpStatus code 404
 */
@RestController
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public EventVO getEventDetail(@PathVariable("id") int eventId) {
        LOGGER.info("getEventDetail started. Param: id = {} ", eventId);
        Optional<Event> eventOptional = eventService.findById(eventId);
        if (!eventOptional.isPresent()) {
            throw new EventNotFoundException("Event Not Found by ID = " + eventId);
        }
        Event event = eventOptional.get();
        EventVO eventVO = EventVO.builder(event.getName())
                .city(event.getCity())
                .country(event.getCountry())
                .description(event.getDescription())
                .id(event.getId())
                .creator(event.getUser().getUsername())
                .creatorName(event.getUser().getName())
                .creatorSurname(event.getUser().getSurname())
                .eventTime(event.getEventTime())
                .location(event.getLocation())
                .build();
        LOGGER.info("getEventDetail finished. Result: {}", eventVO);
        return eventVO;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/event/{id}", method = RequestMethod.PUT)
    public void updateEvent(@PathVariable("id") int eventId, @RequestBody EventVO eventVO) {
        LOGGER.info("updateEvent started. Param: id = {}; event = {} ", eventId, eventVO);

        Event event = Event.builder(eventVO.getName()).
                id(eventId).
                description(eventVO.getDescription()).
                country(eventVO.getCountry()).
                city(eventVO.getCity()).
                location(eventVO.getLocation()).
                gpsLatitude(eventVO.getGpsLatitude()).
                gpsLongitude(eventVO.getGpsLongitude()).
                eventTime(eventVO.getEventTime()).build();

        int updatedEntries = eventService.updateEvent(event);
        if (updatedEntries != 1) {
            throw new EventNotUpdatedException("Event with id = " + eventId + " not updated with new fields value: " + eventVO);
        }
        LOGGER.info("updateEvent finished.");
    }

    @RequestMapping(value = "/event/", method = RequestMethod.GET)
    public EventPackVO getEventList(@RequestParam("queryMode") QueryMode queryMode,
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                    @RequestParam("after") LocalDateTime after,
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                    @RequestParam("before") LocalDateTime before) {
        LOGGER.info("getEventList started. Param: after = {}, before = {}, queryMode = {} ", after, before, queryMode);
        List<Event> eventList;
        EventPackVO eventPackVO;
        LocalDateTime effectiveDate;

        switch (queryMode) {
            case BEFORE:
                eventList = eventService.getOrderedEvents(before, queryMode);
                effectiveDate = after;
                break;
            case AFTER:
                eventList = eventService.getOrderedEvents(after, queryMode);
                effectiveDate = eventList.isEmpty() ? after : eventList.get(0).getCreationTime();
                break;
            default:
                throw new IllegalArgumentException("Unsupported query mode");
        }
        eventPackVO = new EventPackVO(eventService.getNumberOfNewEvents(effectiveDate));

        for (Event event : eventList) {
            EventPreviewVO eventPreviewVO = EventPreviewVO.builder(event.getId()).
                    name(event.getName()).
                    creator(event.getUser().getUsername()).
                    description(event.getDescription()).
                    country(event.getCountry()).
                    city(event.getCity()).
                    location(event.getLocation()).
                    numberOfComments(5).
                    picture(new byte[0]).
                    eventTime(event.getEventTime()).
                    creationTime(event.getCreationTime()).build();
            eventPackVO.addEventPreviewVO(eventPreviewVO);
        }

        LOGGER.info("getEventList finished. Resuls: {}", eventPackVO);
        return eventPackVO;
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EventVO addEvent(@RequestBody EventVO eventVO, Principal principal) {
        LOGGER.info("addEvent started. Param: user name = {}; event = {} ", principal.getName(), eventVO);

        Event event = Event.builder(eventVO.getName()).
                description(eventVO.getDescription()).
                country(eventVO.getCountry()).
                city(eventVO.getCity()).
                location(eventVO.getLocation()).
                gpsLatitude(eventVO.getGpsLatitude()).
                gpsLongitude(eventVO.getGpsLongitude()).
                eventTime(eventVO.getEventTime()).
                build();

        Event newEvent = eventService.createEvent(event, principal.getName());

        EventVO newEventVO = EventVO.builder(newEvent.getName())
                .city(newEvent.getCity())
                .country(newEvent.getCountry())
                .description(newEvent.getDescription())
                .id(newEvent.getId())
                .eventTime(newEvent.getEventTime())
                .gpsLatitude(newEvent.getGpsLatitude())
                .gpsLongitude(newEvent.getGpsLongitude())
                .location(newEvent.getLocation())
                .build();

        LOGGER.info("addEvent finished. eventVO = {}", newEventVO);
        return newEventVO;

    }
}
