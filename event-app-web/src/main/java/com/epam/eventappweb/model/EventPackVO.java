package com.epam.eventappweb.model;

import java.util.List;

/**
 * class describes Event pack
 */
public class EventPackVO {
    private final List<EventPreviewVO> eventPreviewVOList;
    private final int numberOfEvents;

    public EventPackVO(List<EventPreviewVO> eventPreviewVOList, int numberOfEvents) {
        this.eventPreviewVOList = eventPreviewVOList;
        this.numberOfEvents = numberOfEvents;
    }

    public List<EventPreviewVO> getEventPreviewVOList() {
        return eventPreviewVOList;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    public void addEventPreviewVO(EventPreviewVO eventPreviewVO) {
        eventPreviewVOList.add(eventPreviewVO);
    }

    public String toString() {
        return "EventPackVO{" +
                "eventPreviewVOList=" + eventPreviewVOList +
                ", numberOfEvents=" + numberOfEvents +
                '}';
    }
}
