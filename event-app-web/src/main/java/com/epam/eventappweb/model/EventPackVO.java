package com.epam.eventappweb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * class describes Event pack
 */
public final class EventPackVO {
    private final List<EventPreviewVO> eventPreviewVOList;

    public EventPackVO() {
        this.eventPreviewVOList = new ArrayList<>();

    }

    public List<EventPreviewVO> getEventPreviewVOList() {
        return eventPreviewVOList;
    }

    public void addEventPreviewVO(EventPreviewVO eventPreviewVO) {
        eventPreviewVOList.add(eventPreviewVO);
    }

    public String toString() {
        return "EventPackVO{" +
                "eventPreviewVOList=" + eventPreviewVOList +
                '}';
    }
}
