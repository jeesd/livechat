package org.mylivedata.app;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class LiveChatApplication {

    private static Logger logger = LoggerFactory.getLogger(LiveChatApplication.class);

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(LiveChatApplication.class, args);
        if(ctx.getEnvironment().getProperty("h2.console").equals("true")){
			DriverManagerDataSource dm = (DriverManagerDataSource)ctx.getBean("dataSource");
			try {
                logger.info("Starting H2 console");
                Server.startWebServer(dm.getConnection());
			} catch (SQLException e) {
                logger.error(e.getMessage());
			}
		}
	}
	
}
