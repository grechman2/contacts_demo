package org.grechman.contacts_storage.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DbConfig {

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) throws SQLException {
        tryToCreateSchema(dataSource);
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDropFirst(true);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema("contacts");
        liquibase.setChangeLog("classpath:/db/changelog/changelog-master.xml");
        return liquibase;
    }

    private void tryToCreateSchema(DataSource dataSource) throws SQLException {
        String CREATE_SCHEMA_QUERY = "CREATE SCHEMA IF NOT EXISTS contacts";
        dataSource.getConnection().createStatement().execute(CREATE_SCHEMA_QUERY);
    }
}
