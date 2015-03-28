package org.mylivedata.app.dashboard.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userconnection", schema = "", catalog = "mylivedata")
public class UserConnection {

    private Integer userId;
    private String providerId;
    private String providerUserId;
    
    @Id
    @Column(name = "userId", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    @Basic
    @Column(name = "providerId", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
    
    @Basic
    @Column(name = "providerUserId", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }
}
