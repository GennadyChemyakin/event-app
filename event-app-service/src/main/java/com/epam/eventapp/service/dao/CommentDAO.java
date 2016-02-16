package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.Comment;
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
     * method for counting amount of comments that were added before specified time to event
     * @param eventId id of event
     * @param before specified time. We are looking for comments that were added before it
     * @return amount of remaining comments
     */
    int countOfCommentsAddedBeforeDate(int eventId, LocalDateTime before);

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
}
