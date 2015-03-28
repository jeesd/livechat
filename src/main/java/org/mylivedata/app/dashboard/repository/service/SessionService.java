package org.mylivedata.app.dashboard.repository.service;

import org.mylivedata.app.dashboard.domain.UserSessionsEntity;


/**
 * Created by lubo08 on 18.9.2014.
 */
public interface SessionService {
    public UserSessionsEntity registerNewSession(UserSessionsEntity session) throws Exception;
    public void endSession(int sessionId) throws Exception;
}
