package org.mylivedata.app.dashboard.repository.service.implementation;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.DomainsEntity;
import org.mylivedata.app.dashboard.domain.custom.AccountFormView;
import org.mylivedata.app.dashboard.repository.AccountsEntityRepository;
import org.mylivedata.app.dashboard.repository.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountsEntityRepository accountsEntityRepository;
    
    @Override
    public AccountsEntity save(AccountsEntity account) throws Exception {

        try {
            account = accountsEntityRepository.saveAndFlush(account);
        }catch (Exception e){
            LOGGER.info("Cannot save account " + e.getMessage());
            throw new Exception(e);
        }
        
        return account;
    }

	@Override
	public void delete(AccountsEntity account) throws Exception {
        try {
            accountsEntityRepository.delete(account);
        }catch (Exception e){
            LOGGER.info("Cannot delete account " + e.getMessage());
            throw new Exception(e);
        }
	}

    @Override
    public void updateAccountFormView(AccountFormView account) throws Exception {
        AccountsEntity acc = new AccountsEntity();
        acc.setAccountId(44);
        acc.setCompanyName("test2");
        accountsEntityRepository.saveAndFlush(acc);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public boolean checkAccountIdVsUrl(String url, String accountIdentity) throws Exception {
        AccountsEntity accountsEntity = accountsEntityRepository.findByAccountIdentity(accountIdentity);
        for(DomainsEntity dom: accountsEntity.getDomainsesByAccountId()){
            if(dom.getDomain().equals(url))
                return true;
        }
        return false;
    }

}
