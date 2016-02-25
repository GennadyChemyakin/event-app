package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.model.CommentPack;
import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CommentService implementation
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);


    @Autowired
    private CommentDAO commentDAO;


    @Override
    public CommentPack getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, LocalDateTime before, int amount) {
        LOGGER.debug("getCommentsListOfFixedSizeByEventIdBeforeDate started: Params eventId = {}, before = {}, amount = {}", eventId,
                before, amount);
        CommentPack commentPack;
        List<Comment> commentList = commentDAO.getCommentsListOfFixedSizeByEventIdBeforeDate(eventId, before, amount);
        int commentListSize = commentList.size();
        if (commentListSize > 0) {
            int remainingCommentsCount = commentDAO.countCommentsAddedBeforeOrAfterDate(eventId,
                    commentList.get(commentListSize - 1).getCommentTime(), QueryMode.BEFORE);

            commentPack = new CommentPack(commentList, remainingCommentsCount);
        } else {
            commentPack = new CommentPack(commentList, 0);
        }
        LOGGER.debug("getCommentsListOfFixedSizeByEventIdBeforeDate finished. Result: {}", commentPack);
        return commentPack;
    }

    @Override
    public void addComment(Comment comment) {
        LOGGER.debug("addComment started. Params: comment = {}", comment);
        commentDAO.addComment(comment);
        LOGGER.debug("addComment finished.");
    }

    @Override
    public List<Comment> getListOfNewComments(int eventId, LocalDateTime after) {
        LOGGER.debug("getListOfNewComments started: Params eventId = {}, after = {}", eventId,
                after);
        List<Comment> commentList = commentDAO.getListOfNewComments(eventId, after);
        LOGGER.debug("getListOfNewComments finished. Result: {}", commentList);
        return commentList;
    }

    @Override
    @PreAuthorize("#comment.getUser().getUsername() == authentication.name || " +
            "@eventService.findById(#comment.getEventId()).get().getUser().getUsername() == authentication.name")
    public void deleteComment(Comment comment) {
        LOGGER.debug("deleteComment started. Params: comment = {}", comment);
        commentDAO.deleteCommentById(comment.getId());
        LOGGER.debug("deleteComment finished.");
    }

    @Override
    public int countCommentsAddedBeforeOrAfterDate(int eventId, LocalDateTime commentTime, QueryMode queryMode) {
        LOGGER.debug("countCommentsAddedBeforeOrAfterDate started. Params: eventId = {}, commentTime = {}, queryMode = {}",
                eventId, commentTime, queryMode);
        int count = commentDAO.countCommentsAddedBeforeOrAfterDate(eventId, commentTime, queryMode);
        LOGGER.debug("countCommentsAddedBeforeOrAfterDate finished. Result: {}", count);
        return count;
    }
}
