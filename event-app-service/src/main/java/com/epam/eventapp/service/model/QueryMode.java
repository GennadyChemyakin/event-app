package com.epam.eventapp.service.model;

import java.time.LocalDateTime;

/**
 * Enum that specifies <BEFORE> of <AFTER> query Mode for getting Events list.
 * See also {@link com.epam.eventapp.service.dao.EventDAO#getOrderedEvents(LocalDateTime, int, QueryMode)}
 */
public enum QueryMode { BEFORE, AFTER }
