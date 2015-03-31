package org.mylivedata.app.configuration;

import javax.annotation.PostConstruct;

import org.mylivedata.app.configuration.resolver.DbTemplateResolver;
import org.mylivedata.app.configuration.resource.DatabaseDrivenMessageSource;
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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;

@Configuration
@EnableAutoConfiguration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	
	@Autowired
    private SpringTemplateEngine templateEngine;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return sessionLocaleResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

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
