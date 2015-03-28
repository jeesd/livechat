package org.mylivedata.app.dashboard.integration.service;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Created by lubo08 on 2.11.2014.
 */
@MessageEndpoint
public class DepartmentChangedService {

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentChangedService.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private DepartmentService departmentService;

    @ServiceActivator(inputChannel = "changeDepartment")
    public void changeDepartment(SecureUser userDetails, @Header(value = "DEPARTMENT", required = false) String departmentKey) throws Exception{
        departmentService.setDepartment(departmentKey,userDetails.getAccountId(),userDetails.getId().intValue());
        //Cache cache = cacheManager.getCache(SessionUtils.USER_CACHE_NAME);
        //SecureUser userFromCache = (SecureUser)cache.get(userDetails.getId()).get();
        LOGGER.debug("To DO "+departmentKey);
    }

}
