package com.epam.eventapp.service.dao.impl;



import com.epam.eventapp.service.config.TestDataAccessConfig;
import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
     * looking for list of comments with eventId = 0 with fixed size of amount and that were added before known timestamp.
     * Checking if it is present, event id from comment is equal
     * to expected id and size of comment list is equal to expected size
     */
    @Ignore
    @Test
    public void shouldReturnListOfCommentByEventId() {

        //given
        final int id = 0;
        final String commentTime = "2016-01-20 15:00:00";
        final int amount = 3;

        //when
        Optional<List<Comment>> commentList = commentDAO.getCommentsListOfFixedSizeByEventIdBeforeDate(id,
                Timestamp.valueOf(LocalDateTime.parse(commentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))), amount);

        //then
        Assert.assertTrue(commentList.isPresent());
        Assert.assertEquals(commentList.get().size(), 3);
    }


    /**
     * testing getCommentsListOfFixedSizeByEventIdBeforeDate method from CommentDAOImpl.
     * expect that comment list would be empty
     */
    @Ignore
    @Test
    public void shouldReturnAbsentInCaseEventWithNoCommentsIdSpecified() {

        //given
        final int id = 1;
        final String commentTime = "2016-01-20 15:00:00";
        final int amount = 3;

        //when
        Optional<List<Comment>> commentList = commentDAO.getCommentsListOfFixedSizeByEventIdBeforeDate(id,
                Timestamp.valueOf(LocalDateTime.parse(commentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))), amount);

        //then
        Assert.assertEquals(false, commentList.isPresent());
    }

    /**
     * testing countOfCommentsAddedBeforeDate method from CommentDAOImpl.
     * counting comments that were added before specified time
     * checking that amount is equals to know amount of comments
     */
    @Ignore
    @Test
    public void shouldReturnAmountOfRemainingComments() {
        //given
        final int id = 0;
        final String commentTime = "2016-01-20 15:00:00";
        final int amount = 3;

        //when
        Integer remainingComments = commentDAO.countOfCommentsAddedBeforeDate(id,Timestamp.valueOf(
                LocalDateTime.parse(commentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        //then
        Assert.assertEquals(amount, remainingComments.intValue());
    }

}
