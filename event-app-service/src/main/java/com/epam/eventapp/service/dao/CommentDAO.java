package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.Comment;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * DAO for commentary
 */
public interface CommentDAO {

    /**
     * method for getting list of fixed size of amount of comments that were added
     * before commentTime for event by event id
     * @param eventId event id
     * @param commentTime time when comment was added
     * @param amount amount of comments to receive
     * @return Optional with list in case comments were found and otherwise Optional.empty()
     */
    Optional<List<Comment>> getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, Timestamp commentTime, int amount);

    /**
     * method for counting amount of comments that were added before specified time to event
     * @param eventId id of event
     * @param commentTime specified time. We are looking for comments that were added before it
     * @return amount of remaining comments
     */
    Integer countOfCommentsAddedBeforeDate(int eventId, Timestamp commentTime);
}
