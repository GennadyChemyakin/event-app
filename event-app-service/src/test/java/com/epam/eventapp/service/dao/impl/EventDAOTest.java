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
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Class provides methods for testing EventDAOImpl. Use DataAccessConfig.class for creating context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataAccessConfig.class})
//@ActiveProfiles("test")
public class EventDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private EventDAO eventDAO;

    /**
     * testing findById method from EventDAOImpl.
     * looking for event with id = 0. Checking if it is not null and event id is equal to expected id
     */
    @Test
    public void shouldFindEventById() {
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
        final String newName = "Ballet";
        final String newCity = "Moscow";
        final String newLocation = "Kremlin";
        final String newDateTime = "23-DEC-15 11.51.19.152000000 AM";
        DateTimeFormatterBuilder fmb = new DateTimeFormatterBuilder();
        fmb.parseCaseInsensitive();
        fmb.append(DateTimeFormatter.ofPattern("dd-MMM-yy HH.mm.ss[.SSSSSSSSS] a"));
        final String whereClause = "id=" + id + " and name='" + newName + "' and city='" + newCity + "' and address='" +
                newLocation + "' and event_time='" + newDateTime + "'";

        Event updatedEvent = Event.builder(User.builder("Vasya", "vasya@vasya.com").build(), newName).
                id(id).
                city(newCity).
                location(newLocation).
                timeStamp(LocalDateTime.parse(newDateTime, fmb.toFormatter())).build();

        //when
        int updatedEntries = eventDAO.updateEventById(updatedEvent);

        //then
        Assert.assertEquals(1, updatedEntries);
        Assert.assertEquals(updatedEntries, countRowsInTableWhere("event", whereClause));
    }

    @Test
    /**
     * Testing updateEventById from EventDAOImpl.
     * Updating event with id=-1.
     * Checking if zero entries in DB are updated.
     */
    public void shouldReturnZeroInCaseWrongIdSpecified() {
        //given
        final int id = -1;
        final String newName = "Ballet";
        final LocalDateTime newDateTime = LocalDateTime.now();

        Event updatedEvent = Event.builder(User.builder("Vasya", "vasya@vasya.com").build(), newName).
                id(id).
                timeStamp(newDateTime).build();

        //when
        int updatedEntries = eventDAO.updateEventById(updatedEvent);

        //then
        Assert.assertEquals(0, updatedEntries);
    }

}
