package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.InvalidIdentifierException;
import com.epam.eventapp.service.exceptions.ObjectNotCreatedException;
import com.epam.eventapp.service.exceptions.ObjectNotFoundException;
import com.epam.eventapp.service.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;


/**
 * Unit tests for {@link UserService}
 */
public class UserServiceTest {

    @Mock
    private UserDAO userDAOMock;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Testing  method createUser()
     */
    @Test
    public void userCreatedTest() {

        //given
        User user = User.builder("Danil", "Danya@mail.com").build();
        Mockito.doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then
        //no exception is thrown

    }


    @Test(expected = ObjectNotCreatedException.class)
    public void shouldThrowObjectCreatedExceptionIfDataAccessException() {

        //given
        User user = User.builder("Danil", "Danya@mail.com").build();
        Mockito.doThrow(ObjectNotCreatedException.class).doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if data does not get to the table
        Assert.fail("ObjectNotCreatedException is expected to be thrown");

    }

    @Test(expected = InvalidIdentifierException.class)
    public void shouldThrowExceptionIfUserNameExistsInTheDatabase() {

        //given
        User user = User.builder("Danil", "Danya@mail.com").build();
        when(userDAOMock.isUserNameRegistered(user.getUsername())).thenReturn(true);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if UserName is in db
        Assert.fail("InvalidIdentifierException is expected to be thrown");

    }

    @Test(expected = InvalidIdentifierException.class)
    public void shouldThrowExceptionIfEmailExistsInTheDatabase() {
        //given
        User user = User.builder("Danil", "Danya@mail.com").build();
        when(userDAOMock.isEmailRegistered(user.getEmail())).thenReturn(true);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if Email is in db
        Assert.fail("InvalidIdentifierException is expected to be thrown");

    }

    /**
     * testing getUserByUsername method form UserServiceImpl
     * looking for user with username = 'username'.
     * Checking if it is not null and user username is equal to expected username
     */
    @Test
    public void shouldReturnUserByUsername() {
        //given
        final String username = "username";
        final String email = "email";
        User expectedUser = User.builder(username, email).build();
        when(userDAOMock.getUserByUsername(username)).thenReturn(expectedUser);

        //when
        User user = userService.getUserByUsername(username);

        //then
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getUsername(), username);
        Assert.assertEquals(user.getEmail(), email);
    }

    /**
     * testing getUserByUsername method form
     * looking for user with username = 'wrong username'.
     * expect ObjectNotFoundException
     */
    @Test(expected = ObjectNotFoundException.class)
    public void shouldThrowObjectNotFoundException() {
        //given
        final String username = "wrong username";

        when(userDAOMock.getUserByUsername(username)).thenThrow(ObjectNotFoundException.class);

        //when
        userService.getUserByUsername(username);

        //then
        Assert.fail("ObjectNotFoundException should be thrown");

    }

    /**
     * Test for checking userService will return number of updated
     * rows in user table.
     */
    @Test
    public void shouldUpdateUserPhoto() {

        //given
        final String username = "username";
        final String photoLink = "\\\\EPRUPETW0518\\images\\users\\username";
        when(userDAOMock.updateUserPhotoURL(username, photoLink)).thenReturn(1);

        //when
        int rowsUpdated = userService.updateUserPhotoByUsername(username, photoLink);

        //then
        Assert.assertEquals(1, rowsUpdated);

    }


}
