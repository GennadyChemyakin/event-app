package com.epam.eventapp.service.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


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
    @Profile("dev")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
        dataSource.setUrl(environment.getRequiredProperty("db.url"));
        dataSource.setUsername(environment.getRequiredProperty("db.username"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));
        return dataSource;
    }

    /**
     * data source bean that represents in-memory derbyDB
     */
    @Bean
    @Profile("test")
    public DataSource embeddedDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.DERBY)
                .addScript("sql/create.db.sql")
                .addScript("sql/insert.data.sql")
                .build();
        return db;
    }

    /**
     * Transaction manager for the configured datasource.
     *
     * @return Transaction manager.
     */
    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
