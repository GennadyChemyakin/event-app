package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.CommentDAO;
import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.CommentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
     * testing getCommentsListByEventId method from CommentServiceImpl.
     * looking for list of comments with eventId = 0. Checking if it is present, event id from comment is equal
     * to expected id and size of comment list is equal to expected size
     */
    @Test
    public void shouldReturnListOfCommentByEventId() {

        //given
        final int id = 0;
        final int offset = 1;
        final int commentsAmount = 2;
        Comment commentFromIvan = Comment.builder().user(User.builder("Ivan", "ivan@gmail.com").build()).message("Great!").build();
        Comment commentFromPete = Comment.builder().user(User.builder("Peter", "pete@gmail.com").build()).message("Like it!").build();
        List<Comment> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentFromIvan);
        expectedCommentList.add(commentFromPete);
        Optional<List<Comment>> commentList = Optional.of(expectedCommentList);
        when(commentDAOMock.getCommentsListByEventId(id, offset, commentsAmount)).thenReturn(commentList);

        //when
        Optional<List<Comment>> comments = sut.getCommentsListByEventId(id, offset, commentsAmount);

        //then
        Assert.assertTrue(comments.isPresent());
        Assert.assertEquals(id, comments.get().get(0).getEventId());
        Assert.assertEquals(commentsAmount, comments.get().size());
    }

    /**
     * testing getCommentsListByEventId method from CommentServiceImpl.
     * expect that comment list would be empty
     */
    @Test
    public void shouldReturnAbsentInCaseEventWithNoCommentsIdSpecified() {
        //given
        final int id = 1;
        final int offset = 1;
        final int commentsAmount = 2;
        Optional<List<Comment>> absentCommentList = Optional.empty();
        when(commentDAOMock.getCommentsListByEventId(id, offset, commentsAmount)).thenReturn(absentCommentList);

        //when
        Optional<List<Comment>> commentList = sut.getCommentsListByEventId(id, offset, commentsAmount);

        //then
        Assert.assertFalse(commentList.isPresent());
    }


}
