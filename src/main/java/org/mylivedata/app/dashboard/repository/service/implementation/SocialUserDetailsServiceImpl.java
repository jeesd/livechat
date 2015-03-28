package org.mylivedata.app.dashboard.repository.service.implementation;

import javax.servlet.http.HttpServletRequest;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.servlet.support.RequestContextUtils;

//@Service
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        SecureUser userDetails = userService.getSecureUserById(Integer.parseInt(userId));
        userDetails.setLocale(RequestContextUtils.getLocale(request));
        return (SocialUserDetails) userDetails;
    }
}