package org.mylivedata.app.dashboard.repository.service.implementation;

import java.sql.Timestamp;
import java.util.Date;

import org.mylivedata.app.dashboard.domain.Ip2LocationDb11Entity;
import org.mylivedata.app.dashboard.domain.UserSessionsEntity;
import org.mylivedata.app.dashboard.repository.Ip2LocationDb11Repository;
import org.mylivedata.app.dashboard.repository.UserSessionsRepository;
import org.mylivedata.app.dashboard.repository.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lubo08 on 18.9.2014.
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private UserSessionsRepository userSessionsRepository;
    @Autowired
    private Ip2LocationDb11Repository ip2LocationDb11Repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public UserSessionsEntity registerNewSession(UserSessionsEntity session) throws Exception {

        if(session.getIp().equals("127.0.0.1")){
            session.setIp("85.237.227.38");
        }

        String[] ipArray = session.getIp().replace("/", "").split("\\.");
        // convert ip to integer number for now only if IP is IPv4, do not forget add support for IPv16
        int decimalIpForm = 0;
        if(ipArray.length == 4){
            decimalIpForm = ipToInt(ipArray);
        }
        Ip2LocationDb11Entity geoData = ip2LocationDb11Repository.findGeoLocation(new Long(decimalIpForm));
        if(geoData != null) {
            session.setCountryCode(geoData.getCountryCode());
            session.setCountryName(geoData.getCountryName());
        }

        UserSessionsEntity savedSession = userSessionsRepository.saveAndFlush(session);

        return savedSession;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void endSession(int sessionId) throws Exception {
        userSessionsRepository.updateSessionEndTimestamp(new Timestamp(new Date().getTime()),sessionId);
    }

    private int ipToInt(String[] bytes)
    {
        int compacted = 0;
        compacted = (Integer.parseInt(bytes[0])<<24) + (Integer.parseInt(bytes[1])<<16) + (Integer.parseInt(bytes[2])<<8) + Integer.parseInt(bytes[3]);
        return compacted;
    }
}
