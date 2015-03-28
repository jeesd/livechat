package org.mylivedata.app.dashboard.repository.service.implementation;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.repository.service.SignUpUserService;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;

@Service
public class SignUpUserServiceImpl implements SignUpUserService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignUpUserServiceImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	@Override
	public UsersEntity saveUser(AccountsEntity account, String password) throws Exception {
		
    	UsersEntity user = new UsersEntity();
        user.setName(account.getCompanyName());
        user.setEmail(account.getBillingEmail());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setAccountId(account.getAccountId());
        user.setIsCredentialsNonExpired("true");
        user.setLastLogin(new Timestamp(new Date().getTime()));
        user.setRegistration(new Timestamp(new Date().getTime()));
        
        try {
			user = userService.save(user);
		} catch (Exception e) {
			LOGGER.info("Error when saving user : " + e.getMessage());
            throw new Exception(e);
		}
        
        return user;
	}

	@Override
	public UsersEntity saveUser(Connection<?> connection, String email, Integer accountId) throws Exception {
        
		SecureRandom random = new SecureRandom();
		String password = new BigInteger(130, random).toString(32);  
		
		UsersEntity user = new UsersEntity();
		user.setName(connection.fetchUserProfile().getFirstName());
        user.setSurrname(connection.fetchUserProfile().getLastName());
		user.setEmail(email);
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setAccountId(accountId);
		user.setIsCredentialsNonExpired("true");
		user.setLastLogin(new Timestamp(new Date().getTime()));
		user.setRegistration(new Timestamp(new Date().getTime()));
        user.setImageUrl(connection.createData().getImageUrl());
		try {
			user = userService.save(user);
		} catch (Exception e) {
			LOGGER.info("Error when saving social user : " + e.getMessage());
		    throw new Exception(e);
		}
		
		return user;
	}

}
