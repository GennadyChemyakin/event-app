package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.config.TestDataAccessConfig;
import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * Class provides methods for testing UserDAOImpl. Use DataAccessConfig.class for creating context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataAccessConfig.class})
public class UserDAOITCase extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UserDAO userDAO;

    /**
     * testing findByUsername method from UserDAOImpl.
     * looking for user with username = "username". Checking if it is not null and user's username is equal to expected username
     */
    @Test
    public void shouldFindUserByUsername() {
        //given
        final String username = "username";
        //when
        Optional<User> user = userDAO.findByUsername(username);
        //then
        Assert.assertNotNull(user.get());
        Assert.assertEquals(user.get().getUsername(), username);
    }


    /**
     * testing findByUsername method from UserDAOImpl.
     * expect that user would not be found by username
     */
    @Test
    public void shouldReturnAbsentInCaseWrongIdSpecified() {
        //given
        final String username = "NotAValidUsername";
        //when
        Optional<User> user = userDAO.findByUsername(username);
        //then
        Assert.assertEquals(false, user.isPresent());
    }
}
