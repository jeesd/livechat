package org.mylivedata.app.dashboard.repository;

import java.util.List;

import org.mylivedata.app.dashboard.domain.DomainsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface DomainsEntityRepository extends JpaRepository<DomainsEntity, Long> {

    List<DomainsEntity> findByAccountId(Long domainId);
    List<DomainsEntity> findByDomain(String domain);

}
