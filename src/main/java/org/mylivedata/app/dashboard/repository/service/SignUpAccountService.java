package org.mylivedata.app.dashboard.repository.service;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.custom.RegistrationFormDataView;
import org.springframework.social.connect.Connection;


public interface SignUpAccountService {

    public AccountsEntity signUpNewAccount(RegistrationFormDataView userView, String registrationUuid) throws Exception;
    
    public AccountsEntity signUpNewAccount(Connection<?> connection) throws Exception;

}
