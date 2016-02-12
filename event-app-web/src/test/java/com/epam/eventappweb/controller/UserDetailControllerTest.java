package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Denys_Iakibchuk on 2/12/2016.
 */
public class UserDetailControllerTest {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private RegistrationController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void shouldGetUserDetailsByName() throws Exception {

        //given
        final String userName   = "UserTest";
        final String userEmail  = "UserTest@mail.ru";
        final String country    = "Russia";

        User user = User.builder(userName,userEmail).country(country).build();

        when(userServiceMock.getUserByUserName(userName)).thenReturn(Optional.of(user));

        //when
        ResultActions resultActions = mockMvc.perform(get("/profile")
                .param("name",userName));

        //then
        resultActions.andExpect(jsonPath("$.userName").value(userName))
                .andExpect(jsonPath("$.email").value(userEmail))
                .andExpect(jsonPath("$.country").value(country))
        ;

    }

}
