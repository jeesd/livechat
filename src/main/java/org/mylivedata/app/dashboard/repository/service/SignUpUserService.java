package org.mylivedata.app.dashboard.repository.service;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.springframework.social.connect.Connection;



public interface SignUpUserService {

    public UsersEntity saveUser(AccountsEntity account, String password) throws Exception;
    
    public UsersEntity saveUser(Connection<?> connection, String email, Integer accountId) throws Exception;
    
}
