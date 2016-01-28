package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.model.EventPack;
import com.epam.eventapp.service.service.EventService;
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

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Rest controller. Return ResponseEntity with event as body and HttpStatus code OK if we've found event by id,
 * otherwise return ResponseEntity with HttpStatus code 404
 */
@RestController
public class EventDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDetailController.class);
    private static final int EVENTS_AMOUNT = 2;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEventDetail(@PathVariable("id") int eventId) {
        LOGGER.info("getEventDetail started. Param: id = {} ", eventId);
        Optional<Event> event = eventService.findById(eventId);
        ResponseEntity<Event> resultResponseEntity = event.isPresent() ? new ResponseEntity<>(event.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        LOGGER.info("getEventDetail finished. Result:"
                + " Status code: {}; Body: {}", resultResponseEntity.getStatusCode(), event);
        return resultResponseEntity;
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@PathVariable("id") int eventId, @RequestBody EventVO eventVO) {
        LOGGER.info("updateEvent started. Param: id = {}; event = {} ", eventId, eventVO);
        ResponseEntity<Event> resultResponseEntity;

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
        resultResponseEntity = updatedEntries == 1 ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

        LOGGER.info("updateEvent finished. Result: Status code: {}", resultResponseEntity.getStatusCode());
        return resultResponseEntity;
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<EventPackVO> getEventList(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                         @RequestParam("creationTime") LocalDateTime creationTime) throws SQLException {
        LOGGER.info("getEventList started. Param: eventTime = {}; events_amount = {} ", creationTime, EVENTS_AMOUNT);
        EventPack eventPack = eventService.getEventListFixedSizeBeforeTimeOrderedByTimeDesc(creationTime, EVENTS_AMOUNT);
        ResponseEntity<EventPackVO> resultResponseEntity;
        EventPackVO eventPackVO = new EventPackVO(eventPack.getNumberOfAllEvents());
        for(Event event: eventPack.getEvents()) {
            EventPreviewVO eventPreviewVO = EventPreviewVO.builder(event.getName()).
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
        resultResponseEntity = new ResponseEntity<EventPackVO>(eventPackVO, HttpStatus.OK);
        LOGGER.info("getEventList finished. Result: Status code: {}", resultResponseEntity.getStatusCode());
        return resultResponseEntity;
    }
}
