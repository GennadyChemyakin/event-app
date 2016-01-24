package com.epam.eventappweb.controller;

import com.epam.eventappweb.model.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * test Class for RegistrationController
 */
public class CreateUserControllerTest {

    @InjectMocks
    private RegistrationController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnStatusCreated() throws Exception {

        final String userName   = "UserTest";
        final String userEmail  = "UserTest@mail.ru";
        final String password   = "12345678";

        UserVO userVO = UserVO.builder(userName,userEmail)
                .password(password)
                .build();

        //given
        String jsonObj = new ObjectMapper().writeValueAsString(userVO);

        //when
        ResultActions resultActions = mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObj)
        );

        //then
        resultActions.andExpect(status().isCreated());

    }

}
