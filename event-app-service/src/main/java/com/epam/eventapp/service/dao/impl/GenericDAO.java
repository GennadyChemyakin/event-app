package com.epam.eventapp.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * GenericDAO class for extending by other DAO
 */
@Repository
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
