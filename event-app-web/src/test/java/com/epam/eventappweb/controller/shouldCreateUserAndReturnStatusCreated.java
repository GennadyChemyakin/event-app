package com.epam.eventappweb.controller;

import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import com.epam.eventappweb.model.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * test Class for RegistrationController
 */
public class shouldCreateUserAndReturnStatusCreated {

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

    public static class MockSecurityContext implements SecurityContext {

        private static final long serialVersionUID = -1386535243513362694L;

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }


    @Test
     public void shouldReturnStatusCreated() throws Exception {

        final String userName   = "UserTest";
        final String userEmail  = "UserTest@mail.ru";
        final String password   = "12345678";



        UserVO userVO = UserVO.builder(userName,userEmail)
                .password(password)
                .build();

        User user = User.builder(userName,userEmail)
                .password(password)
                .build();

        Mockito.doNothing().when(userServiceMock).createUser(user);

        //given
        String jsonObj = new ObjectMapper().writeValueAsString(userVO);
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(userName,password);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(principal));

        //when
        ResultActions resultActions = mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObj)
                .session(session)
        );

        //then
        resultActions.andExpect(status().isCreated());

    }

}
