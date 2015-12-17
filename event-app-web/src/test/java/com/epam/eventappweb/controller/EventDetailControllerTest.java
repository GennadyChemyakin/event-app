package com.epam.eventappweb.controller;

import com.epam.eventapp.service.dao.EventDAO;
import com.epam.eventapp.service.domain.Event;
import com.epam.eventapp.service.domain.User;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * test Class for EventDetailController
 */
public class EventDetailControllerTest {


    /**
     * testing getEventDetail from EventDetailController
     * mock eventDAO than inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect JSON with right fields
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnEventAsJSON() throws Exception {

        final int id = 0;
        //create expected event that will be returned by eventDAO
        Optional<Event> expectedEvent = Optional.of(Event.builder(User.builder("Ivan", "ivan@gmail.com").build(), "Party").build());
        //mock eventDAO
        EventDAO eventDAOMock = mock(EventDAO.class);
        when(eventDAOMock.findById(id)).thenReturn(expectedEvent);
        //inject eventDAO mock into controller
        EventDetailController controller = new EventDetailController(eventDAOMock);
        MockMvc mockMvc = standaloneSetup(controller).build();


        //assert expectations
        mockMvc.perform(get("/event/" + id)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", Matchers.is(0))).
                andExpect(jsonPath("$.user.username", Matchers.is("Ivan"))).
                andExpect(jsonPath("$.user.email", Matchers.is("ivan@gmail.com"))).
                andExpect(jsonPath("$.name", Matchers.is("Party")));
    }


    /**
     * testing getEventDetail from EventDetailController
     * mock eventDAO than inject it to controller. Using mockMvc to assert the behaviour of controller.
     * expect 404 status code
     *
     * @throws Exception
     */
    @Test
    public void shouldReturn404IfEventNotFound() throws Exception {
        final int id = 1;
        //create empty Optional
        Optional<Event> emptyEvent = Optional.empty();
        //mock eventDAO
        EventDAO eventDAOMock = mock(EventDAO.class);
        when(eventDAOMock.findById(id)).thenReturn(emptyEvent);
        //inject eventDAO mock into controller
        EventDetailController controller = new EventDetailController(eventDAOMock);
        MockMvc mockMvc = standaloneSetup(controller).build();

        //assert expectations
        mockMvc.perform(get("/event/" + id)).andExpect(status().isNotFound());
    }
}
