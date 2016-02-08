package com.epam.eventapp.service.model;

import com.epam.eventapp.service.domain.Event;

import java.util.List;

/**
 * Class for representing page of Events
 */
public class EventPack {
    private List<Event> events;
    private int numberOfNewEvents;

    public EventPack(List<Event> events, int numberOfNewEvents) {
        this.events = events;
        this.numberOfNewEvents = numberOfNewEvents;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getNumberOfNewEvents() {
        return numberOfNewEvents;
    }

    @Override
    public String toString() {
        return "EventPack{events=" + events + ", numberOfNewEvents=" + numberOfNewEvents + "}";
    }
}
