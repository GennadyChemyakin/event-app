package com.epam.eventapp.service.model;

import com.epam.eventapp.service.domain.Event;

import java.util.List;

/**
 * Class for representing package of Events
 */
public class EventPack {
    private List<Event> events;
    private int numberOfAllEvents;

    public EventPack(List<Event> events, int numberOfAllEvents) {
        this.events = events;
        this.numberOfAllEvents = numberOfAllEvents;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getNumberOfAllEvents() {
        return numberOfAllEvents;
    }

    public void setNumberOfAllEvents(int numberOfAllEvents) {
        this.numberOfAllEvents = numberOfAllEvents;
    }

    @Override
    public String toString() {
        return "EventPack{events=" + events + ", numberOfAllEvents=" + numberOfAllEvents + "}";
    }
}
