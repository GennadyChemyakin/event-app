package com.epam.eventapp.service.service.impl;

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
    public void setUp(){
        sut = new EventServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * testing findById method from EventServiceImpl.
     * looking for event with id = 0. Checking if it is not null and event id is equal to expected id
     */
    @Test
    public void shouldReturnEventById(){
        //given
        final int id = 0;
        Optional<Event> expectedEvent = Optional.of(Event.builder("Party").build());
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
   public void shouldReturnAbsentInCaseWrongIdSpecified(){
        //given
        final int id = 1;
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
                timeStamp(LocalDateTime.parse(newDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build();
        when(eventDAOMock.updateEventById(updatedEvent)).thenReturn(1);

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
        final int id = -1;
        final String newName = "Ballet";
        final LocalDateTime newDateTime = LocalDateTime.now();

        Event updatedEvent = Event.builder(newName).
                user(User.builder("Vasya", "vasya@vasya.com").build()).
                id(id).
                timeStamp(newDateTime).build();
        when(eventDAOMock.updateEventById(updatedEvent)).thenReturn(0);

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

}

