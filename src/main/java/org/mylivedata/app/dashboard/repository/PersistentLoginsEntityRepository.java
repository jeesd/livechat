package org.mylivedata.app.dashboard.repository;

import java.util.List;

import org.mylivedata.app.dashboard.domain.PersistentLoginsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lubo08 on 5.4.2014.
 */
//@Repository
public interface PersistentLoginsEntityRepository extends JpaRepository<PersistentLoginsEntity, String> {

    PersistentLoginsEntity findBySeries(String series);
    List<PersistentLoginsEntity> findByUsername(String username);


}
