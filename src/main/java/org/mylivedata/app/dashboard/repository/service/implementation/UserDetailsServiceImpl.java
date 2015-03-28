package org.mylivedata.app.dashboard.repository.service.implementation;

import javax.servlet.http.HttpServletRequest;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContextUtils;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecureUser user = userService.getSecureUserByEmail(username);
        user.setLocale(RequestContextUtils.getLocale(request));
        return user;
    }
}
