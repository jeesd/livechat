package org.mylivedata.app.dashboard.repository;

import java.util.List;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface AccountsEntityRepository extends JpaRepository<AccountsEntity, Long> {

    List<AccountsEntity> findByAccountId(Long accountId);
    List<AccountsEntity> findByIsVeriefiedAndRegistrationCode(Byte isVerified, String registrationCode);
    AccountsEntity findByAccountIdentity(String accountIdentity);
}
