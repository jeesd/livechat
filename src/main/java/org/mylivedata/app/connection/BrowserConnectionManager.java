package org.mylivedata.app.connection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lubo08 on 11.11.2014.
 */
public class BrowserConnectionManager implements Serializable {

    private List<SessionInfo> sessionInfos = new ArrayList<>();
    private String connectionType;
    private boolean isDashboardConnection;


    public boolean isDashboardConnection() {
        return isDashboardConnection;
    }

    public void setDashboardConnection(boolean isDashboardConnection) {
        this.isDashboardConnection = isDashboardConnection;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public List<SessionInfo> getSessionInfos() {
        return sessionInfos;
    }

    public void setSessionInfos(List<SessionInfo> sessionInfos) {
        this.sessionInfos = sessionInfos;
    }

    public void addSessionId(SessionInfo sessionId){
        if(this.sessionInfos == null){
            this.sessionInfos = new ArrayList<>();
        }
        this.sessionInfos.add(sessionId);
    }

    public void updateUserHashId (String sessionId, String userHashId) {
        if(this.sessionInfos != null) {
            for(SessionInfo sinfo: this.sessionInfos){
                if(sinfo.getCometSessionId().equals(sessionId)){
                    this.sessionInfos.remove(sinfo);
                    this.sessionInfos.add(new SessionInfo(sessionId,userHashId));
                    return;
                }
            }
        }
    }

    public void removeSessionId(String sessionId){
        if(this.sessionInfos != null) {
            for(SessionInfo sinfo: this.sessionInfos){
                if(sinfo.getCometSessionId().equals(sessionId)){
                    this.sessionInfos.remove(sinfo);
                    return;
                }
            }
        }
    }

}
