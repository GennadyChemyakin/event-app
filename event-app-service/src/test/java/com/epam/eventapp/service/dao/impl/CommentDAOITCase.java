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

    @Ignore
    /**
     * testing getCommentsListByEventId method from CommentDAOImpl.
     * looking for list of comments with eventId = 0 with fixed size of amount and from fixed offset. Checking if it is
     * present, event id from comment is equal
     * to expected id and size of comment list is equal to expected size
     */
    @Test
    public void shouldReturnListOfCommentByEventId() {

        //given
        final int id = 0;
        final int offset = 1;
        final int amount = 3;

        //when
        Optional<List<Comment>> commentList = commentDAO.getCommentsListByEventId(id, offset, amount);

        //then
        Assert.assertTrue(commentList.isPresent());
        Assert.assertEquals(commentList.get().size(), 3);
    }

    @Ignore
    /**
     * testing getCommentsListByEventId method from CommentDAOImpl.
     * expect that comment list would be empty
     */
    @Test
    public void shouldReturnAbsentInCaseEventWithNoCommentsIdSpecified() {

        //given
        final int id = 1;
        final int offset = 1;
        final int amount = 3;

        //when
        Optional<List<Comment>> commentList = commentDAO.getCommentsListByEventId(id, offset, amount);

        //then
        Assert.assertEquals(false, commentList.isPresent());
    }
}
