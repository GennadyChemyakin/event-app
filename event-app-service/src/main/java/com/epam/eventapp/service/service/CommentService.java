package com.epam.eventapp.service.service;

import com.epam.eventapp.service.domain.Comment;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
     * @return Optional with list in case comments were found and otherwise Optional.empty()
     */
    Optional<List<Comment>> getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, Timestamp commentTime, int amount);
}
