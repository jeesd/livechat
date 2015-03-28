package org.mylivedata.app.dashboard.repository.service.implementation;

import java.util.List;
import java.util.UUID;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.AssocUserGroupsEntity;
import org.mylivedata.app.dashboard.domain.UserConnection;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.custom.CreateUserFormDataView;
import org.mylivedata.app.dashboard.repository.AccountsEntityRepository;
import org.mylivedata.app.dashboard.repository.AssocUserGroupsEntityRepository;
import org.mylivedata.app.dashboard.repository.UserConnectionRepository;
import org.mylivedata.app.dashboard.repository.UsersEntityRepository;
import org.mylivedata.app.dashboard.repository.service.AccountService;
import org.mylivedata.app.dashboard.repository.service.SignUpAccountService;
import org.mylivedata.app.dashboard.repository.service.SignUpService;
import org.mylivedata.app.dashboard.repository.service.SignUpUserService;
import org.mylivedata.app.exceptions.RegistrationNotFountException;
import org.mylivedata.app.util.SignInUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lubo08 on 24.3.2014.
 */
@Service("signUpService")
public class SignUpServiceImpl implements SignUpService {

    private final Logger LOGGER = LoggerFactory
            .getLogger(SignUpService.class);

    @Autowired
    private SignUpAccountService signUpAccountService;
    @Autowired
    private SignUpUserService signUpUserService;
    
    @Autowired
    private AccountService accountService;
    @Autowired
	private AccountsEntityRepository accountRepository;
    @Autowired
	private UserConnectionRepository userConnectionRepository;
    @Autowired
    private AssocUserGroupsEntityRepository assocUserGroupsEntityRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UsersEntityRepository usersEntityRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public UsersEntity confirmRegistrationAndCreateUser(CreateUserFormDataView userView, AccountsEntity account) throws Exception {
    	
    	UsersEntity user = null;
    	try {
    		user = signUpUserService.saveUser(account, userView.getPassword());
    		saveAssocUserGroups(user);
    		saveAccountOwner(account, user.getUserId());
    	} catch(Exception e) {
    		LOGGER.error("Error when confirm registration! " + e);
            throw new Exception(e);
    	}

        return user;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void signUpByUserSocial(Connection<?> connection, String email) throws Exception {

    	UsersEntity user = null;
    	try {
    		AccountsEntity account = signUpAccountService.signUpNewAccount(connection);
    		
    		user = signUpUserService.saveUser(connection, email, account.getAccountId());
    		saveAssocUserGroups(user);
    		saveAccountOwner(account, user.getUserId());
    		
    		saveSocialConnection(user.getUserId(), connection);
	        SignInUtil.signIn(user.getUserId().toString());
    	} catch(Exception e) {
    		LOGGER.error("Error sing up by user social " + e);
            throw new Exception(e);
    	}
    }
    
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
    
    public void saveAccountOwner(AccountsEntity account, Integer ownerId) throws Exception {

        account.setOwnerId(ownerId);
        account.setIsVeriefied(new Byte("1"));
        account.setRegistrationCode(null);
        account.setAccountIdentity(UUID.randomUUID().toString());
    	
        try {
        	accountService.save(account);
		} catch (Exception e) {
			LOGGER.info("Error when saving acount owner : " + e.getMessage());
            throw new Exception(e);
		}
    }
    
    public void saveSocialConnection(Integer userId, Connection<?> connection) throws Exception {

        UserConnection userConnection = new UserConnection();
        userConnection.setUserId(userId);
        userConnection.setProviderId(connection.getKey().getProviderId());
        userConnection.setProviderUserId(connection.getKey().getProviderUserId());
        
        userConnectionRepository.saveAndFlush(userConnection);
    }
    
    public AccountsEntity getRegistrationByTokenAndVerify(String registrationCode) throws Exception{
        AccountsEntity account = null;
        List<AccountsEntity> accountList = accountRepository.findByIsVeriefiedAndRegistrationCode(new Byte("0"), registrationCode);
        if(accountList.isEmpty())
            throw new RegistrationNotFountException();
        else
            account = accountList.iterator().next();

        return account;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void removeAccount(AccountsEntity account) throws Exception {
        try {
            accountService.delete(account);
        }catch (Exception e){
	        LOGGER.info("Error delete account: " + e.getMessage());
            throw new Exception(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public UsersEntity getUserByEmail(String email) throws Exception{
        UsersEntity user = null;
        List<UsersEntity> usersList = usersEntityRepository.findByEmail(email);
        if(!usersList.isEmpty())
            user = usersList.iterator().next();
        return user;
    }

}
