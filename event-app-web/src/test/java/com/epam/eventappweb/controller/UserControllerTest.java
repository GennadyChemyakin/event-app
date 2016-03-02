package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


/**
 * test Class for UserController
 */
public class UserControllerTest {

    @Mock
    private UserService userServiceMock;

    private MockMvc mockMvc;

    @InjectMocks
    private UserController sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(sut).build();
    }

    /**
     * testing getLoggedUserDetails from UserController
     * expect body with right username
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnUsernameOfLoggedUser() throws Exception {
        //given
        final String username = "username";

        //when
        ResultActions resultActions = mockMvc.perform(get("/user/current").
                principal(new TestingAuthenticationToken(username, null)));

        //then
        resultActions.andExpect(status().isOk()).
                andExpect(jsonPath("$.username", is(username)));

    }

    /**
     * testing getLoggedUserDetails from UserController
     * expect body with empty username
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnNullInCaseNoLoggedUser() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/user/current"));

        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.username", is("")));
    }

    /**
     * testing user controller for getting user details by
     * sending username as a parameter.
     * @throws Exception
     */
    @Test
    public void shouldGetUserByName() throws Exception {

        //given
        final String userName   = "UserTest";
        final String userEmail  = "UserTest@mail.ru";
        final String country    = "Russia";

        User user = User.builder(userName,userEmail).country(country).build();

        Mockito.when(userServiceMock.getUserByUsername(userName)).thenReturn(user);

        //when
        ResultActions resultActions = mockMvc.perform(get("/user")
                .param("username",userName));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userName))
                .andExpect(jsonPath("$.email").value(userEmail))
                .andExpect(jsonPath("$.country").value(country));

    }

}
