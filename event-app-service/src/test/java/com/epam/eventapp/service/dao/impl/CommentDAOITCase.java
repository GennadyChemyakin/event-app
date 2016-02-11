package com.epam.eventapp.service.dao.impl;


import com.epam.eventapp.service.config.TestDataAccessConfig;
import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.IdentifierNotDeletedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Class provides methods for testing CommentDAOImpl. Use DataAccessConfig.class for creating context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataAccessConfig.class})
public class CommentDAOITCase extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private CommentDAO commentDAO;


    /**
     * testing getCommentsListOfFixedSizeByEventIdBeforeDate method from CommentDAOImpl.
     * looking for list of comments with eventId = 0 with fixed size of amount and that were added before known time.
     * Checking if  event id from comment is equal
     * to expected id and size of comment list is equal to expected size
     */
    @Test
    public void shouldReturnListOfCommentByEventId() {

        //given
        final int id = 0;
        final String commentTime = "2016-01-20 15:00:00";
        final int amount = 3;

        //when
        List<Comment> commentList = commentDAO.getCommentsListOfFixedSizeByEventIdBeforeDate(id,
                LocalDateTime.parse(commentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), amount);

        //then
        Assert.assertEquals(commentList.size(), 3);
        Assert.assertEquals(commentList.get(commentList.size() - 1).getEventId(), id);
    }


    /**
     * testing getCommentsListOfFixedSizeByEventIdBeforeDate method from CommentDAOImpl.
     * expect that comment list would be empty
     */
    @Test
    public void shouldReturnAbsentInCaseEventWithNoCommentsIdSpecified() {

        //given
        final int id = 1;
        final String commentTime = "2016-01-20 15:00:00";
        final int amount = 3;

        //when
        List<Comment> commentList = commentDAO.getCommentsListOfFixedSizeByEventIdBeforeDate(id,
                LocalDateTime.parse(commentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), amount);

        //then
        Assert.assertEquals(0, commentList.size());
    }

    /**
     * testing countOfCommentsAddedBeforeDate method from CommentDAOImpl.
     * counting comments that were added before specified time
     * checking that amount is equals to known amount of comments
     */
    @Test
    public void shouldReturnAmountOfRemainingComments() {
        //given
        final int id = 0;
        final String commentTime = "2016-01-20 15:00:00";
        final int amount = 3;

        //when
        int remainingCommentsCount = commentDAO.countOfCommentsAddedBeforeDate(id,
                LocalDateTime.parse(commentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //then
        Assert.assertEquals(amount, remainingCommentsCount);
    }

    /**
     * testing addComment method from CommentDAOImpl
     * expect that after adding of comment count of rows in COMMENTARY table increase by 1
     */
    @Test
    public void shouldAddCommentary() {
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
        int rowsBefore = countRowsInTable("commentary");

        //when
        commentDAO.addComment(comment);

        //then
        int rowsAfter = countRowsInTable("commentary");
        Assert.assertEquals(1, rowsAfter - rowsBefore);

    }

    /**
     * testing getListOfNewComments method from CommentDAOImpl.
     * looking for list of comments with eventId = 0 that were added after known time.
     * Checking if  event id from comment is equal
     * to expected id and size of comment list is equal to expected size
     */
    @Test
    public void shouldReturnListOfNewComments() {
        //given
        final int id = 0;
        final String commentTime = "2016-01-18 15:00:00";
        final int amount = 3;

        //when
        List<Comment> commentList = commentDAO.getListOfNewComments(id,
                LocalDateTime.parse(commentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //then
        Assert.assertEquals(commentList.size(), amount);
        Assert.assertEquals(commentList.get(commentList.size() - 1).getEventId(), id);
    }

    /**
     * testing getCommentsListOfFixedSizeByEventIdBeforeDate method from CommentDAOImpl.
     * expect that comment list would be empty
     */
    @Test
    public void shouldReturnEmptyListInCaseNoNewComments() {

        //given
        final int id = 1;
        final String commentTime = "2016-01-20 15:00:00";
        final int amount = 3;

        //when
        List<Comment> commentList = commentDAO.getListOfNewComments(id,
                LocalDateTime.parse(commentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //then
        Assert.assertEquals(0, commentList.size());
    }

    /**
     * testing deleteCommentById from CommentDAOImpl
     * expect that after deleting comment count of rows in COMMENTARY table decrease by 1
     */
    @Test
    public void shouldDeleteComment() {
        //given
        final int id = 1;
        int rowsBefore = countRowsInTable("commentary");

        //when
        commentDAO.deleteCommentById(id);

        //then
        int rowsAfter = countRowsInTable("commentary");
        Assert.assertEquals(1, rowsBefore - rowsAfter);
    }

    /**
     * testing deleteCommentById from CommentDAOImpl
     * expect that IdentifierNotDeletedException thrown if wrong id specified
     */
    @Test(expected = IdentifierNotDeletedException.class)
    public void shouldThrowExceptionInCaseWrongIdSpecified() {
        //given
        final int id = -1;

        //when
        commentDAO.deleteCommentById(id);

        //then
        Assert.fail("IdentifierNotDeletedException not thrown");
    }
}
