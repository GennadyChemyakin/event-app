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
import org.mockito.MockitoAnnotations;

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
        Optional<Event> expectedEvent = Optional.of(Event.builder(User.builder("Ivan", "ivan@gmail.com").build(), "Party").build());
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

}
