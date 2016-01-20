package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.service.CommentService;
import com.epam.eventappweb.model.CommentView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Comment controller
 */
@RestController
public class CommentController {

    public final static int COMMENTS_AMOUNT = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/commentList/{eventId}/{offset}", method = RequestMethod.GET)
    public ResponseEntity<List<CommentView>> getCommentList(@PathVariable("eventId") int eventId, @PathVariable("offset") int offset) {
        LOGGER.info("getCommentList started. Param: eventId = {}, offset = {} ", eventId, offset);

        Optional<List<Comment>> commentList = commentService.getCommentsListByEventId(eventId, offset, COMMENTS_AMOUNT);
        ResponseEntity<List<CommentView>> resultResponseEntity;
        List<CommentView> commentViews = new ArrayList<>();
        if (commentList.isPresent()) {
            for (Comment comment : commentList.get()) {
                CommentView commentView = CommentView.builder().id(comment.getId()).message(comment.getMessage()).
                        eventId(comment.getEventId()).username(comment.getUser().getUsername()).
                        userPhoto(comment.getUser().getPhoto()).timeStamp(comment.getTimeStamp()).build();
                commentViews.add(commentView);
            }
            resultResponseEntity = new ResponseEntity<>(commentViews, HttpStatus.OK);
        } else {
            resultResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("getCommentList finished. Result:"
                + " Status code: {}; Body: {}", resultResponseEntity.getStatusCode(), commentViews);
        return resultResponseEntity;
    }
}
