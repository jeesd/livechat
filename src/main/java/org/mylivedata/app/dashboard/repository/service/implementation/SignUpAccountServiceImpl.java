package org.mylivedata.app.dashboard.repository.service.implementation;

import java.util.UUID;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.custom.RegistrationFormDataView;
import org.mylivedata.app.dashboard.repository.service.AccountService;
import org.mylivedata.app.dashboard.repository.service.SignUpAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;

@Service
public class SignUpAccountServiceImpl implements SignUpAccountService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignUpAccountServiceImpl.class);
    
    @Autowired
    private AccountService accountService;
    
	@Override
	public AccountsEntity signUpNewAccount(RegistrationFormDataView userView, String registrationUuid) throws Exception {
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
        
        return account;
	}

	@Override
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
}
