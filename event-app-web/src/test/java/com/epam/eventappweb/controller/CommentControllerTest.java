package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.model.CommentPack;
import com.epam.eventapp.service.service.CommentService;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.CommentVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * test Class for CommentController
 */
public class CommentControllerTest {

    @Mock
    private CommentService commentServiceMock;

    @Mock
    private UserService userServiceMock;

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
        final int remainingComments = 3;
        final String firstCommentUsername = "Ivan";
        final String firstCommentMessage = "Great!";
        final String secondCommentUsername = "Peter";
        final String secondCommentMessage = "I like it!";
        final String firstCommentTime = "2016-01-21 15:00:00";
        final String secondCommentTime = "2016-01-22 15:00:00";
        final String commentTime = "2016-01-23 15:00:00";
        final LocalDateTime commentDateTime = LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Comment commentFromIvan = Comment.builder().user(User.builder(firstCommentUsername, "ivan@gmail.com").build()).
                message(firstCommentMessage).id(firstCommentId).
                commentTime(LocalDateTime.parse(firstCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        Comment commentFromPete = Comment.builder().user(User.builder(secondCommentUsername, "pete@gmail.com").build()).
                message(secondCommentMessage).id(secondCommentId).
                commentTime(LocalDateTime.parse(secondCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        List<Comment> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentFromIvan);
        expectedCommentList.add(commentFromPete);

        CommentPack expectedCommentPack = new CommentPack(expectedCommentList, remainingComments);
        when(commentServiceMock.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentDateTime, CommentController.COMMENTS_AMOUNT)).
                thenReturn(expectedCommentPack);

        //when
        ResultActions resultActions = mockMvc.perform(get("/comment?eventId=" + id + "&commentTime=" + commentDateTime));

        //then
        resultActions.andExpect(status().isOk()).
                andExpect(jsonPath("$.commentVOList.[0].id", Matchers.is(firstCommentId))).
                andExpect(jsonPath("$.commentVOList.[1].id", Matchers.is(secondCommentId))).
                andExpect(jsonPath("$.commentVOList.[0].message", Matchers.is(firstCommentMessage))).
                andExpect(jsonPath("$.commentVOList.[1].message", Matchers.is(secondCommentMessage))).
                andExpect(jsonPath("$.commentVOList.[0].username", Matchers.is(firstCommentUsername))).
                andExpect(jsonPath("$.commentVOList.[1].username", Matchers.is(secondCommentUsername))).
                andExpect(jsonPath("$.remainingCommentsCount", Matchers.is(remainingComments)));
    }


    /**
     * testing getCommentList from CommentController
     * expect 404 status code
     *
     * @throws Exception
     */
    @Ignore
    @Test
    public void shouldReturn404IfCommentsNotFound() throws Exception {

        //given
        final int id = 1;
        final String commentTime = "2016-01-23 15:00:00";
        final LocalDateTime commentDateTime = LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        CommentPack emptyCommentPack = new CommentPack(new ArrayList<>(), 0);
        when(commentServiceMock.getCommentsListOfFixedSizeByEventIdBeforeDate(id, commentDateTime, CommentController.COMMENTS_AMOUNT)).
                thenReturn(emptyCommentPack);

        //when
        ResultActions resultActions = mockMvc.perform(get("/comment?eventId=" + id + "&commentTime=" + commentDateTime));

        //then
        resultActions.andExpect(status().isNotFound());
    }

    /**
     * testing addComment from CommentController
     * expect JSON with right fields
     *
     * @throws Exception
     */
    @Test
    public void shouldAddCommentAndReturnListOfNewComments() throws Exception {
        //given
        final int id = 0;
        final int firstCommentId = 0;
        final int secondCommentId = 1;
        final int newCommentId = 2;
        final int remainingComments = 0;
        final String firstCommentUsername = "Ivan";
        final String firstCommentMessage = "Great!";
        final String secondCommentUsername = "Peter";
        final String secondCommentMessage = "I like it!";
        final String newCommentMessage = "Hello!";
        final String firstCommentTime = "2016-01-21 15:00:00";
        final String secondCommentTime = "2016-01-22 15:00:00";
        final String newCommentTime = "2016-01-23 15:00:10";
        final String commentTime = "2016-01-23 15:00:00";
        final LocalDateTime commentDateTime = LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        User newCommentUser = User.builder(firstCommentUsername, "ivan@gmail.com").id(0).build();

        Comment commentFromIvan = Comment.builder().user(User.builder(firstCommentUsername, "ivan@gmail.com").build()).
                message(firstCommentMessage).id(firstCommentId).
                commentTime(LocalDateTime.parse(firstCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        Comment commentFromPete = Comment.builder().user(User.builder(secondCommentUsername, "pete@gmail.com").build()).
                message(secondCommentMessage).id(secondCommentId).
                commentTime(LocalDateTime.parse(secondCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        Comment newComment = Comment.builder().user(newCommentUser).id(newCommentId).eventId(id).message("Hello!").
                commentTime(LocalDateTime.parse(newCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();

        CommentVO newCommentVO = CommentVO.builder().username(newComment.getUser().getUsername()).message(newComment.getMessage()).
                commentTime(newComment.getCommentTime()).eventId(newComment.getEventId()).build();

        List<Comment> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentFromIvan);
        expectedCommentList.add(commentFromPete);
        expectedCommentList.add(newComment);

        when(userServiceMock.getUserByUsername(newCommentVO.getUsername())).thenReturn(newCommentUser);
        when(commentServiceMock.getListOfNewComments(id, commentDateTime)).
                thenReturn(expectedCommentList);
        Mockito.doNothing().when(commentServiceMock).addComment(argThat(Matchers.isA(Comment.class)));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //when
        ResultActions resultActions = mockMvc.perform(post("/comment?after=" + commentDateTime).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(newCommentVO)));

        //then
        resultActions.andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].id", Matchers.is(firstCommentId))).
                andExpect(jsonPath("$.[1].id", Matchers.is(secondCommentId))).
                andExpect(jsonPath("$.[2].id", Matchers.is(newCommentId))).
                andExpect(jsonPath("$.[0].message", Matchers.is(firstCommentMessage))).
                andExpect(jsonPath("$.[1].message", Matchers.is(secondCommentMessage))).
                andExpect(jsonPath("$.[2].message", Matchers.is(newCommentMessage))).
                andExpect(jsonPath("$.[0].username", Matchers.is(firstCommentUsername))).
                andExpect(jsonPath("$.[1].username", Matchers.is(secondCommentUsername)));
    }
}
