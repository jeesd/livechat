package org.mylivedata.app.connection.domain;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by lubo08 on 30.10.2014.
 */
public class VisitorPrincipal {
    private String origin;
    private String accountIdHash;
    private String department;
    private Locale locale;
    private String userHashId;
    private String remoteAddress;
    private String screenWH;
    private String referrer;
    private SecureUser secureUser;

    public SecureUser getSecureUser() {
        return secureUser;
    }

    public void setSecureUser(SecureUser secureUser) {
        this.secureUser = secureUser;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getScreenWH() {
        return screenWH;
    }

    public void setScreenWH(String screenWH) {
        this.screenWH = screenWH;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getUserHashId() {
        return userHashId;
    }

    public void setUserHashId(String userHashId) {
        this.userHashId = userHashId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccountIdHash() {
        return accountIdHash;
    }

    public void setAccountIdHash(String accountIdHash) {
        this.accountIdHash = accountIdHash;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
