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
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * looking for list of comments with eventId = 0. Checking if it is present, event id from comment is equal
     * to expected id and size of comment list is equal to expected size
     */
    @Test
    public void shouldReturnListOfCommentByEventId() {

        //given
        final int id = 0;
        final String firstCommentTime = "2016-01-21 15:00:00";
        final String secondCommentTime = "2016-01-22 15:00:00";
        final String commentTime = "2016-01-23 15:00:00";
        final int commentsAmount = 2;
        final int remainingComments = 3;
        final Timestamp commentTimestamp = Timestamp.valueOf(LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Comment commentFromIvan = Comment.builder().user(User.builder("Ivan", "ivan@gmail.com").build()).message("Great!").
                timeStamp(LocalDateTime.parse(firstCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        Comment commentFromPete = Comment.builder().user(User.builder("Peter", "pete@gmail.com").build()).message("Like it!").
                timeStamp(LocalDateTime.parse(secondCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        List<Comment> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentFromIvan);
        expectedCommentList.add(commentFromPete);
        Optional<List<Comment>> commentList = Optional.of(expectedCommentList);
        when(commentDAOMock.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentTimestamp,
                commentsAmount)).thenReturn(commentList);
        when(commentDAOMock.countOfCommentsAddedBeforeDate(id, Timestamp.valueOf(commentList.get().get(commentList.get().size() - 1).
                getTimeStamp()))).thenReturn(remainingComments);

        //when
        Optional<CommentPack> commentPack = sut.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentTimestamp, commentsAmount);

        //then
        Assert.assertTrue(commentPack.isPresent());
        Assert.assertEquals(id, commentPack.get().getComments().get(0).getEventId());
        Assert.assertEquals(commentsAmount, commentPack.get().getComments().size());
        Assert.assertEquals(remainingComments, commentPack.get().getRemainingComments().intValue());
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
        final int remainingComments = 3;
        final Timestamp commentTimestamp = Timestamp.valueOf(LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Optional<List<Comment>> absentCommentList = Optional.empty();
        when(commentDAOMock.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentTimestamp,
                commentsAmount)).thenReturn(absentCommentList);
        when(commentDAOMock.countOfCommentsAddedBeforeDate(id, commentTimestamp)).thenReturn(remainingComments);

        //when
        Optional<CommentPack> comments = sut.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentTimestamp, commentsAmount);

        //then
        Assert.assertFalse(comments.isPresent());
    }


}
