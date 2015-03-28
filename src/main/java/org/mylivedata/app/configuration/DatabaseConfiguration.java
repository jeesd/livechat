package org.mylivedata.app.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableCaching
@EnableAutoConfiguration
@Profile({ "production", "online" })
public class DatabaseConfiguration {
	
	@Bean(name = "dataSource")
    public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/mylivedata?useUnicode=true&amp;characterEncoding=UTF8&amp;characterSetResults=UTF8");
        driverManagerDataSource.setUsername("root");
        //driverManagerDataSource.setPassword("");
        return driverManagerDataSource;
    }
	
	
}
