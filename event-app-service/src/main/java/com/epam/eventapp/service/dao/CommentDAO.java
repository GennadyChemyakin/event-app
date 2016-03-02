package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.model.QueryMode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO for commentary
 */
public interface CommentDAO {

    /**
     * method for getting list of fixed size of amount of comments that were added
     * before specified date for event by event id
     * @param eventId event id
     * @param before we are looking for comments that were added before this time
     * @param amount amount of comments to receive
     * @return list of founded comments
     */
    List<Comment> getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, LocalDateTime before, int amount);

    /**
     * method for counting amount of comments that were added before or after specified time <commentTime> to event
     * @param eventId id of event
     * @param commentTime specified time. We are looking for comments that were added before or after it
     * @return amount of comments
     */
    int countCommentsAddedBeforeOrAfterDate(int eventId, LocalDateTime commentTime, QueryMode queryMode);

    /**
     * method for adding a commentary
     * @param comment commentary to add
     */
    void addComment(Comment comment);

    /**
     * method for getting list of comments that were added after after to event with id = eventId
     * @param eventId id of event
     * @param after specified time. We are looking for comments that were added after it
     * @return list of founded comments
     */
    List<Comment> getListOfNewComments(int eventId, LocalDateTime after);

    /**
     * method for deleting commentary by id
     * @param id id of deleting commentary
     */
    void deleteCommentById(int id);
}
