package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<List<Comment>> getCommentsListByEventId(int eventId, int offset, int amount) {
        LOGGER.debug("getCommentsListByEventId started: Params eventId = {}, offset = {}, amount = {}", eventId,
                offset, amount);
        Optional<List<Comment>> commentList = commentDAO.getCommentsListByEventId(eventId, offset, amount);
        LOGGER.debug("getCommentsListByEventId finished. Result: {}", commentList);
        return commentList;
    }
}
