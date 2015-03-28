package org.mylivedata.app.dashboard.repository;

import org.mylivedata.app.dashboard.domain.DepartmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface DepartmentsEntityRepository extends JpaRepository<DepartmentsEntity, Integer> {

    DepartmentsEntity findByKeyNameAndAccountId(String departmentKey, int accountId);

}
