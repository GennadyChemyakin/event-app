package com.epam.eventapp.service.service.impl;

import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Test fot UserService class
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
    public void shoudCreateUserAndReturnNewRowsCount() {
        //given
        User user = User.builder("Danil", "Danya@mail.com").build();
        when(userDAOMock.createUser(user)).thenReturn(1);

        //when
        int rows = userService.createUser(user);

        //then
        Assert.assertNotEquals(0, rows);

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

}
