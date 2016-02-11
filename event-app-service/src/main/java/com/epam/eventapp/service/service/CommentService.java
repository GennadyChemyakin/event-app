package com.epam.eventapp.service.service;


import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.model.CommentPack;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Comment Service
 */
public interface CommentService {

    /**
     * method for getting list of fixed size of amount of comments that were added
     * before after for event by event id
     * @param eventId event id
     * @param before we are looking for comments that were added before this time
     * @param amount amount of comments to receive
     * @return CommentPack with list of founded comments and amount of remaining comments
     */
    CommentPack getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, LocalDateTime before, int amount);


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
     * method for deleting commentary
     * @param comment deleting commentary
     */
    void deleteComment(Comment comment);

}
