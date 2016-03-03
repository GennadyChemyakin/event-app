package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.EmailAlreadyExistsException;
import com.epam.eventapp.service.exceptions.UserNameAlreadyExistsException;
import com.epam.eventapp.service.exceptions.UserNotCreatedException;
import com.epam.eventapp.service.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        User user = User.builder("Danil","Danya@mail.com").build();
        Mockito.doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then
        //no exception is thrown

    }


    @Test(expected = UserNotCreatedException.class)
    public void shouldThrowUserNotCreatedExceptionIfDataAccessException() {

        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        Mockito.doThrow(UserNotCreatedException.class).doNothing().when(userDAOMock).createUser(user);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if data does not get to the table
        Assert.fail("UserNotCreatedException is expected to be thrown");

    }

    @Test(expected = UserNameAlreadyExistsException.class)
    public void shouldThrowUserNameExistsInTheDatabase() {

        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        when(userDAOMock.isUserNameRegistered(user.getUsername())).thenReturn(true);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if UserName is in db
        Assert.fail("UserNameAlreadyExistsException is expected to be thrown");

    }

    /**
     * Method tests if email already in the database
     */
    @Test(expected = EmailAlreadyExistsException.class)
    public void shouldThrowEmailExistsInTheDatabase() {
        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        when(userDAOMock.isEmailRegistered(user.getEmail())).thenReturn(true);

        //when
        userService.createUser(user);

        //then
        // exception should be thrown if Email is in db
        Assert.fail("EmailAlreadyExistsException is expected to be thrown");

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

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowUserNotFoundException() {
        //given
        final String username  = "wrong username";
        User expectedUser      = User.builder().username(username).build();
        when(userDAOMock.getUserByUsername(username)).thenThrow(UsernameNotFoundException.class);

        //when
        User user = userService.getUserByUsername(username);

        //then
        Assert.fail("UserNotFoundException should be thrown");

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
        when(userDAOMock.updateUserPhotoURL(username,photoLink)).thenReturn(1);

        //when
        int rowsUpdated = userService.updateUserPhotoByUsername(username,photoLink);

        //then
        Assert.assertEquals(1,rowsUpdated);

    }


}
