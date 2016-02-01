package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.model.EventPack;
import com.epam.eventapp.service.service.EventService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
     * mock eventService then inject it to controller. Using mockMvc to assert the behaviour of controller.
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
     * mock eventService then inject it to controller. Using mockMvc to assert the behaviour of controller.
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
     * mock eventService then inject it to controller. Using mockMvc to assert the behaviour of controller.
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
     * mock eventService then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * Expect 404 status code
     */
    @Test
    public void shouldReturn404InCaseWrongEventIdSpecified() throws Exception {
        //given
        final int id = 100;
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
     * Method for getting prepared EventPack
     * @param firstEventName name of first Event
     * @param secondEventName name of second Event
     * @param numberOfEvents number of all events in DB
     * @return EventPack of expected Events
     */
    private static EventPack getExpectedEventsList(String firstEventName, String secondEventName, int numberOfEvents) {
        final String username = "Vasya";
        final String email = "vasya@vasya.com";
        final User user = User.builder(username, email).build();
        final Event firstEvent = Event.builder(firstEventName).id(0).user(user).build();
        final Event secondEvent = Event.builder(secondEventName).id(1).user(user).build();
        final List<Event> expectedEventsList = new ArrayList<>();
        expectedEventsList.add(firstEvent);
        expectedEventsList.add(secondEvent);
        final EventPack eventPack = new EventPack(expectedEventsList, numberOfEvents);
        return eventPack;
    }

    /**
     * Testing getEventList from EventDetailController.
     * mock eventService then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect JSON with right fields
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnEventPackAsJSON() throws Exception {
        //given
        final String firstEventName = "EPAM fanfest 1";
        final String secondEventName = "EPAM fanfest 2";
        final int events_amount = 2;
        final int numberOfEvents = 10;
        final LocalDateTime creationTime = LocalDateTime.now();
        final EventPack eventPack = getExpectedEventsList(firstEventName, secondEventName, numberOfEvents);

        when(eventServiceMock.getEventsBeforeTime(creationTime, events_amount)).thenReturn(eventPack);

        //when
        ResultActions resultActions = mockMvc.perform(get("/events?creationTime=" + creationTime));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.eventPreviewVOList.[0].name", Matchers.is(firstEventName)))
                .andExpect(jsonPath("$.eventPreviewVOList.[1].name", Matchers.is(secondEventName)))
                .andExpect(jsonPath("$.numberOfEvents", Matchers.is(numberOfEvents)))
        ;

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
}

