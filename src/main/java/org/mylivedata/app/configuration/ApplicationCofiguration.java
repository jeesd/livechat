package org.mylivedata.app.configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.mylivedata.app.configuration.resolver.DbTemplateResolver;
import org.mylivedata.app.configuration.resource.DatabaseDrivenMessageSource;
import org.mylivedata.app.dashboard.repository.service.LayoutService;
import org.mylivedata.app.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
@EnableAutoConfiguration
public class ApplicationCofiguration {
	
	@Autowired
    private SpringTemplateEngine templateEngine;
	
	@Bean
	public SessionUtils sessionUtils() {
		return new SessionUtils();
	}
	
	@PostConstruct
    public void extension() {
        templateEngine.addTemplateResolver(dbTemplateResolver());
    }
	
	@Bean
	public DbTemplateResolver dbTemplateResolver() {
		DbTemplateResolver resolver = new DbTemplateResolver();
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(2);
        resolver.setCacheable(false);
		return resolver;
	}
	
//	@Bean
//	public ReloadableResourceBundleMessageSource defaultMessageSource() {
//		ReloadableResourceBundleMessageSource defaultMessageSource = new ReloadableResourceBundleMessageSource();
//		defaultMessageSource.setBasename("messages");
//		defaultMessageSource.setDefaultEncoding("utf-8");
//		return defaultMessageSource;
//	}
//	
//	
//	@Bean
//	public MessageSource messageSource() {
//		DatabaseDrivenMessageSource dbMessageSource = new DatabaseDrivenMessageSource();
//		dbMessageSource.setParentMessageSource(defaultMessageSource());
//		return dbMessageSource;
//	}
	
	
}
