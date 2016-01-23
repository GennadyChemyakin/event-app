package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.EmailExistsInTheDatabase;
import com.epam.eventapp.service.exceptions.UserNameExistsInTheDatabase;
import com.epam.eventapp.service.exceptions.UserNotCreatedException;
import com.epam.eventapp.service.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Mock
    private UserDAO userDAOMock;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp(){
        userService = new UserServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void shouldExpectUserIdChanged() {
        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        Mockito.doReturn(user.builder("Danil","Danya@mail.com").id(1).build()).doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then
        Assert.assertNotEquals(0, user.getId());

    }

    @Test(expected = UserNotCreatedException.class)
    public void shouldThrowExceptionIfWrongDataSend() {

        //given
        User user = User.builder("Danil","Danya@mail.com").build();

        //when
        Mockito.doThrow(UserNotCreatedException.class).doNothing().when(userDAOMock).createUser(user);

        //then
        userService.createUser(user);

    }

    @Test(expected = UserNameExistsInTheDatabase.class)
    public void shouldThrowUserNameExistsInTheDatabase() {

        //given
        User user = User.builder("Danil","Danya@mail.com").build();

        //when
        Mockito.doThrow(UserNameExistsInTheDatabase.class).doNothing().when(userDAOMock).createUser(user);

        //then
        userService.createUser(user);

    }

    @Test(expected = EmailExistsInTheDatabase.class)
    public void shouldThrowEmailExistsInTheDatabase() {

        //given
        User user = User.builder("Danil","Danya@mail.com").build();

        //when
        Mockito.doThrow(EmailExistsInTheDatabase.class).doNothing().when(userDAOMock).createUser(user);

        //then
        userService.createUser(user);

    }



}
