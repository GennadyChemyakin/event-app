package com.epam.eventapp.service.dao;

import com.epam.eventapp.service.domain.User;

import java.util.Optional;

/**
 * Created by Denys_Iakibchuk on 12/16/2015.
 */
public interface UserDAO {




    boolean createUser(User user);

}
