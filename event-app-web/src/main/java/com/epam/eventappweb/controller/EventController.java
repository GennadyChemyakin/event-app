package com.epam.eventappweb.controller;

import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.service.EventService;
import com.epam.eventappweb.exceptions.EventNotFoundException;
import com.epam.eventappweb.exceptions.EventNotUpdatedException;
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
import java.time.LocalDateTime;
import java.util.LinkedList;
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
                .city(event.getCity().orElse(null))
                .country(event.getCountry().orElse(null))
                .description(event.getDescription().orElse(null))
                .id(event.getId())
                .creator(event.getUser().getUsername())
                .creatorName(event.getUser().getName().orElse(null))
                .creatorSurname(event.getUser().getSurname().orElse(null))
                .eventTime(event.getEventTime().orElse(null))
                .location(event.getLocation().orElse(null))
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
                description(eventVO.getDescription().orElse(null)).
                country(eventVO.getCountry().orElse(null)).
                city(eventVO.getCity().orElse(null)).
                location(eventVO.getLocation().orElse(null)).
                gpsLatitude(eventVO.getGpsLatitude()).
                gpsLongitude(eventVO.getGpsLongitude()).
                eventTime(eventVO.getEventTime().orElse(null)).build();

        int updatedEntries = eventService.updateEvent(event);
        if (updatedEntries != 1) {
            throw new EventNotUpdatedException("Event with id = " + eventId + " not updated with new fields value: " + eventVO);
        }
        LOGGER.info("updateEvent finished.");
    }

    @RequestMapping(value = "/event/", method = RequestMethod.GET)
    public List<EventPreviewVO> getEventList(@RequestParam("queryMode") QueryMode queryMode,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                             @RequestParam("time") LocalDateTime effectiveTime) {
        LOGGER.info("getEventList started. Param: effectiveTime = {},queryMode = {} ", effectiveTime, queryMode);
        List<EventPreviewVO> eventPreviewVOList = new LinkedList<>();
        List<Event> eventList = eventService.getOrderedEvents(effectiveTime, queryMode);
        for (Event event : eventList) {
            EventPreviewVO eventPreviewVO = EventPreviewVO.builder(event.getId()).
                    name(event.getName()).
                    creator(event.getUser().getUsername()).
                    description(event.getDescription().orElse(null)).
                    country(event.getCountry().orElse(null)).
                    city(event.getCity().orElse(null)).
                    location(event.getLocation().orElse(null)).
                    numberOfComments(5).
                    picture(new byte[0]).
                    eventTime(event.getEventTime().orElse(null)).
                    creationTime(event.getCreationTime()).build();
            eventPreviewVOList.add(eventPreviewVO);
        }
        LOGGER.info("getEventList finished. Resuls: {}", eventPreviewVOList);
        return eventPreviewVOList;
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EventVO addEvent(@RequestBody EventVO eventVO, Principal principal) {
        LOGGER.info("addEvent started. Param: user name = {}; event = {} ", principal.getName(), eventVO);

        Event event = Event.builder(eventVO.getName()).
                description(eventVO.getDescription().orElse(null)).
                country(eventVO.getCountry().orElse(null)).
                city(eventVO.getCity().orElse(null)).
                location(eventVO.getLocation().orElse(null)).
                gpsLatitude(eventVO.getGpsLatitude()).
                gpsLongitude(eventVO.getGpsLongitude()).
                eventTime(eventVO.getEventTime().orElse(null)).
                build();

        Event newEvent = eventService.createEvent(event, principal.getName());

        EventVO newEventVO = EventVO.builder(newEvent.getName())
                .city(newEvent.getCity().orElse(null))
                .country(newEvent.getCountry().orElse(null))
                .description(newEvent.getDescription().orElse(null))
                .id(newEvent.getId())
                .eventTime(newEvent.getEventTime().orElse(null))
                .gpsLatitude(newEvent.getGpsLatitude())
                .gpsLongitude(newEvent.getGpsLongitude())
                .location(newEvent.getLocation().orElse(null))
                .build();

        LOGGER.info("addEvent finished. eventVO = {}", newEventVO);
        return newEventVO;
    }

    @RequestMapping(value = "/event/count", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public int countNumberOfNewEvents(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                      @RequestParam("after") LocalDateTime after) {
        LOGGER.info("countNumberOfNewEvents started. Param: after = {} ", after);
        int numberOfNewEvents = eventService.getNumberOfNewEvents(after);
        LOGGER.info("countNumberOfNewEvents finished. numberOfNewEvents = {}", numberOfNewEvents);
        return numberOfNewEvents;
    }
}
