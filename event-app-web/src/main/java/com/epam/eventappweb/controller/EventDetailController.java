package com.epam.eventappweb.controller;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Rest controller. Return ResponseEntity with event as body and HttpStatus code OK if we've found event by id,
 * otherwise return ResponseEntity with HttpStatus code 404
 */
@RestController
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventDetailController {


    @Autowired
    private EventDAO eventDAO;

//    @Autowired
//    public EventDetailController(EventDAO eventDAO) {
//        this.eventDAO = eventDAO;
//    }

    @RequestMapping("/event/{id}")
    public ResponseEntity<Event> getEventDetail(@PathVariable("id") int eventId) {
        Optional<Event> event = eventDAO.findById(eventId);
        return event.isPresent() ? new ResponseEntity<>(event.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
