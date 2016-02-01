package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.model.CommentPack;
import com.epam.eventapp.service.service.CommentService;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.exceptions.UserNotLoggedInException;
import com.epam.eventappweb.model.CommentPackVO;
import com.epam.eventappweb.model.CommentVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Comment controller
 */
@RestController
public class CommentController {

    public final static int COMMENTS_AMOUNT = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public ResponseEntity<CommentPackVO> getCommentList(@RequestParam("eventId") int eventId,
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        @RequestParam("commentTime") LocalDateTime commentTime) throws SQLException {

        LOGGER.info("getCommentList started. Param: eventId = {}, commentTime = {} ", eventId, commentTime);

        CommentPack commentPack = commentService.getCommentsListOfFixedSizeByEventIdBeforeDate(eventId, commentTime, COMMENTS_AMOUNT);
        ResponseEntity<CommentPackVO> resultResponseEntity;
        CommentPackVO commentPackVO;
        List<CommentVO> commentViews = new ArrayList<>();
        if (commentPack.getComments().size() > 0) {
            for (Comment comment : commentPack.getComments()) {
                CommentVO commentView = CommentVO.builder().id(comment.getId()).message(comment.getMessage()).
                        eventId(comment.getEventId()).username(comment.getUser().getUsername()).
                        userPhoto(comment.getUser().getPhoto()).commentTime(comment.getCommentTime()).build();
                commentViews.add(commentView);
            }
        }
        commentPackVO = new CommentPackVO(commentViews, commentPack.getRemainingCommentsCount());
        resultResponseEntity = new ResponseEntity<>(commentPackVO, HttpStatus.OK);

        LOGGER.info("getCommentList finished. Result:"
                + " Status code: {}; Body: {}", resultResponseEntity.getStatusCode(), commentPackVO);
        return resultResponseEntity;
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity<List<CommentVO>> addComment(@RequestBody CommentVO commentVO,
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                    @RequestParam("after") LocalDateTime after, Principal principal) {

        LOGGER.info("addComment started. Param: commentVO = {}, after = {}, principal = {}", commentVO, after, principal);
        if(principal == null)
            throw new UserNotLoggedInException("user tries to add a comment without logging in");
        ResponseEntity<List<CommentVO>> resultResponseEntity;
        User user = userService.getUserByUsername(principal.getName());
        Comment addedComment = Comment.builder().eventId(commentVO.getEventId()).message(commentVO.getMessage()).
                commentTime(commentVO.getCommentTime()).user(user).build();
        commentService.addComment(addedComment);
        List<Comment> commentList = commentService.getListOfNewComments(commentVO.getEventId(), after);
        List<CommentVO> commentViews = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVO commentView = CommentVO.builder().id(comment.getId()).message(comment.getMessage()).
                    eventId(comment.getEventId()).username(comment.getUser().getUsername()).
                    userPhoto(comment.getUser().getPhoto()).commentTime(comment.getCommentTime()).build();
            commentViews.add(commentView);
        }
        resultResponseEntity = new ResponseEntity<>(commentViews, HttpStatus.OK);
        LOGGER.info("addComment finished. Result:"
                + " Status code: {}; Body: {}", resultResponseEntity.getStatusCode(), commentViews);
        return resultResponseEntity;
    }
}
