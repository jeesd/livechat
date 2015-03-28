package org.mylivedata.app.webchat.domain;


import eu.bitwalker.useragentutils.BrowserType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lubo08 on 4.11.2014.
 */
public class ChatUser implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2111037152473039839L;
	private long userId;
    private long accountId;
    private long sessionId;
    private List<String> chatSessionIds;
    private String identificationHash;
    private UserStatus status;
    private boolean isChatOnline;
    private boolean isHttpSessionOnline;
    private boolean isOperator;
    private boolean isMakingOrder;
    private boolean isShoppingCartNotEmpty;
    private Date connectedAt;
    private Date disconnectedAt;
    private String browser;
    private String system;
    private String countryCode;
    private String countryName;
    private String ip;
    private String longitude;
    private String latitude;
    private String department;
    private String currentUrl;
    private boolean isMobile;
    private BrowserType browserType;
    private String screenWH;

    public String getScreenWH() {
        return screenWH;
    }

    public void setScreenWH(String screenWH) {
        this.screenWH = screenWH;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public boolean isMakingOrder() {
        return isMakingOrder;
    }

    public void setMakingOrder(boolean isMakingOrder) {
        this.isMakingOrder = isMakingOrder;
    }

    public boolean isShoppingCartNotEmpty() {
        return isShoppingCartNotEmpty;
    }

    public void setShoppingCartNotEmpty(boolean isShoppingCartNotEmpty) {
        this.isShoppingCartNotEmpty = isShoppingCartNotEmpty;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public List<String> getChatSessionIds() {
        return chatSessionIds;
    }

    public void setChatSessionIds(List<String> chatSessionIds) {
        this.chatSessionIds = chatSessionIds;
    }

    public void addSessionId(String sessionId){
        if(chatSessionIds == null){
            chatSessionIds = new ArrayList<>();
        }
        chatSessionIds.add(sessionId);
    }

    public void removeSessionId(String sessionId){
        if(chatSessionIds != null)
            chatSessionIds.remove(sessionId);
    }

    public String getIdentificationHash() {
        return identificationHash;
    }

    public void setIdentificationHash(String identificationHash) {
        this.identificationHash = identificationHash;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isChatOnline() {
        return isChatOnline;
    }

    public void setChatOnline(boolean isChatOnline) {
        this.isChatOnline = isChatOnline;
    }

    public boolean isHttpSessionOnline() {
        return isHttpSessionOnline;
    }

    public void setHttpSessionOnline(boolean isHttpSessionOnline) {
        this.isHttpSessionOnline = isHttpSessionOnline;
    }

    public boolean isOperator() {
        return isOperator;
    }

    public void setOperator(boolean isOperator) {
        this.isOperator = isOperator;
    }

    public Date getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(Date connectedAt) {
        this.connectedAt = connectedAt;
    }

    public Date getDisconnectedAt() {
        return disconnectedAt;
    }

    public void setDisconnectedAt(Date disconnectedAt) {
        this.disconnectedAt = disconnectedAt;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
