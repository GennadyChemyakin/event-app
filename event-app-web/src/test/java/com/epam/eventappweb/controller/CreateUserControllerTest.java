package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * test Class for RegistrationController
 */
public class CreateUserControllerTest {

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
    @Ignore
    public void shouldReturnStatusCreated() throws Exception {

        final String userName   = "UserTest";
        final String userEmail  = "UserTest@mail.ru";
        final String password   = "12345678";

        final HttpServletRequest req = mock(HttpServletRequest.class);

        UserVO userVO = UserVO.builder(userName,userEmail)
                .password(password)
                .build();

        User user = User.builder(userName,userEmail)
                .password(password)
                .build();

        Mockito.doNothing().when(userServiceMock).createUser(user);
        Mockito.doNothing().when(req).login(userName,password);

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
