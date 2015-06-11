package org.mylivedata.app;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class LiveChatApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(LiveChatApplication.class, args);
        JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
        Job job = (Job)ctx.getBean("loadCsv");
        jobLauncher.run(job, new JobParameters());
        if(ctx.getEnvironment().getProperty("h2.console").equals("true")){
			DriverManagerDataSource dm = (DriverManagerDataSource)ctx.getBean("dataSource");
			try {
				Server.startWebServer(dm.getConnection());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
}
