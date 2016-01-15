package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.EventService;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.EventVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * test Class for EventDetailController
 */
public class EventDetailControllerTest {


    @Mock
    private EventService eventServiceMock;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private EventDetailController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    /**
     * testing getEventDetail from EventDetailController
     * mock eventDAO than inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect JSON with right fields
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnEventAsJSON() throws Exception {

        //given
        final int id = 0;
        Optional<Event> expectedEvent = Optional.of(Event.builder(User.builder("Ivan", "ivan@gmail.com").build(), "Party").build());
        when(eventServiceMock.findById(id)).thenReturn(expectedEvent);

        //when
        ResultActions resultActions = mockMvc.perform(get("/event/" + id));

        //then
        resultActions.andExpect(status().isOk()).
                andExpect(jsonPath("$.id", is(0))).
                andExpect(jsonPath("$.user.username", is("Ivan"))).
                andExpect(jsonPath("$.user.email", is("ivan@gmail.com"))).
                andExpect(jsonPath("$.name", is("Party")));
    }


    /**
     * testing getEventDetail from EventDetailController
     * mock eventDAO than inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect 404 status code
     *
     * @throws Exception
     */
    @Test
    public void shouldReturn404IfEventNotFound() throws Exception {
        //given
        final int id = 1;
        Optional<Event> emptyEvent = Optional.empty();
        when(eventServiceMock.findById(id)).thenReturn(emptyEvent);

        //when
        ResultActions resultActions = mockMvc.perform(get("/event/" + id));

        //then
        resultActions.andExpect(status().isNotFound());
    }

    /**
     * Testing updateEvent from EventDetailController.
     * mock eventDAO than inject it to controller. Using mockMvc to assert the behaviour of controller.
     * Expect 200 status code
     *
     * @throws Exception
     */
    @Test
    public void shouldUpdateEvent() throws Exception {
        //given
        final int id = 0;
        final String username = "Vasya";
        final String newName = "Ballet";
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        final User user = User.builder(username, "vasya@vasya.com").build();
        final EventVO updatedEventVO = EventVO.builder(newName).
                username(username).
                city(newCity).
                location(newLocation).build();

        when(userServiceMock.findByUsername(username)).thenReturn(Optional.of(user));
        when(eventServiceMock.updateEvent(argThat(both(Matchers.isA(Event.class)).and(hasProperty("id", equalTo(0)))))).thenReturn(1);

        //when
        ResultActions resultActions = mockMvc.perform(put("/event/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedEventVO)));

        //then
        resultActions.andExpect(status().isOk());
    }

    /**
     * Testing updateEvent from EventDetailController.
     * mock eventDAO than inject it to controller. Using mockMvc to assert the behaviour of controller.
     * Expect 500 status code
     */
    @Test
    public void shouldReturn500InCaseWrongIdSpecified() throws Exception {
        //given
        final int id = -1;
        final String username = "Vasya";
        final String newName = "Ballet";
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        final User user = User.builder(username, "vasya@vasya.com").build();
        final EventVO updatedEventVO = EventVO.builder(newName).
                username(username).
                city(newCity).
                location(newLocation).build();

        when(userServiceMock.findByUsername(username)).thenReturn(Optional.of(user));
        when(eventServiceMock.updateEvent(argThat(both(Matchers.isA(Event.class)).and(hasProperty("id", equalTo(-1)))))).thenReturn(0);

        //when
        ResultActions resultActions = mockMvc.perform(put("/event/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedEventVO)));

        //then
        resultActions.andExpect(status().isInternalServerError());
    }
}

