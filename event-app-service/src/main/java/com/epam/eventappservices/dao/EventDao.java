package com.epam.eventappservices.dao;

import com.epam.eventappservices.domain.*;

import java.util.List;

/**
 * Created by gennady on 02.12.15.
 */
public interface EventDao {

    public Event findById(int id);

}
