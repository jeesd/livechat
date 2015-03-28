package org.mylivedata.app.dashboard.repository.service;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.custom.CreateUserFormDataView;
import org.springframework.social.connect.Connection;


/**
 * Created by lubo08 on 24.3.2014.
 */
public interface SignUpService {

    public UsersEntity confirmRegistrationAndCreateUser(CreateUserFormDataView userView, AccountsEntity account) throws Exception;

	public void signUpByUserSocial(Connection<?> connection, String email) throws Exception;
	
    public AccountsEntity getRegistrationByTokenAndVerify(String registrationCode) throws Exception;

    public void removeAccount(AccountsEntity account) throws Exception;

    public UsersEntity getUserByEmail(String email) throws Exception;
}
