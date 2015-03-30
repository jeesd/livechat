package org.mylivedata.app.configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.mylivedata.app.configuration.resolver.DbTemplateResolver;
import org.mylivedata.app.configuration.resource.DatabaseDrivenMessageSource;
import org.mylivedata.app.dashboard.repository.service.LayoutService;
import org.mylivedata.app.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
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
	
	@Bean
	public ReloadableResourceBundleMessageSource defaultMessageSource() {
		ReloadableResourceBundleMessageSource defaultMessageSource = new ReloadableResourceBundleMessageSource();
		defaultMessageSource.setBasename("classpath:/messages");
		defaultMessageSource.setDefaultEncoding("utf-8");
		return defaultMessageSource;
	}
	
	
	@Bean
	public MessageSource messageSource() {
		DatabaseDrivenMessageSource dbMessageSource = new DatabaseDrivenMessageSource();
		dbMessageSource.setParentMessageSource(defaultMessageSource());
		return dbMessageSource;
	}

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error403");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error404");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/customerror");
            container.addErrorPages(error403Page, error404Page, error500Page);
        });
    }

	
}
