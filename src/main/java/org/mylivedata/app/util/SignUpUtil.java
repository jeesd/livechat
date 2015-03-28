package org.mylivedata.app.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.AssocUserGroupsEntity;
import org.mylivedata.app.dashboard.domain.UserConnection;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.custom.RegistrationFormDataView;
import org.mylivedata.app.dashboard.repository.AccountsEntityRepository;
import org.mylivedata.app.dashboard.repository.AssocUserGroupsEntityRepository;
import org.mylivedata.app.dashboard.repository.UserConnectionRepository;
import org.mylivedata.app.dashboard.repository.service.AccountService;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpUtil {
	
    private final Logger LOGGER = LoggerFactory.getLogger(SignUpUtil.class);
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
	private AccountsEntityRepository accountRepository;
    @Autowired
	private UserConnectionRepository userConnectionRepository;
    @Autowired
    private AssocUserGroupsEntityRepository assocUserGroupsEntityRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void signUpNewAccount(RegistrationFormDataView userView, String registrationUuid) throws Exception {
        LOGGER.info("SignUp user registration started");

        AccountsEntity account = new AccountsEntity();
        account.setBillingEmail(userView.getEmail());
        account.setIsVeriefied(new Byte("0"));
        account.setRegistrationCode(registrationUuid);
        account.setCompanyName(userView.getName().trim()!=null?userView.getName().trim():userView.getEmail().split("@")[0]);
        
        try {
			account = accountService.save(account);
		} catch (Exception e) {
	        LOGGER.info("Error saving account: " + e.getMessage());
            throw new Exception(e);
		}

        LOGGER.info("SignUp user registration ended");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public AccountsEntity signUpNewAccount(Connection<?> connection) throws Exception {
        LOGGER.info("SignUp social user registration started");

    	String email = (connection.fetchUserProfile().getEmail() != null) ? connection.fetchUserProfile().getEmail() : "";
    	
        AccountsEntity account = new AccountsEntity();
        account.setCompanyName(connection.fetchUserProfile().getName());
        account.setIsVeriefied(new Byte("0"));
        account.setRegistrationCode(UUID.randomUUID().toString());
		account.setBillingEmail(email);
        
        try {
			account = accountService.save(account);
		} catch (Exception e) {
	        LOGGER.info("Error saving scial account: " + e.getMessage());
            throw new Exception(e);
		}

        LOGGER.info("SignUp social user registration ended");
        
        return account;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public UsersEntity saveUser(AccountsEntity account, String password) throws Exception {
    	
    	UsersEntity user = new UsersEntity();
        user.setName(account.getCompanyName());
        user.setEmail(account.getBillingEmail());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setAccountId(account.getAccountId());
        user.setIsCredentialsNonExpired("true");
        user.setLastLogin(new Timestamp(new Date().getTime()));
        user.setRegistration(new Timestamp(new Date().getTime()));
        user.setImageUrl("img/avatar/user.jpg");

        try {
			user = userService.save(user);
		} catch (Exception e) {
			LOGGER.info("Error when saving user : " + e.getMessage());
            throw new Exception(e);
		}
        
        return user;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
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
        user.setImageUrl(connection.getImageUrl());

        //if (user != null)
        	//throw new Exception();
        
        try {
			user = userService.save(user);
		} catch (Exception e) {
			LOGGER.info("Error when saving user : " + e.getMessage());
            throw new Exception(e);
		}
        
        return user;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void saveAssocUserGroups(UsersEntity user) throws Exception {
    	
        AssocUserGroupsEntity assocUserGroupsEntity = new AssocUserGroupsEntity();
        assocUserGroupsEntity.setUserId(user.getUserId());
        assocUserGroupsEntity.setGroupId(1);
    	
        try {
            assocUserGroupsEntityRepository.saveAndFlush(assocUserGroupsEntity);
		} catch (Exception e) {
			LOGGER.info("Error when saving assoc user group : " + e.getMessage());
            throw new Exception(e);
		}
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void saveAccountOwner(AccountsEntity account, Integer ownerId) throws Exception {

        account.setOwnerId(ownerId);
        account.setIsVeriefied(new Byte("1"));
        account.setAccountIdentity(UUID.randomUUID().toString());
    	
        try {
        	accountService.save(account);
		} catch (Exception e) {
			LOGGER.info("Error when saving acount owner : " + e.getMessage());
            throw new Exception(e);
		}
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void saveSocialConnection(Integer userId, Connection<?> connection) {

        UserConnection userConnection = new UserConnection();
        userConnection.setUserId(userId);
        userConnection.setProviderId(connection.getKey().getProviderId());
        userConnection.setProviderUserId(connection.getKey().getProviderUserId());
        
        userConnectionRepository.saveAndFlush(userConnection);
    }
}
