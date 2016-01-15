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

import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * test class for UserService
 */
public class UserServiceTest {
    @Mock
    private UserDAO userDAOMock;

    @InjectMocks
    private UserService sut;

    @Before
    public void setUp(){
        sut = new UserServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * testing findByUsername method from UserServiceImpl.
     * looking for user with username = "Ivan". Checking if it is not null and user's username is equal to expected username
     */
    @Test
    public void shouldReturnUserByUsername(){
        //given
        final String username = "Ivan";
        Optional<User> expectedUser = Optional.of(User.builder("Ivan", "ivan@gmail.com").build());
        when(userDAOMock.findByUsername(username)).thenReturn(expectedUser);

        //when
        Optional<User> user = sut.findByUsername(username);

        //then
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(username, user.get().getUsername());
    }

    /**
     * testing findByUsername method from UserServiceImpl.
     * expect that user would not be found by username
     */
    @Test
    public void shouldReturnAbsentInCaseWrongUsernameSpecified(){
        //given
        final String username = "Misha";
        Optional<User> absentUser = Optional.empty();
        when(userDAOMock.findByUsername(username)).thenReturn(absentUser);

        //when
        Optional<User> user = sut.findByUsername(username);

        //then
        Assert.assertFalse(user.isPresent());
    }
}
