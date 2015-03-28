package org.mylivedata.app.dashboard.repository.service.implementation;

import org.mylivedata.app.dashboard.domain.DepartmentsEntity;
import org.mylivedata.app.dashboard.repository.DepartmentsEntityRepository;
import org.mylivedata.app.dashboard.repository.OnlineUsersRepository;
import org.mylivedata.app.dashboard.repository.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lubo08 on 2.11.2014.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentsEntityRepository departmentsEntityRepository;
    @Autowired
    private OnlineUsersRepository onlineUsersRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public boolean setDepartment(String departmentKey, int accountId, int userId) throws Exception {
        DepartmentsEntity departmentsEntity = departmentsEntityRepository.findByKeyNameAndAccountId(departmentKey,accountId);
        if(departmentsEntity != null){
            onlineUsersRepository.updateDepartmentId(departmentsEntity.getDepartmentId(),userId);
            return true;
        }
        return false;
    }
}
