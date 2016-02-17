package com.epam.eventappweb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * class describes Event pack
 */
public final class EventPackVO {
    private final List<EventPreviewVO> eventPreviewVOList;
    private final int numberOfNewEvents;

    public EventPackVO(int numberOfNewEvents) {
        this.eventPreviewVOList = new ArrayList<>();
        this.numberOfNewEvents = numberOfNewEvents;
    }

    public List<EventPreviewVO> getEventPreviewVOList() {
        return eventPreviewVOList;
    }

    public int getNumberOfNewEvents() {
        return numberOfNewEvents;
    }

    public void addEventPreviewVO(EventPreviewVO eventPreviewVO) {
        eventPreviewVOList.add(eventPreviewVO);
    }

    public String toString() {
        return "EventPackVO{" +
                "eventPreviewVOList=" + eventPreviewVOList +
                ", numberOfNewEvents=" + numberOfNewEvents +
                '}';
    }
}
