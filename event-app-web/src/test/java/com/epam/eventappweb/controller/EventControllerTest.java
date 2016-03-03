package com.epam.eventappweb.controller;

import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.EventService;
import com.epam.eventappweb.exceptions.EventNotFoundException;
import com.epam.eventappweb.exceptions.EventNotUpdatedException;
import com.epam.eventappweb.model.EventVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matcher;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

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
 * test Class for EventController
 */
public class EventControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private EventService eventServiceMock;

    @InjectMocks
    private EventController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    /**
     * testing getEventDetail from EventController
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
                andExpect(jsonPath("$.creator", is("Ivan"))).
                andExpect(jsonPath("$.name", is("Party")));
    }


    /**
     * testing getEventDetail from EventDetailController
     * mock eventDAO then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect EventNotFoundException thrown
     *
     * @throws Exception
     */
    @Test
    public void shouldTrowExceptionIfEventNotFound() throws Exception {
        //given
        final int id = 1;
        Optional<Event> emptyEvent = Optional.empty();
        when(eventServiceMock.findById(id)).thenReturn(emptyEvent);

        //when
        thrown.expect(NestedServletException.class);
        thrown.expectCause(org.hamcrest.Matchers.isA(EventNotFoundException.class));
        mockMvc.perform(get("/event/" + id));

        //then
        Assert.fail("EventNotFoundException not thrown");
    }

    /**
     * Testing updateEvent from EventController.
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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        final String contentString = objectMapper.writeValueAsString(updatedEventVO);

        when(eventServiceMock.updateEvent(argThat(equalToEvent(id, newName, newCity, newLocation)))).thenReturn(1);

        //when
        ResultActions resultActions = mockMvc.perform(put("/event/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentString));

        //then
        resultActions.andExpect(status().isOk());
    }

    /**
     * Testing updateEvent from EventDetailController.
     * mock eventDAO then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect EventNotFoundException thrown
     */
    @Test
    public void shouldThrowExceptionInCaseWrongEventIdSpecified() throws Exception {
        //given
        final int id = -1;
        final String newName = "Ballet";
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        final EventVO updatedEventVO = EventVO.builder(newName).
                city(newCity).
                location(newLocation).build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        final String contentString = objectMapper.writeValueAsString(updatedEventVO);

        when(eventServiceMock.updateEvent(argThat(equalToEvent(id, newName, newCity,
                newLocation)))).thenReturn(0);

        //when
        thrown.expect(NestedServletException.class);
        thrown.expectCause(org.hamcrest.Matchers.isA(EventNotUpdatedException.class));
        mockMvc.perform(put("/event/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentString));

        //then
        Assert.fail("EventNotUpdatedException not thrown");
    }

    /**
     * Method for getting prepared list of Events
     *
     * @param firstEventName  name of first Event
     * @param secondEventName name of second Event
     * @return list of expected Events
     */
    private static List<Event> getExpectedEventsList(String firstEventName, String secondEventName,
                                                     LocalDateTime firstEventTime, LocalDateTime secondEventTime) {
        final String username = "Vasya";
        final String email = "vasya@vasya.com";
        final User user = User.builder(username, email).build();
        final Event firstEvent = Event.builder(firstEventName).id(0).user(user).creationTime(firstEventTime).build();
        final Event secondEvent = Event.builder(secondEventName).id(1).user(user).creationTime(secondEventTime).build();
        final List<Event> expectedEventsList = new ArrayList<>();
        expectedEventsList.add(firstEvent);
        expectedEventsList.add(secondEvent);
        return expectedEventsList;
    }

    /**
     * Testing getEventList from EventController in <BEFORE> queryMode.
     * Mock eventService then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect JSON with right fields.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnEventsBeforeTimeAsJSON() throws Exception {
        //given
        final String firstEventName = "EPAM fanfest 1";
        final String secondEventName = "EPAM fanfest 2";
        final LocalDateTime firstEventTime = LocalDateTime.parse("2008-09-11T15:00");
        final LocalDateTime secondEventTime = LocalDateTime.parse("2007-09-11T15:00");
        final LocalDateTime effectiveTime = LocalDateTime.parse("2005-09-11T15:00");
        final QueryMode queryMode = QueryMode.BEFORE;
        final List<Event> eventList = getExpectedEventsList(firstEventName, secondEventName, firstEventTime, secondEventTime);

        when(eventServiceMock.getOrderedEvents(effectiveTime, queryMode)).thenReturn(eventList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/event/?queryMode=" + queryMode.toString() +
                "&time=" + effectiveTime));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", Matchers.is(firstEventName)))
                .andExpect(jsonPath("$.[1].name", Matchers.is(secondEventName)));
    }

    /**
     * Testing getEventList from EventController in <AFTER> queryMode.
     * Mock eventService then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect JSON with right fields.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnEventsAfterTimeAsJSON() throws Exception {
        //given
        final String firstEventName = "EPAM fanfest 1";
        final String secondEventName = "EPAM fanfest 2";
        final LocalDateTime firstEventTime = LocalDateTime.parse("2008-09-11T15:00");
        final LocalDateTime secondEventTime = LocalDateTime.parse("2007-09-11T15:00");
        final LocalDateTime effectiveTime = LocalDateTime.now();
        final QueryMode queryMode = QueryMode.AFTER;
        final List<Event> eventList = getExpectedEventsList(firstEventName, secondEventName, firstEventTime, secondEventTime);

        when(eventServiceMock.getOrderedEvents(effectiveTime, queryMode)).thenReturn(eventList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/event/?queryMode=" + queryMode.toString() +
                "&time=" + effectiveTime));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", Matchers.is(firstEventName)))
                .andExpect(jsonPath("$.[1].name", Matchers.is(secondEventName)));
    }

    /**
     * Testing getEventList from EventDetailController.
     * Passing as parameter string that doesn't correspond with existing enum value.
     * Expect 400 status.
     *
     * @throws Exception
     */
    @Test
    public void shoudReturn400inCaseWrongQueryModeSpecified() throws Exception {
        //given
        final LocalDateTime effectiveTime = LocalDateTime.now();
        final String queryMode = "WRONG MODE";

        //when
        ResultActions resultActions = mockMvc.perform(get("/event/?queryMode=" + queryMode +
                "&time=" + effectiveTime));
        //then
        resultActions.andExpect(status().isBadRequest());
    }

    /**
     * Test addEvent of EventDetailController
     * mock EventDetailController and expects status 201
     */
    @Test
    public void shouldCreateEvent() throws Exception {

        //given
        final String userName = "Admin";
        final String eventName = "test event";
        final String location = "Obvodniy kanal";
        final String city = "spb";
        final String password = "1234";

        EventVO eventVO = EventVO.builder(eventName).location(location).city(city).build();
        Event event = Event.builder(eventName).location(location).city(city).build();
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(userName, password);

        String jsonObj = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .writeValueAsString(eventVO);

        Mockito.when(eventServiceMock.createEvent(argThat(allOf(Matchers.isA(Event.class), hasProperty("location", Matchers.is(Optional.of(location))),
                hasProperty("name", Matchers.is(eventName)), hasProperty("city", Matchers.is(Optional.of(city))))), eq(userName))).thenReturn(event);

        //when
        ResultActions resultActions = mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj)
                        .principal(principal)
        );

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(eventName)))
                .andExpect(jsonPath("$.city", is(city)))
                .andExpect(jsonPath("$.location", is(location)));

    }

    /**
     * Testing countNumberOfNewEvents from EventDetailController.
     * Mock getNumberOfNewEvents then inject it to controller. Using mockMvc to assert the behaviour of controller.
     * Expect number of new events.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnNumberOfNewEvents() throws Exception {
        //given
        final LocalDateTime after = LocalDateTime.now();
        final Integer expectedNumberOfNewEvents = 2;
        when(eventServiceMock.getNumberOfNewEvents(after)).thenReturn(expectedNumberOfNewEvents);

        //when
        ResultActions resultActions = mockMvc.perform(get("/event/count?after=" + after));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string(expectedNumberOfNewEvents.toString()));
    }

    /**
     * Matcher for Events, checks if both events are instances of same class and have same id field
     *
     * @param id value to compare with
     * @return Matcher
     */
    private static Matcher<Event> equalToEvent(int id, String name, String city, String location) {
        return org.hamcrest.Matchers.allOf(
                is(instanceOf(Event.class)),
                hasProperty("id", is(equalTo(id))),
                hasProperty("name", is(equalTo(name))),
                hasProperty("city", is(equalTo(Optional.of(city)))),
                hasProperty("location", is(equalTo(Optional.of(location))))
        );
    }
}

