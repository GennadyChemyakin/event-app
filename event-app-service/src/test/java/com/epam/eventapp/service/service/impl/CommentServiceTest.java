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

import java.sql.SQLException;
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
    public void shouldReturnListOfCommentByEventId() throws SQLException {

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
                getTimeStamp())).thenReturn(remainingComments);

        //when
        CommentPack commentPack = sut.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentDateTime, commentsAmount);

        //then
        Assert.assertEquals(id, commentPack.getComments().get(0).getEventId());
        Assert.assertEquals(commentsAmount, commentPack.getComments().size());
        Assert.assertEquals(remainingComments, commentPack.getRemainingCommentsCount());
    }

    /**
     * method for test data preparation
     * @return
     */
    private List<Comment> getExpectedCommentList() {
        final String firstCommentTime = "2016-01-21 15:00:00";
        final String secondCommentTime = "2016-01-22 15:00:00";
        Comment commentFromIvan = Comment.builder().user(User.builder("Ivan", "ivan@gmail.com").build()).message("Great!").
                timeStamp(LocalDateTime.parse(firstCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        Comment commentFromPete = Comment.builder().user(User.builder("Peter", "pete@gmail.com").build()).message("Like it!").
                timeStamp(LocalDateTime.parse(secondCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
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
    public void shouldReturnAbsentInCaseEventWithNoCommentsIdSpecified() throws SQLException {
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


}
