package org.mylivedata.app.dashboard.domain.custom;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lubo08 on 15.10.2014.
 */

public class OnlineUsersView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7273712873934864586L;
	private Integer status;
    private Integer isOnline;
    private Integer departmentId;
    private Integer isChatHistory;
    private Integer isInChat;
    private Integer isOperator;
    private Integer isHttpOnline;
    private String ip;
    private Timestamp sessionStart;
    private Timestamp sessionEnd;
    private String browser;
    private String screenResolution;
    private String system;
    private String countryCode;
    private String countryName;

    public OnlineUsersView(Integer status,
                           Integer isOnline,
                           Integer departmentId,
                           Integer isChatHistory,
                           Integer isInChat,
                           Integer isOperator,
                           Integer isHttpOnline,
                           String ip,
                           Date sessionStart,
                           Date sessionEnd,
                           String browser,
                           String screenResolution,
                           String system,
                           String countryCode,
                           String countryName
    ) {
        this.status = status;
        this.isChatHistory = isChatHistory;
        this.isOnline = isOnline;
        this.departmentId = departmentId;
        this.isInChat = isInChat;
        this.isOperator = isOperator;
        this.isHttpOnline = isHttpOnline;
        this.ip = ip;
        this.sessionStart = new Timestamp(sessionStart.getTime());
        this.sessionEnd = new Timestamp(sessionEnd.getTime());
        this.browser = browser;
        this.screenResolution = screenResolution;
        this.system = system;
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getIsChatHistory() {
        return isChatHistory;
    }

    public void setIsChatHistory(Integer isChatHistory) {
        this.isChatHistory = isChatHistory;
    }

    public Integer getIsInChat() {
        return isInChat;
    }

    public void setIsInChat(Integer isInChat) {
        this.isInChat = isInChat;
    }

    public Integer getIsOperator() {
        return isOperator;
    }

    public void setIsOperator(Integer isOperator) {
        this.isOperator = isOperator;
    }

    public Integer getIsHttpOnline() {
        return isHttpOnline;
    }

    public void setIsHttpOnline(Integer isHttpOnline) {
        this.isHttpOnline = isHttpOnline;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(Timestamp sessionStart) {
        this.sessionStart = sessionStart;
    }

    public Timestamp getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(Timestamp sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
