package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.EventService;
import com.epam.eventappweb.model.EventVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
     * mock eventDAO then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect JSON with right fields
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnEventAsJSON() throws Exception {

        //given
        final int id = 0;
        Optional<Event> expectedEvent = Optional.of(Event.builder("Party").
                user(User.builder("Ivan", "ivan@gmail.com").build()).build());
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
     * mock eventDAO then inject it to controller. Using mockMvc to assert the behaviour of controller.
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
     * mock eventDAO then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * Expect 200 status code
     *
     * @throws Exception
     */
    @Test
    public void shouldUpdateEvent() throws Exception {
        //given
        final int id = 0;
        final String newName = "Ballet";
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        final EventVO updatedEventVO = EventVO.builder(newName).
                city(newCity).
                location(newLocation).build();

        when(eventServiceMock.updateEvent(argThat(equalToEvent(id, newName, newCity, newLocation)))).thenReturn(1);

        //when
        ResultActions resultActions = mockMvc.perform(put("/event/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedEventVO)));

        //then
        resultActions.andExpect(status().isOk());
    }

    /**
     * Testing updateEvent from EventDetailController.
     * mock eventDAO then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * Expect 404 status code
     */
    @Test
    public void shouldReturn404InCaseWrongEventIdSpecified() throws Exception {
        //given
        final int id = -1;
        final String newName = "Ballet";
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        final EventVO updatedEventVO = EventVO.builder(newName).
                city(newCity).
                location(newLocation).build();

        when(eventServiceMock.updateEvent(argThat(equalToEvent(id, newName, newCity, newLocation)))).thenReturn(0);

        //when
        ResultActions resultActions = mockMvc.perform(put("/event/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedEventVO)));

        //then
        resultActions.andExpect(status().isNotFound());
    }

    /**
     * Matcher for Events, checks if both events are instances of same class and have same id field
     * @param id value to compare with
     * @return Matcher
     */
    private static Matcher<Event> equalToEvent(int id, String name, String city, String location) {
        return org.hamcrest.Matchers.allOf(
                is(instanceOf(Event.class)),
                hasProperty("id", is(equalTo(id))),
                hasProperty("name", is(equalTo(name))),
                hasProperty("city", is(equalTo(city))),
                hasProperty("location", is(equalTo(location)))
        );
    }

    /**
     * Test addEvent of EventDetailController
     * mock EventDetailController and expects status 201
     */
    @Test
    public void shouldReturnStatusCreated() throws Exception {

        //given
        final String userName  = "Admin";
        final String eventName = "test event";
        final String password  = "1234";

        EventVO eventVO = EventVO.builder(eventName).build();
        Event   event   = Event.builder(eventName).build();
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(userName,password);

        Mockito.doNothing().when(eventServiceMock).createEvent(event,userName);
        String jsonObj = new ObjectMapper().writeValueAsString(eventVO);

        //when
        ResultActions resultActions = mockMvc.perform(post("/add_event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObj)
                .principal(principal)
        );

        //then
        resultActions.andExpect(status().isCreated());

    }

}

