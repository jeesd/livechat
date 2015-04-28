package org.mylivedata.app.configuration;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.mylivedata.app.configuration.filter.AjaxRedirectFilter;
import org.mylivedata.app.configuration.filter.VisitorFilter;
import org.mylivedata.app.dashboard.repository.UsersEntityRepository;
import org.mylivedata.app.dashboard.repository.service.implementation.MyPersistentTokenRepositoryImpl;
import org.mylivedata.app.dashboard.repository.service.implementation.SocialUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableWebMvcSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService customUserDetailsService;
    @Autowired
    private UsersEntityRepository userRepository;

    /* */
    @Autowired
    private PersistentTokenRepository rememberMeTokenRepository;

    private final static String generatedKey = "#..liveDataApp..879456#";//UUID.randomUUID().toString();


    @Bean
    public RememberMeServices rememberMeServices(){
        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(
                generatedKey, customUserDetailsService, rememberMeTokenRepository);
        rememberMeServices.setCookieName("1337180a-42d5-4a2c-a227-9f42b36d30c4");
        return rememberMeServices;
    }
	
    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider(){
        RememberMeAuthenticationProvider rememberMeAuthenticationProvider =
                new RememberMeAuthenticationProvider(generatedKey);
        return rememberMeAuthenticationProvider;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new MyPersistentTokenRepositoryImpl();
    }
    
	@Bean
    public VisitorFilter visitorFilter(){
        return new VisitorFilter();
    }
	
	@Bean
    public AjaxRedirectFilter ajaxRedirectFilter(){
        return new AjaxRedirectFilter();
    }
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("lubo").password("xxx").roles("USER").and().withUser("paul")
          //      .password("emu").roles("USER");

        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(rememberMeAuthenticationProvider());
        //auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/bootstrap/**",
                "/less/**",
                "/img/**",
                "/fonts/**",
                "/org/**",
                "/jquery/**",
                "/resources/**",
                "/change-avatar**",
                //"/safix/**",
                "/oauth/uncache_approvals", "/oauth/cache_approvals");
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
            http
                    //.addFilterBefore(cometdPreAuthenticatedProcessingFilter(authenticationManagerBean()), AnonymousAuthenticationFilter.class)
                    //.addFilterBefore(visitorFilter(), SecurityContextPersistenceFilter.class)
                    .addFilterAfter(ajaxRedirectFilter(), ExceptionTranslationFilter.class)
                    .formLogin()
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    .failureUrl("/signin?authentication_error=true")
                    .loginPage("/signin").permitAll()
                    .loginProcessingUrl("/login.do")
                    .defaultSuccessUrl("/dashboard", true)
                    .and()
                    .rememberMe()
                        .rememberMeServices(rememberMeServices())
                        .key(generatedKey)
                    .and()
            //.formLogin().loginPage("/login.jsp").permitAll().and()
            //.authorizeRequests().antMatchers("/login.jsp").permitAll().and()
                    .csrf()
                    .requireCsrfProtectionMatcher(new RequestMatcher() {
                        private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
                        private RegexRequestMatcher apiMatcher = new RegexRequestMatcher("/cometd/.*", null);
                        private RegexRequestMatcher apiMatcher2 = new RegexRequestMatcher("/cometd?.*", null);

                        @Override
                        public boolean matches(HttpServletRequest request) {
                            // No CSRF due to allowedMethod
                            if (allowedMethods.matcher(request.getMethod()).matches())
                                return false;

                            // No CSRF due to api call
                            if (apiMatcher.matches(request))
                                return false;
                            if (apiMatcher2.matches(request))
                                return false;


                            // CSRF for everything else that is not an API call or an allowedMethod
                            return true;
                        }
                    }).and()
                    .logout()
                    //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/signin")
                    .logoutUrl("/logout.do")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error403")
                    //.and().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
                    .and()
                    //.anonymous().authorities("ROLE_VISITOR").and()
            .authorizeRequests()
                //.antMatchers("/signup").permitAll()
                //.anyRequest().hasRole("USER") password
                .antMatchers("/signup", "/signup/**").permitAll()//.hasAnyRole("ANONYMOUS","VISITOR")
                .antMatchers("/error403", "/error403/**").permitAll()
                .antMatchers("/unsubscribe", "/unsubscribe/**").hasAnyRole("ANONYMOUS","VISITOR")
                .antMatchers("/password", "/password/**").hasAnyRole("ANONYMOUS","VISITOR")
                .antMatchers("/confirm", "/confirm/**").hasAnyRole("ANONYMOUS","VISITOR")
                .antMatchers("/confirmemail", "/confirmemail/**").hasAnyRole("ANONYMOUS","VISITOR")
                .antMatchers("/sign-up-my", "/sign-up-my/**").hasAnyRole("ANONYMOUS","VISITOR")
                .antMatchers("/dashboard", "/dashboard/**").hasRole("USER")
                .antMatchers("/cometd", "/cometd/**").hasAnyRole("ANONYMOUS","VISITOR","USER")
                .antMatchers("/chat/template", "/chat/template/**").hasAnyRole("ANONYMOUS","VISITOR","USER")
                //.antMatchers("/cometd", "/cometd/**").authenticated()
                //.antMatchers("/safix", "/safix/**").hasRole("VISITOR")
                //.antMatchers("/safix", "/safix/**").authenticated()
                .antMatchers("/visitor-list", "/visitor-list/**").hasRole("USER")
                .antMatchers("/visitor-list-table", "/visitor-list-table/**").hasRole("USER")
                .anyRequest().hasRole("USER")
            .and().apply(new SpringSocialConfigurer());
            ;
        // @formatter:on
    }
	
	@Bean
    public SocialUserDetailsServiceImpl socialUserDetailsService() {
        return new SocialUserDetailsServiceImpl();
    }
	
}
