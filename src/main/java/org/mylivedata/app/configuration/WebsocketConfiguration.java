package org.mylivedata.app.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.CometDServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.NetworkTrafficServerConnector;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class WebsocketConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(WebsocketConfiguration.class);
	
	@Bean
	public JettyEmbeddedServletContainerFactory servletContainerFactory() {
		JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
		
		factory.addServerCustomizers(new JettyServerCustomizer(){
			@Override
			public void customize(Server server) {
				try {
		    		WebSocketServerContainerInitializer.configureContext((WebAppContext)server.getHandler());
				} catch (ServletException e) {
					logger.error(e.getMessage());
				}
				/*
				HttpConfiguration https = new HttpConfiguration();
		    	https.addCustomizer(new SecureRequestCustomizer());
		    	
		    	SslContextFactory sslContextFactory = new SslContextFactory();
		    	sslContextFactory.setKeyStorePath("D:/MYWORK/keystore");
		    	sslContextFactory.setKeyStorePassword("test123456");
		    	sslContextFactory.setKeyManagerPassword("test123456");

	            NetworkTrafficServerConnector sslConnector 
	    		= new NetworkTrafficServerConnector(
	    				server,
	    				new HttpConnectionFactory(https),
	    				sslContextFactory
	    		);
	            sslConnector.setPort(8443);
	            server.setConnectors(new Connector[] { sslConnector });
	            */
			}			
		});		
		return factory;
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		CometDServlet cometdServlet = new CometDServlet();
		ServletRegistrationBean registration = new ServletRegistrationBean(cometdServlet,"/cometd/*");
		registration.setAsyncSupported(true);
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean filterRegistration() {
		FilterRegistrationBean filter = new FilterRegistrationBean();
		CrossOriginFilter crossOriginFilter = new CrossOriginFilter();
		filter.setFilter(crossOriginFilter);
		filter.setAsyncSupported(true);
		filter.setName("cross-origin");
		filter.addUrlPatterns("/cometd/*","/chat/template/*");
		return filter;
	}
	
	@Bean
	public ServletContextInitializer initializer() {
	    return new ServletContextInitializer() {
	        @Override
	        public void onStartup(ServletContext servletContext) throws ServletException {
	            servletContext.setAttribute(BayeuxServer.ATTRIBUTE, bayeuxServer(servletContext));
	        }
	    };
	}
	
	@Bean
    public BayeuxServer bayeuxServer(ServletContext servletContext)
    {
		BayeuxServerImpl bean = new BayeuxServerImpl();
        bean.addExtension(new org.cometd.server.ext.AcknowledgedMessagesExtension());
        bean.setOption(ServletContext.class.getName(), servletContext);
        bean.setOption("ws.cometdURLMapping", "/cometd/*"); 
        return bean;
    }
	
}
