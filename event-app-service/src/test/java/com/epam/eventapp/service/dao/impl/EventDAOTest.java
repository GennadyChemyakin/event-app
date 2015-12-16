package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.config.DataAccessConfig;
import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Class provides methods for testing EventDAOImpl. Use DataAccessConfig.class for creating context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataAccessConfig.class})
@ActiveProfiles("test")
public class EventDAOTest {

    @Autowired
    private EventDAO eventDAO;

    /**
     * testing findById method from EventDAOImpl.
     * looking for event with id = 0. Checking if it is not null and event id is equal to expected id
     */
    @Test
    public void shouldFindEventById(){
        //given
        final int id = 0;
        //when
        Optional<Event> event = eventDAO.findById(0);
        //then
        Assert.assertNotNull(event.get());
        Assert.assertEquals(event.get().getId(), id);
    }


    /**
     * testing findById method from EventDAOImpl.
     * expect NoSuchElementException when we use nonexistent id
     */
    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionIfEventNotFoundById(){
        //given
        final int id = 1;
        //when
        Optional<Event> event = eventDAO.findById(id);
        //then
        Assert.assertNotNull(event.get());
    }

    @Test
    /**
     * Testing updateEventById from EventDAOImpl.
     * Updating event with id=0.
     * Checking if changed fields are updated and we updated only one entry in DB.
     */
    public void shouldUpdateEventById() {
        //given
        final int id = 0;
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        Optional<Event> event = eventDAO.findById(id);
        Event updatedEvent = Event.builder(event.get().getUser(), event.get().getName()).
                id(event.get().getId()).
                description(event.get().getDescription()).
                country(event.get().getCountry()).
                city(newCity).
                location(newLocation).
                gpsLatitude(event.get().getGpsLatitude()).
                gpsLongitude(event.get().getGpsLongitude()).
                timeStamp(event.get().getTimeStamp()).build();
        //when
        int updatedEnries = eventDAO.updateEventById(updatedEvent);
        event = eventDAO.findById(id);
        //then
        Assert.assertEquals(newCity, event.get().getCity());
        Assert.assertEquals(newLocation, event.get().getLocation());
        Assert.assertEquals(1, updatedEnries);

    }
}
