package org.mylivedata.app.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableAutoConfiguration
@Profile({ "dev", "demo" })
public class EmbeddedDatabaseConfiguration {
	@Bean(name = "dataSource")
    public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        driverManagerDataSource.setUrl("jdbc:h2:mem:mylivedata;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS mylivedata");
        return driverManagerDataSource;
    }
}
