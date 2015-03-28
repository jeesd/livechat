package org.mylivedata.app.dashboard.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lubo08 on 5.9.2014.
 */
@Entity
@Table(name = "userconnection", schema = "", catalog = "mylivedata")
public class UserconnectionEntity {
    private Integer userId;
    private String providerId;
    private String providerUserId;
    private String displayName;
    private String profileUrl;
    private String imageUrl;
    private String accessToken;
    private String secret;
    private String refreshToken;
    private String expireTime;

    @Id
    @Column(name = "userId", nullable = false, insertable = true, updatable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "providerId", nullable = false, insertable = true, updatable = true, length = 100)
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Basic
    @Column(name = "providerUserId", nullable = false, insertable = true, updatable = true, length = 50)
    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    @Basic
    @Column(name = "displayName", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "profileUrl", nullable = true, insertable = true, updatable = true, length = 255)
    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Basic
    @Column(name = "imageUrl", nullable = true, insertable = true, updatable = true, length = 255)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Basic
    @Column(name = "accessToken", nullable = true, insertable = true, updatable = true, length = 200)
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Basic
    @Column(name = "secret", nullable = true, insertable = true, updatable = true, length = 150)
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Basic
    @Column(name = "refreshToken", nullable = true, insertable = true, updatable = true, length = 150)
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Basic
    @Column(name = "expireTime", nullable = true, insertable = true, updatable = true, length = 200)
    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserconnectionEntity that = (UserconnectionEntity) o;

        if (accessToken != null ? !accessToken.equals(that.accessToken) : that.accessToken != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (expireTime != null ? !expireTime.equals(that.expireTime) : that.expireTime != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        if (profileUrl != null ? !profileUrl.equals(that.profileUrl) : that.profileUrl != null) return false;
        if (providerId != null ? !providerId.equals(that.providerId) : that.providerId != null) return false;
        if (providerUserId != null ? !providerUserId.equals(that.providerUserId) : that.providerUserId != null)
            return false;
        if (refreshToken != null ? !refreshToken.equals(that.refreshToken) : that.refreshToken != null) return false;
        if (secret != null ? !secret.equals(that.secret) : that.secret != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (providerId != null ? providerId.hashCode() : 0);
        result = 31 * result + (providerUserId != null ? providerUserId.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (profileUrl != null ? profileUrl.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (secret != null ? secret.hashCode() : 0);
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        return result;
    }
}
