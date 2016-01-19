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
    public void shouldReturnUserCreated() {
        //given
        User user = User.builder("Danil","Danya@mail.com").build();
        when(userDAOMock.createUser(user)).thenReturn(1);

        //when
        int rows = userService.createUser(user);

        //then
        Assert.assertNotEquals(0, rows);

    }

    @Test
    public void shouldNotCreateUser(){

        //given
        User user = User.builder("Danil", "").build();
        when(userDAOMock.createUser(user)).thenReturn(0);

        //when
        int rows = userService.createUser(user);

        //then
        Assert.assertEquals(0,rows);

    }


}
