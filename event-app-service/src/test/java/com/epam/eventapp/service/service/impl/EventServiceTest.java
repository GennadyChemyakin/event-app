package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.model.QueryMode;
import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.EventService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;

/**
 * test class for EventService
 */
public class EventServiceTest {

    @Mock
    private EventDAO eventDAOMock;

    @InjectMocks
    private EventService sut;

    @Before
    public void setUp() {
        sut = new EventServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * testing findById method from EventServiceImpl.
     * looking for event with id = 0. Checking if it is not null and event id is equal to expected id
     */
    @Test
    public void shouldReturnEventById() {
        //given
        final int id = 0;
        Optional<Event> expectedEvent = Optional.of(Event.builder("Party").id(id).build());
        when(eventDAOMock.findById(id)).thenReturn(expectedEvent);

        //when
        Optional<Event> event = sut.findById(id);

        //then
        Assert.assertTrue(event.isPresent());
        Assert.assertEquals(id, event.get().getId());
    }

    /**
     * testing findById method from EventServiceImpl.
     * expect that event would not be found by id
     */
    @Test
    public void shouldReturnAbsentInCaseWrongIdSpecified() {
        //given
        final int id = 100;
        Optional<Event> absentEvent = Optional.empty();
        when(eventDAOMock.findById(id)).thenReturn(absentEvent);

        //when
        Optional<Event> event = sut.findById(id);

        //then
        Assert.assertFalse(event.isPresent());
    }

    /**
     * Testing updateEvent from EventServiceImpl.
     * Updating event with id=0.
     * Checking if changed fields are updated and we updated only one entry in DB.
     */
    @Test
    public void shouldUpdateEvent() {
        //given
        final int id = 0;
        final String newName = "Ballet";
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        final String newDateTime = "2015-12-23 11:51:19.152";

        Event updatedEvent = Event.builder(newName).
                user(User.builder("Vasya", "vasya@vasya.com").build()).
                id(id).
                city(newCity).
                location(newLocation).
                eventTime(LocalDateTime.parse(newDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build();
        when(eventDAOMock.updateEvent(updatedEvent)).thenReturn(1);

        //when
        int updatedEntries = sut.updateEvent(updatedEvent);

        //then
        Assert.assertEquals(1, updatedEntries);
    }

    /**
     * Testing updateEvent from EventServiceImpl.
     * Updating event with id=-1.
     * Checking if zero entries in DB are updated.
     */
    @Test
    public void shouldReturnZeroInCaseWrongIdSpecified() {
        //given
        final int id = 100;
        final String newName = "Ballet";
        final LocalDateTime newDateTime = LocalDateTime.now();

        Event updatedEvent = Event.builder(newName).
                user(User.builder("Vasya", "vasya@vasya.com").build()).
                id(id).
                eventTime(newDateTime).build();
        when(eventDAOMock.updateEvent(updatedEvent)).thenReturn(0);

        //when
        int updatedEntries = sut.updateEvent(updatedEvent);

        //then
        Assert.assertEquals(0, updatedEntries);
    }

    @Test
    public void shouldAddEvent() {

        //given
        final String name = "My event";
        final String username = "admin";
        Event event =  Event.builder(name).build();

        Mockito.when(eventDAOMock.addEvent(event,username)).thenReturn(event);

        //when
        sut.createEvent(event,username);

        //then
        //no exception is thrown
    }


    /**
     * Method for getting prepared event list
     * @return List of expected Events
     */
    private static List<Event> getExpectedEventList() {
        final String firstEventName = "EPAM fanfest 1";
        final String secondEventName = "EPAM fanfest 2";
        final LocalDateTime firstCreationTime = LocalDateTime.parse("2015-09-11T15:00");
        final LocalDateTime secondCreationTime = LocalDateTime.parse("2005-09-11T15:00");
        final String username = "Vasya";
        final String email = "vasya@vasya.com";
        final User user = User.builder(username, email).build();
        final Event firstEvent = Event.builder(firstEventName).id(0).creationTime(firstCreationTime).user(user).build();
        final Event secondEvent = Event.builder(secondEventName).id(1).creationTime(secondCreationTime).user(user).build();
        final List<Event> expectedEventList = new ArrayList<>();
        expectedEventList.add(firstEvent);
        expectedEventList.add(secondEvent);
        return expectedEventList;
    }

    /**
     * Testing getOrderedEvents method from EventServiceImpl.
     * Looking for events that were created before specified time.
     * Checking if we've got less or equal than amount events.
     */
    @Test
    public void shouldReturnEventListWithSortedEventsAndNumberOfAllEvents() {
        //given
        final int amount = 2;
        final QueryMode queryMode = QueryMode.BEFORE;
        final LocalDateTime effectiveTime = LocalDateTime.now();
        final List<Event> expectedEventList = getExpectedEventList();

        when(eventDAOMock.getOrderedEvents(effectiveTime, amount, queryMode)).thenReturn(expectedEventList);

        //when
        List<Event> eventList = sut.getOrderedEvents(effectiveTime, queryMode);

        //then
        Assert.assertTrue(eventList.size() <= amount);
    }

    /**
     * Testing getNumberOfNewEvents method from EventServiceImpl.
     * Looking for number of events that were created after specified time.
     * Checking if we've got expected number of events.
     */
    @Test
    public void shouldReturnNumberOfNewEvents() {
        final int expectedNumber = 1;
        final LocalDateTime after = LocalDateTime.parse("2005-09-11T15:00");

        when(eventDAOMock.getNumberOfNewEvents(after)).thenReturn(expectedNumber);

        //when
        int numberOfNewEvents = sut.getNumberOfNewEvents(after);

        //then
        Assert.assertEquals(expectedNumber, numberOfNewEvents);
    }
}