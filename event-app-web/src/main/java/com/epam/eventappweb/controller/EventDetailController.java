package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.service.EventService;
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

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEventDetail(@PathVariable("id") int eventId) {
        Optional<Event> event = eventService.findById(eventId);
        return event.isPresent() ? new ResponseEntity<>(event.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateUser(@PathVariable("id") long id, @RequestBody Event event) {
        int updatedEntries = eventService.updateEvent(event);
        return updatedEntries == 1 ? new ResponseEntity<>(event, HttpStatus.OK) : new ResponseEntity<>(event, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
