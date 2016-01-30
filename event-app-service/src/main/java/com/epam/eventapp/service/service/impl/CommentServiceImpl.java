package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.model.CommentPack;
import com.epam.eventapp.service.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
    public CommentPack getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, LocalDateTime commentTime, int amount) throws SQLException {
        LOGGER.debug("getCommentsListOfFixedSizeByEventIdBeforeDate started: Params eventId = {}, commentTime = {}, amount = {}", eventId,
                commentTime, amount);
        CommentPack commentPack;
        List<Comment> commentList = commentDAO.getCommentsListOfFixedSizeByEventIdBeforeDate(eventId, commentTime, amount);
        int commentListSize = commentList.size();
        if (commentListSize > 0) {
            int remainingCommentsCount = commentDAO.countOfCommentsAddedBeforeDate(eventId,
                    commentList.get(commentListSize - 1).getCommentTime());

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
    public CommentPack getListOfNewComments(int eventId, LocalDateTime commentTime) {
        LOGGER.debug("getListOfNewComments started: Params eventId = {}, commentTime = {}", eventId,
                commentTime);
        CommentPack commentPack;
        List<Comment> commentList = commentDAO.getListOfNewComments(eventId, commentTime);
        commentPack = new CommentPack(commentList, 0);
        LOGGER.debug("getListOfNewComments finished. Result: {}", commentPack);
        return commentPack;
    }
}
