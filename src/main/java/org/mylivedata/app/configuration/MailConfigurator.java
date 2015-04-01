package org.mylivedata.app.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("file:${mail.property.location}")
public class MailConfigurator {
	
	@Value("${mail.name}")
    private String userName;
    @Value("${mail.psw}")
    private String password;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(465);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(password);
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.socketFactory.port", "465");
        mailProperties.setProperty("mail.smtp.host", "smtp.gmail.com");
        mailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProperties.setProperty("mail.smtp.port", "465");
        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }
	
}
