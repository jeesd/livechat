package org.mylivedata.app.dashboard.domain;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Created by lubo08 on 5.9.2014.
 */
@Entity
@Table(name = "user_sessions", schema = "", catalog = "mylivedata")
public class UserSessionsEntity {
    private Integer sessionId;
    private String ip;
    private Timestamp sessionStart;
    private Timestamp sessionEnd;
    private String browser;
    private String screenResolution;
    private String system;
    private String countryCode;
    private String countryName;
    private Integer userId;
    private Collection<OnlineUsersEntity> onlineUsersesBySessionId;
    private Collection<UserGeoDataEntity> userGeoDatasBySessionId;
    private Collection<UserPagesEntity> userPagesesBySessionId;
    private UsersEntity usersByUserId;

    @Id
    @GeneratedValue
    @Column(name = "session_id", nullable = false, insertable = true, updatable = true)
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "ip", nullable = true, insertable = true, updatable = true, length = 50)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "session_start", nullable = false, insertable = true, updatable = true)
    public Timestamp getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(Timestamp sessionStart) {
        this.sessionStart = sessionStart;
    }

    @Basic
    @Column(name = "session_end", nullable = true, insertable = true, updatable = true)
    public Timestamp getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(Timestamp sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    @Basic
    @Column(name = "browser", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    @Basic
    @Column(name = "screen_resolution", nullable = true, insertable = true, updatable = true, length = 20)
    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    @Basic
    @Column(name = "system", nullable = true, insertable = true, updatable = true, length = 20)
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Basic
    @Column(name = "country_code", nullable = true, insertable = true, updatable = true, length = 2)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "country_name", nullable = true, insertable = true, updatable = true, length = 500)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSessionsEntity that = (UserSessionsEntity) o;

        if (browser != null ? !browser.equals(that.browser) : that.browser != null) return false;
        if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (screenResolution != null ? !screenResolution.equals(that.screenResolution) : that.screenResolution != null)
            return false;
        if (sessionEnd != null ? !sessionEnd.equals(that.sessionEnd) : that.sessionEnd != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (sessionStart != null ? !sessionStart.equals(that.sessionStart) : that.sessionStart != null) return false;
        if (system != null ? !system.equals(that.system) : that.system != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionId != null ? sessionId.hashCode() : 0;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (sessionStart != null ? sessionStart.hashCode() : 0);
        result = 31 * result + (sessionEnd != null ? sessionEnd.hashCode() : 0);
        result = 31 * result + (browser != null ? browser.hashCode() : 0);
        result = 31 * result + (screenResolution != null ? screenResolution.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "user_id", nullable = true, insertable = true, updatable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "userSessionsBySessionId")
    public Collection<OnlineUsersEntity> getOnlineUsersesBySessionId() {
        return onlineUsersesBySessionId;
    }

    public void setOnlineUsersesBySessionId(Collection<OnlineUsersEntity> onlineUsersesBySessionId) {
        this.onlineUsersesBySessionId = onlineUsersesBySessionId;
    }

    @OneToMany(mappedBy = "userSessionsBySessionId")
    public Collection<UserGeoDataEntity> getUserGeoDatasBySessionId() {
        return userGeoDatasBySessionId;
    }

    public void setUserGeoDatasBySessionId(Collection<UserGeoDataEntity> userGeoDatasBySessionId) {
        this.userGeoDatasBySessionId = userGeoDatasBySessionId;
    }

    @OneToMany(mappedBy = "userSessionsBySessionId")
    public Collection<UserPagesEntity> getUserPagesesBySessionId() {
        return userPagesesBySessionId;
    }

    public void setUserPagesesBySessionId(Collection<UserPagesEntity> userPagesesBySessionId) {
        this.userPagesesBySessionId = userPagesesBySessionId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
