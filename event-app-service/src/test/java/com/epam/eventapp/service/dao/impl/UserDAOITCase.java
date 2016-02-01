package com.epam.eventapp.service.dao.impl;

import com.epam.eventapp.service.config.TestDataAccessConfig;
import com.epam.eventapp.service.dao.UserDAO;
import com.epam.eventapp.service.domain.User;
import org.junit.Assert;
import org.junit.Ignore;
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

}
