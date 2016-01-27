package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.EmailExistsInTheDatabaseException;
import com.epam.eventapp.service.exceptions.UserNameExistsInTheDatabaseException;
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


/**
 * Unit tests for {@link UserService}
 */
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
    public void userSuccessfullyCreatedTest() {
        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        Mockito.doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then

    }

    @Test(expected = UserNotCreatedException.class)
    public void shouldThrowExceptionIfWrongDataSend() {

        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        Mockito.doThrow(UserNotCreatedException.class).doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if data does not get to the table
        Assert.fail("UserNotCreatedException is expected to be thrown");


    }

    @Test(expected = UserNameExistsInTheDatabaseException.class)
    public void shouldThrowUserNameExistsInTheDatabase() {

        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        Mockito.doThrow(UserNameExistsInTheDatabaseException.class).doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if UserName is in db
        Assert.fail("UserNameExistsInTheDatabaseException is expected to be thrown");

    }

    @Test(expected = EmailExistsInTheDatabaseException.class)
    public void shouldThrowEmailExistsInTheDatabase() {

        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        Mockito.doThrow(EmailExistsInTheDatabaseException.class).doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if Email is in db
        Assert.fail("EmailExistsInTheDatabaseException is expected to be thrown");

    }

}
