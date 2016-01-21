package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.CommentService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * test Class for CommentController
 */
public class CommentControllerTest {

    @Mock
    private CommentService commentServiceMock;

    @InjectMocks
    private CommentController sut;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(sut).build();
    }

    /**
     * testing getCommentList from CommentController
     * expect JSON with right fields
     * @throws Exception
     */
    @Test
    public void shouldReturnCommentListAsJson() throws Exception {

        //given
        final int id = 0;
        final int offset = 2;
        final int firstCommentId = 0;
        final String FIRST_COMMENT_USERNAME = "Ivan";
        final String FIRST_COMMENT_MESSAGE = "Great!";
        final String SECOND_COMMENT_USERNAME = "Peter";
        final String SECOND_COMMENT_MESSAGE = "Peter";
        final int secondCommentId = 1;
        Comment commentFromIvan = Comment.builder().user(User.builder(FIRST_COMMENT_USERNAME, "ivan@gmail.com").build()).
                message(FIRST_COMMENT_MESSAGE).id(firstCommentId).build();
        Comment commentFromPete = Comment.builder().user(User.builder(SECOND_COMMENT_USERNAME, "pete@gmail.com").build()).
                message(SECOND_COMMENT_MESSAGE).id(secondCommentId).build();
        List<Comment> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentFromIvan);
        expectedCommentList.add(commentFromPete);
        Optional<List<Comment>> commentList = Optional.of(expectedCommentList);
        when(commentServiceMock.getCommentsListByEventId(id, offset, CommentController.COMMENTS_AMOUNT)).thenReturn(commentList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/commentList/" + id + "/" + offset));

        //then
        resultActions.andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].id", Matchers.is(firstCommentId))).
                andExpect(jsonPath("$.[1].id", Matchers.is(secondCommentId))).
                andExpect(jsonPath("$.[0].message", Matchers.is(FIRST_COMMENT_MESSAGE))).
                andExpect(jsonPath("$.[1].message", Matchers.is(SECOND_COMMENT_MESSAGE))).
                andExpect(jsonPath("$.[0].username", Matchers.is(FIRST_COMMENT_USERNAME))).
                andExpect(jsonPath("$.[1].username", Matchers.is(SECOND_COMMENT_USERNAME)));
    }

    /**
     * testing getCommentList from CommentController
     * expect 404 status code
     * @throws Exception
     */
    @Test
    public void shouldReturn404IfCommentsNotFound() throws Exception {

        //given
        final int id = 1;
        final int offset = 2;
        Optional<List<Comment>> emptyCommentList = Optional.empty();
        when(commentServiceMock.getCommentsListByEventId(id, offset, CommentController.COMMENTS_AMOUNT)).
                thenReturn(emptyCommentList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/commentList/" + id + "/" + offset));

        //then
        resultActions.andExpect(status().isNotFound());
    }
}
