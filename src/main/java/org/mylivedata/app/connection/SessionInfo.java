package org.mylivedata.app.connection;

/**
 * Created by lubo08 on 13.2.2015.
 */
public class SessionInfo {

    public SessionInfo (String cometSessionId,  String userHashId) {
        this.cometSessionId = cometSessionId;
        this.userHashId = userHashId;
    }


    public String getCometSessionId() {
        return cometSessionId;
    }

    public void setCometSessionId(String cometSessionId) {
        this.cometSessionId = cometSessionId;
    }

    public String getUserHashId() {
        return userHashId;
    }

    public void setUserHashId(String userHashId) {
        this.userHashId = userHashId;
    }

    private String cometSessionId;
    private String userHashId;


}
