package org.mylivedata.app.configuration;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.CometDServlet;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.mylivedata.app.connection.security.ChatSecurity;
import org.mylivedata.app.connection.session.ChatSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
@EnableAutoConfiguration
public class WebsocketConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(WebsocketConfiguration.class);

    @Value("${key.store.path}")
    private String keyStorePath;
    @Value("${key.store.password}")
    private String keyStorePassword;
    @Value("${key.manager.password}")
    private String keyManagerPassword;
    @Value("${https.port}")
    private int httpsPort = 8443;
    @Value("${http.port}")
    private int httpPort = 8080;

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
                Connector[] conn = new Connector[1];

                if(keyStorePath != null) {
                    conn = new Connector[2];
                    HttpConfiguration https = new HttpConfiguration();
                    https.addCustomizer(new SecureRequestCustomizer());

                    SslContextFactory sslContextFactory = new SslContextFactory();
                    sslContextFactory.setKeyStorePath(keyStorePath);
                    sslContextFactory.setKeyStorePassword(keyStorePassword);
                    sslContextFactory.setKeyManagerPassword(keyManagerPassword);

                    NetworkTrafficServerConnector sslConnector
                            = new NetworkTrafficServerConnector(
                            server,
                            new HttpConnectionFactory(https),
                            sslContextFactory
                    );
                    sslConnector.setPort(httpsPort);
                    conn[1] = sslConnector;
                }
                NetworkTrafficServerConnector httpConnector = new NetworkTrafficServerConnector(server);
                httpConnector.setPort(httpPort);
                conn[0] = httpConnector;
                server.setConnectors(conn);

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
        bean.setSecurityPolicy(chatSecurity());
        bean.addListener(chatSessionListener());
        bean.addExtension(new org.cometd.server.ext.AcknowledgedMessagesExtension());
        bean.setOption(ServletContext.class.getName(), servletContext);
        bean.setOption("ws.cometdURLMapping", "/cometd/*"); 
        return bean;
    }

    @Bean
    public ChatSecurity chatSecurity() {
        ChatSecurity chatSecurity = new ChatSecurity();
        return chatSecurity;
    }

    @Bean
    public ChatSessionListener chatSessionListener() {
        return new ChatSessionListener();
    }

}
