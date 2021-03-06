package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.config.TestDataAccessConfig;
import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import com.epam.eventapp.service.exceptions.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Class provides methods for testing UserDAOImpl. Use DataAccessConfig.class for creating context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataAccessConfig.class})
public class UserDAOITCase extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void shouldAddUserInTheTable() {
        final String userName = "Vasya12";
        final String userMail = "Vasya12@vasya.com";
        final String password = "123";

        User user = User.builder(userName, userMail)
                .password(password)
                .build();

        int rowsBefore = countRowsInTable("SEC_USER");

        userDAO.createUser(user);

        int rowsAfter  = countRowsInTable("SEC_USER");
        Assert.assertNotEquals(rowsBefore, rowsAfter);

    }

    /**
     * testing method getUserByUsername from UserDAOImpl
     * expect that found user is not null and has expected username
     */
    @Test
    public void shouldReturnUserByUsername(){
        //given
        final String username = "username";

        //when
        User user = userDAO.getUserByUsername(username);

        //then
        Assert.assertNotNull(user);
        Assert.assertEquals(username, user.getUsername());
    }

    /**
     * testing method getUserByUsername from UserDAOImpl
     * expect ObjectNotFoundException thrown
     */
    @Test(expected = ObjectNotFoundException.class)
    public void shouldThrowExceptionInCaseOfWrongUsername(){
        //given
        final String username = "wrongUsername";

        //when
        User user = userDAO.getUserByUsername(username);

        //then
        Assert.fail("ObjectNotFoundException not thrown");
    }

    /**
     * Test for checking that photo link will be updated
     */
    @Test
    public void shouldUpdateUserPhoto(){

        //given
        final String username = "username";
        final String photoLink = "\\\\EPRUPETW0518\\images\\users\\username";

        //when
        int rowsUpdated = userDAO.updateUserPhotoURL(username, photoLink);

        //then
        Assert.assertEquals(1,rowsUpdated);

    }

}
