package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.Comment;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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
     * @return list of founded comments
     */
    List<Comment> getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, LocalDateTime commentTime, int amount);

    /**
     * method for counting amount of comments that were added before specified time to event
     * @param eventId id of event
     * @param commentTime specified time. We are looking for comments that were added before it
     * @return amount of remaining comments
     */
    int countOfCommentsAddedBeforeDate(int eventId, LocalDateTime commentTime) throws SQLException;
<<<<<<< HEAD

    /**
     * method for adding a commentary
     * @param comment commentary to add
     */
    void addComment(Comment comment);

    /**
     * method for getting list of comments that were added after commentTime to event with id = eventId
     * @param eventId id of event
     * @param commentTime specified time. We are looking for comments that were added after it
     * @return list of founded comments
     */
    List<Comment> getListOfNewComments(int eventId, LocalDateTime commentTime);
=======
>>>>>>> ea-28
}
