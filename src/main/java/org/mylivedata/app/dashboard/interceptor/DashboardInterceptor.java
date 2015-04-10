package org.mylivedata.app.dashboard.interceptor;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.social.connect.Connection;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by lubo08 on 2.4.2015.
 */
public class DashboardInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(DashboardInterceptor.class);

    @Autowired
    private SessionUtils sessionUtils;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String ajaxHeader = request.getHeader("X-Requested-With");
        SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext != null && securityContext.getAuthentication().isAuthenticated()) {
            SecureUser secUser = (SecureUser) securityContext.getAuthentication().getPrincipal();
            if (!"XMLHttpRequest".equals(ajaxHeader)) {

                String userImageUrl = "img/user.jpg";
                // get image from social context
                if (securityContext.getAuthentication() instanceof SocialAuthenticationToken) {
                    SocialAuthenticationToken socialAuthToken = (SocialAuthenticationToken) securityContext.getAuthentication();
                    Connection<?> connection = socialAuthToken.getConnection();
                    userImageUrl = connection.createData().getImageUrl();
                }

                modelAndView.addObject("langCountry", RequestContextUtils.getLocale(request));
                modelAndView.addObject("userName", secUser.getFirstName() + " " + (secUser.getLastName() != null ? secUser.getLastName() : ""));
                modelAndView.addObject("userImageUrl", userImageUrl);
                modelAndView.addObject("userEmail", secUser.getEmail());
                int visitorsCount = sessionUtils.getOnlineVisitorsCount(secUser.getAccountId().longValue());
                int operatorsCount = sessionUtils.getOnlineOperatorsCount(secUser.getAccountId().longValue());
                modelAndView.addObject("visitorsCount", visitorsCount);
                modelAndView.addObject("operatorsCount", operatorsCount);
                logger.debug("Default dashboard info set for user " + secUser.getEmail());
            }
        }
    }

}
