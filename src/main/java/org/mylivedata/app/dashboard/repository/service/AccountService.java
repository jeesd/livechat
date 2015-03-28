package org.mylivedata.app.dashboard.repository.service;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.custom.AccountFormView;


public interface AccountService {

	public AccountsEntity save(AccountsEntity account) throws Exception;

	public void delete(AccountsEntity account) throws Exception;

    public void updateAccountFormView(AccountFormView account) throws Exception;

    public boolean checkAccountIdVsUrl(String url, String accountIdentity) throws Exception;

}
