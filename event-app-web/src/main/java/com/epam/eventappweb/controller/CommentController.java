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
     * before specified <before> time for event by event id
     *
     * @param eventId id of event
     * @param before  we are looking for comments that were added before this time
     * @return pack of comments
     */
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public CommentPackVO getCommentList(@RequestParam("eventId") int eventId,
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        @RequestParam("before") LocalDateTime before) {

        LOGGER.info("getCommentList started. Param: eventId = {}, before = {} ", eventId, before);

        CommentPack commentPack = commentService.getCommentsListOfFixedSizeByEventIdBeforeDate(eventId, before, COMMENTS_AMOUNT);
        CommentPackVO commentPackVO;
        List<CommentVO>  commentViews = commentPack.getComments().stream().map(comment -> CommentVO.builder().id(comment.getId()).message(comment.getMessage()).
                eventId(comment.getEventId()).username(comment.getUser().getUsername()).
                userPhoto(comment.getUser().getPhoto()).commentTime(comment.getCommentTime()).build()).collect(Collectors.toList());

        commentPackVO = new CommentPackVO(commentViews, commentPack.getRemainingCommentsCount());
        LOGGER.info("getCommentList finished. Result: ", commentPackVO);
        return commentPackVO;
    }

    /**
     * method for adding new commentary
     *
     * @param commentVO new commentary
     * @param principal principle of logged user
     * @return status code 201 in case of success
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public void addComment(@RequestBody CommentVO commentVO, Principal principal) {

        LOGGER.info("addComment started. Param: commentVO = {}, principal = {}", commentVO, principal);
        User user = userService.getUserByUsername(principal.getName());
        Comment addedComment = Comment.builder().eventId(commentVO.getEventId()).message(commentVO.getMessage()).
                commentTime(commentVO.getCommentTime()).user(user).build();
        commentService.addComment(addedComment);
        LOGGER.info("addComment finished.");
    }


    /**
     * method for getting list of new comments, that were added after specified date after
     *
     * @param eventId id of commented event
     * @param after   specified date
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
        LOGGER.info("showNewComments finished. Result:", newCommentsVO);
        return newCommentsVO;
    }

    /**
     * method for deleting commentary
     *
     * @param commentVO deleting commentary
     * @return status code 204 if commentary deleted
     */
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/comment", method = RequestMethod.DELETE)
    public void deleteCommentary(@RequestBody CommentVO commentVO) {
        LOGGER.info("deleteCommentary started. Param: commentVO = {}", commentVO);
        User user = User.builder().username(commentVO.getUsername()).build();
        Comment comment = Comment.builder().eventId(commentVO.getEventId()).message(commentVO.getMessage()).id(commentVO.getId()).
                commentTime(commentVO.getCommentTime()).user(user).build();
        commentService.deleteComment(comment);
        LOGGER.info("deleteCommentary finished.");
    }

}
