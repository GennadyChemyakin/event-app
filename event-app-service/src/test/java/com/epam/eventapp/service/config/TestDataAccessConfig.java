package com.epam.eventapp.service.config;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Configuration for testing data access
 */
@Configuration
@ComponentScan("com.epam.eventapp.service.dao.impl")
public class TestDataAccessConfig {

    @Bean
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
        return new DataSourceTransactionManager(embeddedDataSource());
    }

}
