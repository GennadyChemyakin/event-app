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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnCommentListAsJson() throws Exception {

        //given
        final int id = 0;
        final int firstCommentId = 0;
        final int secondCommentId = 1;
        final String firstCommentUsername = "Ivan";
        final String firstCommentMessage = "Great!";
        final String secondCommentUsername = "Peter";
        final String secondCommentMessage = "I like it!";
        final String firstCommentTime = "2016-01-21 15:00:00";
        final String secondCommentTime = "2016-01-22 15:00:00";
        final String commentTime = "2016-01-23 15:00:00";
        final Timestamp commentTimestamp = Timestamp.valueOf(LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Comment commentFromIvan = Comment.builder().user(User.builder(firstCommentUsername, "ivan@gmail.com").build()).
                message(firstCommentMessage).id(firstCommentId).
                timeStamp(LocalDateTime.parse(firstCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        Comment commentFromPete = Comment.builder().user(User.builder(secondCommentUsername, "pete@gmail.com").build()).
                message(secondCommentMessage).id(secondCommentId).
                timeStamp(LocalDateTime.parse(secondCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        List<Comment> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentFromIvan);
        expectedCommentList.add(commentFromPete);
        Optional<List<Comment>> commentList = Optional.of(expectedCommentList);
        when(commentServiceMock.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentTimestamp, CommentController.COMMENTS_AMOUNT)).thenReturn(commentList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/commentList/" + id + "/" + commentTimestamp.getTime()));

        //then
        resultActions.andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].id", Matchers.is(firstCommentId))).
                andExpect(jsonPath("$.[1].id", Matchers.is(secondCommentId))).
                andExpect(jsonPath("$.[0].message", Matchers.is(firstCommentMessage))).
                andExpect(jsonPath("$.[1].message", Matchers.is(secondCommentMessage))).
                andExpect(jsonPath("$.[0].username", Matchers.is(firstCommentUsername))).
                andExpect(jsonPath("$.[1].username", Matchers.is(secondCommentUsername)));
    }

    /**
     * testing getCommentList from CommentController
     * expect 404 status code
     *
     * @throws Exception
     */
    @Test
    public void shouldReturn404IfCommentsNotFound() throws Exception {

        //given
        final int id = 1;
        final String commentTime = "2016-01-23 15:00:00";
        final Timestamp commentTimestamp = Timestamp.valueOf(LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Optional<List<Comment>> emptyCommentList = Optional.empty();
        when(commentServiceMock.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentTimestamp, CommentController.COMMENTS_AMOUNT)).
                thenReturn(emptyCommentList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/commentList/" + id + "/" + commentTimestamp.getTime()));

        //then
        resultActions.andExpect(status().isNotFound());
    }
}
