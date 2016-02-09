package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.config.TestDataAccessConfig;
import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.EventNotCreatedException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Class provides methods for testing EventDAOImpl. Use DataAccessConfig.class for creating context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataAccessConfig.class})
public class EventDAOITCase extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * testing findById method from EventDAOImpl.
     * looking for event with id = 0. Checking if it is not null and event id is equal to expected id
     */
    @Test
    public void shouldFindEventById() {
        //given
        final int id = 0;
        //when
        Optional<Event> event = eventDAO.findById(id);
        //then
        Assert.assertNotNull(event.get());
        Assert.assertEquals(event.get().getId(), id);
    }


    /**
     * testing findById method from EventDAOImpl.
     * expect that event would not be found by id
     */
    @Test
    public void shouldReturnAbsentInCaseWrongIdSpecified() {
        //given
        final int id = 1;
        //when
        Optional<Event> event = eventDAO.findById(id);
        //then
        Assert.assertEquals(false, event.isPresent());
    }

    /**
     * Testing updateEventById from EventDAOImpl.
     * Updating event with id=0.
     * Checking if changed fields are updated and we updated only one entry in DB.
     */
    @Test
    public void shouldUpdateEventById() {
        //given
        final int id = 0;
        final String newName = "Ballet";
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        final String newDateTime = "2015-12-23 11:51:19.152";

        final String whereClause = "id=" + id + " and name='" + newName + "' and city='" + newCity + "' and address='" +
                newLocation + "' and event_time={ts '" + newDateTime + "'}";

        Event updatedEvent = Event.builder(newName).
                user(User.builder("Vasya", "vasya@vasya.com").build()).
                id(id).
                city(newCity).
                location(newLocation).
                timeStamp(LocalDateTime.parse(newDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build();

        //when
        int updatedEntries = eventDAO.updateEventById(updatedEvent);

        //then
        Assert.assertEquals(1, updatedEntries);
        Assert.assertEquals(updatedEntries, countRowsInTableWhere("event", whereClause));
    }

    /**
     * Testing updateEventById from EventDAOImpl.
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

        //when
        int updatedEntries = eventDAO.updateEventById(updatedEvent);

        //then
        Assert.assertEquals(0, updatedEntries);
    }

    /**
     * Testing addEvent from EventDAOImpl.
     * Passing the username which is not expected in database.
     * Checking if EventNotCreatedException throws.
     */
    @Test(expected = EventNotCreatedException.class)
    public void shouldThrowEventNotCreatedExceptionInCaseUsernameEmpty() {
        //given
        final String name = "My event";
        final String username  = "";
        //when
        Event event = Event.builder(name).build();

        eventDAO.addEvent(event,username);

        //then
        //throws exception
        Assert.fail("EventNotCreatedException should be thrown");
    }

    /**
     * Testing addEvent from EventDAOImpl.
     * Adding user in order event to be created
     * and then adding event.
     * No exceptions should be thrown.
     */
    @Test
    public void shouldAddEvent() {
        //given
        final String userName  = "Admin1";
        final String email     = "admin1@email.com";
        final String pass      = "11111";
        final String eventName = "test event";

        User user = User.builder(userName,email)
                .password(pass).build();

        Event event = Event.builder(eventName)
                .build();

        userDAO.createUser(user);

        //when
        Event newEvent = eventDAO.addEvent(event, userName);

        //then
        Assert.assertNotEquals(0,newEvent.getId());

    }

    @Test(expected=EventNotCreatedException.class)
    public void shouldThrowEventNotCreatedException() {

        //given
        final String eventName = "test event";
        final String userName  = "Admin";

        Event event = Event.builder(eventName)
                .build();

        //when
        Event newEvent = eventDAO.addEvent(event, userName);

        //then
        //Exception should be thrown
    }

}
