package org.mylivedata.app.dashboard.repository;

import org.mylivedata.app.dashboard.domain.DefaultLayoutsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface DefaultLayoutsEntityRepository extends JpaRepository<DefaultLayoutsEntity, Integer> {

    DefaultLayoutsEntity findByNameAndFragment(String name, String fragment);

}
