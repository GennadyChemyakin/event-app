package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.model.CommentPack;
import com.epam.eventapp.service.service.CommentService;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.CommentPackVO;
import com.epam.eventappweb.model.CommentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * method for getting list of of comments that were added
     * before commentTime for event by event id
     * @param eventId id of event
     * @param before we are looking for comments that were added before this time
     * @return pack of comments
     */
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public ResponseEntity<CommentPackVO> getCommentList(@RequestParam("eventId") int eventId,
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        @RequestParam("before") LocalDateTime before) {

        LOGGER.info("getCommentList started. Param: eventId = {}, before = {} ", eventId, before);

        CommentPack commentPack = commentService.getCommentsListOfFixedSizeByEventIdBeforeDate(eventId, before, COMMENTS_AMOUNT);
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

    /**
     * method for adding new commentary
     * @param commentVO new commentary
     * @param principal principle of logged user
     * @return status code 200 in case of success
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity addComment(@RequestBody CommentVO commentVO, Principal principal) {

        LOGGER.info("addComment started. Param: commentVO = {}, principal = {}", commentVO, principal);
        ResponseEntity resultResponseEntity;
        User user = userService.getUserByUsername(principal.getName());
        Comment addedComment = Comment.builder().eventId(commentVO.getEventId()).message(commentVO.getMessage()).
                commentTime(commentVO.getCommentTime()).user(user).build();
        commentService.addComment(addedComment);
        resultResponseEntity = new ResponseEntity<>(HttpStatus.OK);
        LOGGER.info("addComment finished. Result: OK");
        return resultResponseEntity;
    }


    /**
     * method for getting list of new comments, that were added after specified date after
     * @param eventId id of commented event
     * @param after specified date
     * @return list of new comments
     */
    @RequestMapping(value = "/comment/new", method = RequestMethod.GET)
    public List<CommentVO> showNewComments(@RequestParam("eventId") int eventId,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                           @RequestParam("after") LocalDateTime after) {

        LOGGER.info("showNewComments started. Param: eventId = {}, after = {}", eventId, after);
        List<Comment> newComments = commentService.getListOfNewComments(eventId, after);
        List<CommentVO> newCommentsVO = newComments.stream().map(comment -> CommentVO.builder().id(comment.getId()).eventId(comment.getEventId()).
                username(comment.getUser().getUsername()).message(comment.getMessage()).
                commentTime(comment.getCommentTime()).userPhoto(comment.getUser().getPhoto()).build()).collect(Collectors.toList());
        LOGGER.info("addComment finished. Result:", newCommentsVO);
        return newCommentsVO;
    }

}
