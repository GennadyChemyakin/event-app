package com.epam.eventappweb.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


/**
 * test Class for UserController
 */
public class UserControllerTest {

    private MockMvc mockMvc;

    private UserController sut;

    @Before
    public void setUp() {
        sut = new UserController();
        mockMvc = standaloneSetup(sut).build();
    }

    /**
     * testing getLoggedUserDetails from UserController
     * expect body with wright username
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

}
