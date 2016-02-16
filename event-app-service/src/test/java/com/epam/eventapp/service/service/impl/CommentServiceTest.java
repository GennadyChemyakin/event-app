package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.model.CommentPack;
import com.epam.eventapp.service.service.CommentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * test class for CommentService
 */
public class CommentServiceTest {

    @Mock
    private CommentDAO commentDAOMock;

    @InjectMocks
    private CommentService sut;

    @Before
    public void setUp() {
        sut = new CommentServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * testing getCommentsListOfFixedSizeByEventIdBeforeDate method from CommentServiceImpl.
     * looking for list of comments with eventId = 0. Checking if event id from comment is equal
     * to expected id and size of comment list is equal to expected size
     */
    @Test
    public void shouldReturnListOfCommentByEventId() {

        //given
        final int id = 0;
        final String commentTime = "2016-01-23 15:00:00";
        final int commentsAmount = 2;
        final int remainingComments = 3;
        final LocalDateTime commentDateTime = LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Comment> expectedCommentList = getExpectedCommentList();

        when(commentDAOMock.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentDateTime,
                commentsAmount)).thenReturn(expectedCommentList);
        when(commentDAOMock.countOfCommentsAddedBeforeDate(id, expectedCommentList.get(expectedCommentList.size() - 1).
                getCommentTime())).thenReturn(remainingComments);


        //when
        CommentPack commentPack = sut.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentDateTime, commentsAmount);

        //then
        Assert.assertEquals(id, commentPack.getComments().get(0).getEventId());
        Assert.assertEquals(commentsAmount, commentPack.getComments().size());
        Assert.assertEquals(remainingComments, commentPack.getRemainingCommentsCount());
    }

    /**
     * method for test data preparation
     *
     * @return
     */
    private List<Comment> getExpectedCommentList() {
        final String firstCommentTime = "2016-01-21 15:00:00";
        final String secondCommentTime = "2016-01-22 15:00:00";
        Comment commentFromIvan = Comment.builder().user(User.builder("Ivan", "ivan@gmail.com").build()).message("Great!").
                commentTime(LocalDateTime.parse(firstCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        Comment commentFromPete = Comment.builder().user(User.builder("Peter", "pete@gmail.com").build()).message("Like it!").
                commentTime(LocalDateTime.parse(secondCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        List<Comment> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentFromIvan);
        expectedCommentList.add(commentFromPete);
        return expectedCommentList;
    }


    /**
     * testing getCommentsListOfFixedSizeByEventIdBeforeDate method from CommentServiceImpl.
     * expect that comment list would be empty
     */
    @Test
    public void shouldReturnAbsentInCaseEventWithNoCommentsIdSpecified() {
        //given
        final int id = 1;
        final String commentTime = "2016-01-21 15:00:00";
        final int commentsAmount = 2;
        final int remainingComments = 0;
        final LocalDateTime commentDateTime = LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Comment> absentCommentList = new ArrayList<>();
        when(commentDAOMock.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentDateTime,
                commentsAmount)).thenReturn(absentCommentList);

        //when
        CommentPack commentPack = sut.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentDateTime, commentsAmount);

        //then
        Assert.assertEquals(commentPack.getComments().size(), 0);
        Assert.assertEquals(remainingComments, commentPack.getRemainingCommentsCount());
    }

    /**
     * testing addComment method from CommentServiceImpl
     * expect no exception
     */
    @Test
    public void shouldAddComment() {
        //given
        final int userId = 0;
        final int eventId = 0;
        final String username = "username";
        final String email = "user@gmail.com";
        final String message = "It was cool!";
        final String commentTimeString = "2016-01-28 23:19:20.111";
        final LocalDateTime commentTime = LocalDateTime.parse(commentTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        Comment comment = Comment.builder().user(User.builder(username, email).id(userId).build()).eventId(eventId).
                message(message).commentTime(commentTime).build();
        Mockito.doNothing().when(commentDAOMock).addComment(comment);

        //when
        sut.addComment(comment);
    }

    /**
     * testing getListOfNewComments method from CommentServiceImpl.
     * looking for list of comments with eventId = 0. Checking if event id from comment is equal
     * to expected id and size of comment list is equal to expected size
     */
    @Test
    public void shouldReturnListOfNewComments() {
        //given
        final int id = 0;

        final String commentTime = "2016-01-19 15:00:00";
        final LocalDateTime commentDateTime = LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Comment> expectedCommentList = getExpectedCommentList();
        final int commentsAmount = expectedCommentList.size();
        when(commentDAOMock.getListOfNewComments(id, commentDateTime)).thenReturn(expectedCommentList);


        //when
        List<Comment> commentList = sut.getListOfNewComments(id, commentDateTime);

        //then
        Assert.assertEquals(commentList.size(), commentsAmount);
        Assert.assertEquals(commentList.get(0).getEventId(), id);
    }

    /**
     * testing getListOfNewComments method from CommentServiceImpl.
     * expect that comment list would be empty
     */
    @Test
    public void shouldReturnEmptyListInCaseNoNewComments() {
        //given
        final int id = 1;
        final String commentTime = "2016-01-21 15:00:00";
        final LocalDateTime commentDateTime = LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Comment> absentCommentList = new ArrayList<>();
        when(commentDAOMock.getListOfNewComments(id, commentDateTime)).thenReturn(absentCommentList);

        //when
        List<Comment> commentList = sut.getListOfNewComments(id, commentDateTime);

        //then
        Assert.assertTrue(commentList.isEmpty());
    }
}
