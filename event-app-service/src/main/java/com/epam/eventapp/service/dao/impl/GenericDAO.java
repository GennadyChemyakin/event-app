package com.epam.eventapp.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;

/**
 * GenericDAO class for extending by other DAO
 */
public class GenericDAO extends NamedParameterJdbcDaoSupport {


    /**
     * injecting dataSource
     * @param dataSource
     */
    @Autowired
    public void setDS(DataSource dataSource){
        super.setDataSource(dataSource);
    }
}
