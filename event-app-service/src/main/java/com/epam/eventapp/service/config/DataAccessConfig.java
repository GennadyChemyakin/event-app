package com.epam.eventapp.service.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;


import javax.sql.DataSource;

/**
 * Configuration for data access
 */
@Configuration
@PropertySource("classpath:eventappservice.properties")
@ComponentScan("com.epam.eventapp.service.dao.impl")
public class DataAccessConfig {

    @Autowired
    private Environment environment;

    /**
     * data source bean that represents our OracleDB
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
        dataSource.setUrl(environment.getRequiredProperty("db.url"));
        dataSource.setUsername(environment.getRequiredProperty("db.username"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));
        return dataSource;
    }
}
