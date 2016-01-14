package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.EventService;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.EventPageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Rest controller. Return ResponseEntity with event as body and HttpStatus code OK if we've found event by id,
 * otherwise return ResponseEntity with HttpStatus code 404
 */
@RestController
public class EventDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDetailController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEventDetail(@PathVariable("id") int eventId) {
        LOGGER.info("getEventDetail started. Param: id = {} ", eventId);
        Optional<Event> event = eventService.findById(eventId);
        ResponseEntity<Event> resultResponseEntity = event.isPresent() ? new ResponseEntity<>(event.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        /*
        EventPageModel eventModel = EventPageModel.builder(event.getName()).
                username(event.getUser().getUsername().
                description(event.getDescription()).
                country(event.getCountry()).
                city(event.getCity()).
                location(event.getLocation()).
                gpsLatitude(event.getGpsLatitude()).
                gpsLongitude(event.getGpsLongitude()).
                timeStamp(event.getTimeStamp()).
                build();
         */
        LOGGER.info("getEventDetail finished. Result:"
                + " Status code: {}; Body: {}", resultResponseEntity.getStatusCode(), event);
        return resultResponseEntity;
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@PathVariable("id") int eventId, @RequestBody EventPageModel eventPageModel) {
        LOGGER.info("updateEvent started. Param: id = {}; event = {} ", eventId, eventPageModel);
        ResponseEntity<Event> resultResponseEntity;
        Optional<User> user = userService.findByUsername(eventPageModel.getUsername());
        if(user.isPresent()) {
            Event event = Event.builder(user.get(), eventPageModel.getName()).
                    id(eventId).
                    description(eventPageModel.getDescription()).
                    country(eventPageModel.getCountry()).
                    city(eventPageModel.getCity()).
                    location(eventPageModel.getLocation()).
                    gpsLatitude(eventPageModel.getGpsLatitude()).
                    gpsLongitude(eventPageModel.getGpsLongitude()).
                    timeStamp(eventPageModel.getTimeStamp()).build();

            int updatedEntries = eventService.updateEvent(event);
            resultResponseEntity = updatedEntries == 1 ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            resultResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOGGER.info("updateEvent finished. Result: Status code: {}", resultResponseEntity.getStatusCode());
        return resultResponseEntity;
    }
}
