package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Comment;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.ObjectNotDeletedException;
import com.epam.eventapp.service.model.CommentPack;
import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.service.CommentService;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.CommentVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private CommentService commentServiceMock;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private CommentController sut;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(sut).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
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
                andExpect(jsonPath("$.commentVOList.[0].id", is(firstCommentId))).
                andExpect(jsonPath("$.commentVOList.[1].id", is(secondCommentId))).
                andExpect(jsonPath("$.commentVOList.[0].message", is(firstCommentMessage))).
                andExpect(jsonPath("$.commentVOList.[1].message", is(secondCommentMessage))).
                andExpect(jsonPath("$.commentVOList.[0].username", is(firstCommentUsername))).
                andExpect(jsonPath("$.commentVOList.[1].username", is(secondCommentUsername))).
                andExpect(jsonPath("$.remainingCommentsCount", is(remainingComments)));
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
                andExpect(jsonPath("$.[0].id", is(expectedCommentList.get(0).getId()))).
                andExpect(jsonPath("$.[1].id", is(expectedCommentList.get(1).getId()))).
                andExpect(jsonPath("$.[0].message", is(expectedCommentList.get(0).getMessage()))).
                andExpect(jsonPath("$.[1].message", is(expectedCommentList.get(1).getMessage()))).
                andExpect(jsonPath("$.[0].username", is(expectedCommentList.get(0).getUser().getUsername()))).
                andExpect(jsonPath("$.[1].username", is(expectedCommentList.get(1).getUser().getUsername())));
    }


    /**
     * testing addComment from CommentController
     * expect status code 204
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

        String contentString = objectMapper.writeValueAsString(newCommentVO);

        when(userServiceMock.getUserByUsername(newCommentUser.getUsername())).thenReturn(newCommentUser);
        Mockito.doNothing().when(commentServiceMock).addComment(newComment);

        //when
        ResultActions resultActions = mockMvc.perform(post("/comment").
                principal(new TestingAuthenticationToken(newCommentUser.getUsername(), null)).
                contentType(MediaType.APPLICATION_JSON).
                content(contentString));

        //then
        resultActions.andExpect(status().isNoContent());
    }

    /**
     * testing deleteCommentary from CommentController
     * expect status code 204
     *
     * @throws Exception
     */
    @Test
    public void shouldDeleteComment() throws Exception {
        //given
        final String newCommentTime = "2016-01-23 15:00:10";
        User commentUser = User.builder("Ivan", "ivan@gmail.com").id(0).build();

        CommentVO commentVO = CommentVO.builder().username(commentUser.getUsername()).message("Hello!").
                commentTime(LocalDateTime.parse(newCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                eventId(0).id(0).build();

        Comment comment = Comment.builder().user(commentUser).id(commentVO.getId()).eventId(commentVO.getEventId()).
                message(commentVO.getMessage()).commentTime(commentVO.getCommentTime()).id(0).build();

        String contentString = objectMapper.writeValueAsString(commentVO);

        Mockito.doNothing().when(commentServiceMock).deleteComment(comment);

        //when
        ResultActions resultActions = mockMvc.perform(delete("/comment").
                contentType(MediaType.APPLICATION_JSON).
                content(contentString));

        //then
        resultActions.andExpect(status().isNoContent());

    }

    /**
     * testing deleteCommentary from CommentController
     * expect ObjectNotDeletedException thrown
     *
     * @throws Exception
     */
    @Test
    public void shouldThrowExceptionIfCommentaryNotDeleted() throws Exception {
        //given
        final String newCommentTime = "2016-01-23 15:00:10";
        User commentUser = User.builder("Ivan", "ivan@gmail.com").id(0).build();

        CommentVO commentVO = CommentVO.builder().username(commentUser.getUsername()).message("Hello!").
                commentTime(LocalDateTime.parse(newCommentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                eventId(0).id(0).build();

        Comment comment = Comment.builder().user(commentUser).id(commentVO.getId()).eventId(commentVO.getEventId()).
                message(commentVO.getMessage()).commentTime(commentVO.getCommentTime()).id(0).build();

        Mockito.doThrow(ObjectNotDeletedException.class).when(commentServiceMock).
                deleteComment(argThat(allOf(Matchers.isA(Comment.class), hasProperty("id", is(comment.getId())))));

        String contentString = objectMapper.writeValueAsString(commentVO);

        //when
        thrown.expect(NestedServletException.class);
        thrown.expectCause(Matchers.isA(ObjectNotDeletedException.class));
        mockMvc.perform(delete("/comment").
                contentType(MediaType.APPLICATION_JSON).
                content(contentString));

        //then
        Assert.fail("ObjectNotDeletedException not thrown");
    }

    /**
     * testing countCommentsAddedBeforeOrAfterDate from CommentController
     * expect status 200 and amount of comments that were added after specified date
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnAmountOfCommentsAddedAfterDate() throws Exception {
        //given
        final int id = 0;
        final String commentTimeString = "2016-01-23 15:00:10";
        final LocalDateTime commentTime = LocalDateTime.parse(commentTimeString,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        final int commentsAmount = 2;
        QueryMode queryMode = QueryMode.AFTER;
        when(commentServiceMock.countCommentsAddedBeforeOrAfterDate(id, commentTime, queryMode)).thenReturn(commentsAmount);

        //when
        ResultActions resultActions = mockMvc.perform(get("/comment/count?queryMode=" + queryMode.name() + "&eventId=" + id +
                "&commentTime=" + commentTime));

        //then
        resultActions.andExpect(status().isOk());
        Assert.assertEquals(String.valueOf(commentsAmount), resultActions.andReturn().getResponse().getContentAsString());
    }

}
