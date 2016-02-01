package com.epam.eventapp.service.service;


import com.epam.eventapp.service.model.CommentPack;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Comment Service
 */
public interface CommentService {

    /**
     * method for getting list of fixed size of amount of comments that were added
     * before commentTime for event by event id
     * @param eventId event id
     * @param commentTime time when comment was added
     * @param amount amount of comments to receive
     * @return CommentPack with list of founded comments and amount of remaining comments
     */
    CommentPack getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, LocalDateTime commentTime, int amount) throws SQLException;
}
