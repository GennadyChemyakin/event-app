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
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        ResultActions resultActions = mockMvc.perform(get("/comment?eventId=" + id + "&before=" + commentDateTime));

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
     * testing showNewComments from CommentController
     * expect JSON with right fields
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnListOfNewComments() throws Exception {
        //given
        final int id = 0;
        final String firstCommentTime = "2016-01-21 15:00:00";
        final String secondCommentTime = "2016-01-22 15:00:00";
        final String commentTime = "2016-01-19 15:00:00";
        final LocalDateTime commentDateTime = LocalDateTime.parse(commentTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        Comment commentFromIvan = Comment.builder().user(User.builder("Ivan", "ivan@gmail.com").build()).
                message("Great!").id(0).
                commentTime(LocalDateTime.parse(firstCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
        Comment commentFromPete = Comment.builder().user(User.builder("Peter", "pete@gmail.com").build()).
                message("I like it!").id(1).
                commentTime(LocalDateTime.parse(secondCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();


        List<Comment> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentFromIvan);
        expectedCommentList.add(commentFromPete);


        when(commentServiceMock.getListOfNewComments(id, commentDateTime)).
                thenReturn(expectedCommentList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/comment/new?eventId=" + id + "&after=" + commentDateTime));

        //then
        resultActions.andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].id", Matchers.is(expectedCommentList.get(0).getId()))).
                andExpect(jsonPath("$.[1].id", Matchers.is(expectedCommentList.get(1).getId()))).
                andExpect(jsonPath("$.[0].message", Matchers.is(expectedCommentList.get(0).getMessage()))).
                andExpect(jsonPath("$.[1].message", Matchers.is(expectedCommentList.get(1).getMessage()))).
                andExpect(jsonPath("$.[0].username", Matchers.is(expectedCommentList.get(0).getUser().getUsername()))).
                andExpect(jsonPath("$.[1].username", Matchers.is(expectedCommentList.get(1).getUser().getUsername())));
    }


    /**
     * testing addComment from CommentController
     * expect status code 200
     *
     * @throws Exception
     */
    @Test
    public void shouldAddComment() throws Exception {

        //given
        final String newCommentTime = "2016-01-23 15:00:10";
        User newCommentUser = User.builder("Ivan", "ivan@gmail.com").id(0).build();

        CommentVO newCommentVO = CommentVO.builder().username(newCommentUser.getUsername()).message("Hello!").
                commentTime(LocalDateTime.parse(newCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                eventId(0).build();

        Comment newComment = Comment.builder().user(newCommentUser).id(newCommentVO.getId()).eventId(newCommentVO.getEventId()).
                message(newCommentVO.getMessage()).commentTime(newCommentVO.getCommentTime()).build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        when(userServiceMock.getUserByUsername(newCommentUser.getUsername())).thenReturn(newCommentUser);
        Mockito.doNothing().when(commentServiceMock).addComment(newComment);

        //when
        ResultActions resultActions = mockMvc.perform(post("/comment").
                principal(new TestingAuthenticationToken(newCommentUser.getUsername(), null)).
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(newCommentVO)));

        //then
        resultActions.andExpect(status().isOk());
    }
}
