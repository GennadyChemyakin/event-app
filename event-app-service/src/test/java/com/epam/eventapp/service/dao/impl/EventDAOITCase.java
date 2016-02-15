package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.conditions.QueryMode;
import com.epam.eventapp.service.config.TestDataAccessConfig;
import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.hamcrest.core.Every;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;

/**
 * Class provides methods for testing EventDAOImpl. Use DataAccessConfig.class for creating context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataAccessConfig.class})
public class EventDAOITCase extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private EventDAO eventDAO;

    /**
     * testing findById method from EventDAOImpl.
     * looking for event with id = 0. Checking if it is not null and event id is equal to expected id
     */
    @Test
    public void shouldFindEventById() {
        //given
        final int id = 1;
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
        final int id = -1;
        //when
        Optional<Event> event = eventDAO.findById(id);
        //then
        Assert.assertEquals(false, event.isPresent());
    }

    /**
     * Testing updateEvent from EventDAOImpl.
     * Updating event with id=1.
     * Checking if changed fields are updated and we updated only one entry in DB.
     */
    @Test
    public void shouldUpdateEventById() {
        //given
        final int id = 1;
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
                eventTime(LocalDateTime.parse(newDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).build();

        //when
        int updatedEntries = eventDAO.updateEvent(updatedEvent);

        //then
        Assert.assertEquals(1, updatedEntries);
        Assert.assertEquals(updatedEntries, countRowsInTableWhere("event", whereClause));
    }

    /**
     * Testing updateEvent from EventDAOImpl.
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
                eventTime(newDateTime).build();

        //when
        int updatedEntries = eventDAO.updateEvent(updatedEvent);

        //then
        Assert.assertEquals(0, updatedEntries);
    }

    /**
     * Testing getOrderedEvents method from EventDAOImpl.
     * Looking for events that were created after specified time.
     * Checking if we've got less or equal than amount events and that creationTime of each of these these events
     * before specified time.
     */
    @Test
    public void shouldGetEventListBeforeTime() {
        //given
        final int amount = 3;
        final LocalDateTime specifiedTime = LocalDateTime.parse("2015-07-11T15:00");
        final QueryMode queryMode = QueryMode.BEFORE;

        //when
        List<Event> eventList = eventDAO.getOrderedEvents(specifiedTime, amount, queryMode);

        //then
        Assert.assertNotNull(eventList);
        Assert.assertTrue(eventList.size() <= amount);
        Assert.assertThat(eventList, Every.everyItem(hasProperty("creationTime", lessThan(specifiedTime))));
    }

    /**
     * Testing getOrderedEvents method from EventDAOImpl.
     * Looking for events that were created after specified time.
     * Checking if we've got less or equal than amount events and that creationTime of each of these these events
     * after specified time.
     */
    @Test
    public void shouldGetEventListAfterTime() {
        //given
        final int amount = 3;
        final LocalDateTime specifiedTime = LocalDateTime.parse("2015-09-11T15:00");
        final QueryMode queryMode = QueryMode.AFTER;

        //when
        List<Event> eventList = eventDAO.getOrderedEvents(specifiedTime, amount, queryMode);

        //then
        Assert.assertNotNull(eventList);
        Assert.assertTrue(eventList.size() <= amount);
        Assert.assertThat(eventList, Every.everyItem(hasProperty("creationTime", greaterThan(specifiedTime))));
    }

    /**
     * Testing getNumberOfNewEvents method from EventDAOImpl.
     * Looking for number of events that were created after specified time.
     * Checking if number of events is the same as expected number.
     */
    @Test
    public void shouldGetNumberOfEventsAfter() {
        //given
        final LocalDateTime specifiedTime = LocalDateTime.parse("2015-09-11T15:00");
        final int expectedNumber = 2;

        //when
        int numberOfEvents = eventDAO.getNumberOfNewEvents(specifiedTime);

        //then
        Assert.assertEquals(expectedNumber, numberOfEvents);
    }
}
