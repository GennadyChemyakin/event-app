package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.model.CommentPack;
import com.epam.eventapp.service.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * CommentService implementation
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);


    @Autowired
    private CommentDAO commentDAO;

    @Override
    public Optional<CommentPack> getCommentsListOfFixedSizeByEventIdBeforeDate(int eventId, Timestamp commentTime, int amount) {
        LOGGER.debug("getCommentsListOfFixedSizeByEventIdBeforeDate started: Params eventId = {}, commentTime = {}, amount = {}", eventId,
                commentTime, amount);
        Optional<CommentPack> commentPack;
        Optional<List<Comment>> commentList = commentDAO.getCommentsListOfFixedSizeByEventIdBeforeDate(eventId, commentTime, amount);
        if (commentList.isPresent()) {
            int commentListSize = commentList.get().size();
            Integer remainingComments = commentDAO.countOfCommentsAddedBeforeDate(eventId,
                    Timestamp.valueOf(commentList.get().get(commentListSize - 1).getTimeStamp()));
            commentPack = Optional.of(new CommentPack(commentList.get(), remainingComments));
        } else {
            commentPack = Optional.empty();
        }
        LOGGER.debug("getCommentsListOfFixedSizeByEventIdBeforeDate finished. Result: {}", commentPack);
        return commentPack;
    }
}
