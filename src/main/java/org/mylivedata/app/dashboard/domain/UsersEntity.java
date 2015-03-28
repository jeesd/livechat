package org.mylivedata.app.dashboard.domain;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Created by lubo08 on 9.3.2014.
 */
@Entity
@Table(name = "users", schema = "", catalog = "mylivedata")
public class UsersEntity {
    private Integer userId;
    private String name;
    private String surrname;
    private String email;
    private String password;
    private Timestamp registration;
    private Timestamp lastLogin;
    private String lastIp;
    private String identificationHash;
    private Integer accountId;
    private Collection<AccountsEntity> accountsesByUserId;
    private Collection<AssocUserDomainEntity> assocUserDomainsByUserId;
    private Collection<AssocUserGroupsEntity> assocUserGroupsesByUserId;
    private AccountsEntity accountsByAccountId;
    private String isCredentialsNonExpired;
    private OnlineUsersEntity onlineUsersByUserId;
    private String imageUrl;
    private Collection<UserGeoDataEntity> userGeoDatasByUserId;
    private Collection<UserPagesEntity> userPagesesByUserId;
    private Collection<UserSessionsEntity> userSessionsesByUserId;
    private Collection<UsersChatsEntity> usersChatsesByUserId;
    private Collection<UsersChatsEntity> usersChatsesByUserId_0;

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surrname", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    public String getSurrname() {
        return surrname;
    }

    public void setSurrname(String surrname) {
        this.surrname = surrname;
    }

    @Basic
    @Column(name = "email", unique = true, nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "registration", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getRegistration() {
        return registration;
    }

    public void setRegistration(Timestamp registration) {
        this.registration = registration;
    }

    @Basic
    @Column(name = "last_login", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Basic
    @Column(name = "last_ip", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    @Basic
    @Column(name = "identification_hash", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    public String getIdentificationHash() {
        return identificationHash;
    }

    public void setIdentificationHash(String identificationHash) {
        this.identificationHash = identificationHash;
    }

    @Basic
    @Column(name = "account_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (identificationHash != null ? !identificationHash.equals(that.identificationHash) : that.identificationHash != null)
            return false;
        if (lastIp != null ? !lastIp.equals(that.lastIp) : that.lastIp != null) return false;
        if (lastLogin != null ? !lastLogin.equals(that.lastLogin) : that.lastLogin != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (registration != null ? !registration.equals(that.registration) : that.registration != null) return false;
        if (surrname != null ? !surrname.equals(that.surrname) : that.surrname != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surrname != null ? surrname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (registration != null ? registration.hashCode() : 0);
        result = 31 * result + (lastLogin != null ? lastLogin.hashCode() : 0);
        result = 31 * result + (lastIp != null ? lastIp.hashCode() : 0);
        result = 31 * result + (identificationHash != null ? identificationHash.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByOwnerId")
    public Collection<AccountsEntity> getAccountsesByUserId() {
        return accountsesByUserId;
    }

    public void setAccountsesByUserId(Collection<AccountsEntity> accountsesByUserId) {
        this.accountsesByUserId = accountsesByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<AssocUserDomainEntity> getAssocUserDomainsByUserId() {
        return assocUserDomainsByUserId;
    }

    public void setAssocUserDomainsByUserId(Collection<AssocUserDomainEntity> assocUserDomainsByUserId) {
        this.assocUserDomainsByUserId = assocUserDomainsByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<AssocUserGroupsEntity> getAssocUserGroupsesByUserId() {
        return assocUserGroupsesByUserId;
    }

    public void setAssocUserGroupsesByUserId(Collection<AssocUserGroupsEntity> assocUserGroupsesByUserId) {
        this.assocUserGroupsesByUserId = assocUserGroupsesByUserId;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)})
    public AccountsEntity getAccountsByAccountId() {
        return accountsByAccountId;
    }

    public void setAccountsByAccountId(AccountsEntity accountsByAccountId) {
        this.accountsByAccountId = accountsByAccountId;
    }

    @Basic
    @Column(name = "is_credentials_non_expired", nullable = false, insertable = true, updatable = true, length = 5, precision = 0)
    public String getIsCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setIsCredentialsNonExpired(String isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    @JsonManagedReference
    @OneToOne(mappedBy = "usersByUserId")
    public OnlineUsersEntity getOnlineUsersByUserId() {
        return onlineUsersByUserId;
    }

    public void setOnlineUsersByUserId(OnlineUsersEntity onlineUsersByUserId) {
        this.onlineUsersByUserId = onlineUsersByUserId;
    }

    @Basic
    @Column(name = "image_url", nullable = true, insertable = true, updatable = true, length = 500)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UserGeoDataEntity> getUserGeoDatasByUserId() {
        return userGeoDatasByUserId;
    }

    public void setUserGeoDatasByUserId(Collection<UserGeoDataEntity> userGeoDatasByUserId) {
        this.userGeoDatasByUserId = userGeoDatasByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UserPagesEntity> getUserPagesesByUserId() {
        return userPagesesByUserId;
    }

    public void setUserPagesesByUserId(Collection<UserPagesEntity> userPagesesByUserId) {
        this.userPagesesByUserId = userPagesesByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UserSessionsEntity> getUserSessionsesByUserId() {
        return userSessionsesByUserId;
    }

    public void setUserSessionsesByUserId(Collection<UserSessionsEntity> userSessionsesByUserId) {
        this.userSessionsesByUserId = userSessionsesByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UsersChatsEntity> getUsersChatsesByUserId() {
        return usersChatsesByUserId;
    }

    public void setUsersChatsesByUserId(Collection<UsersChatsEntity> usersChatsesByUserId) {
        this.usersChatsesByUserId = usersChatsesByUserId;
    }

    @OneToMany(mappedBy = "usersByInChatWithUserId")
    public Collection<UsersChatsEntity> getUsersChatsesByUserId_0() {
        return usersChatsesByUserId_0;
    }

    public void setUsersChatsesByUserId_0(Collection<UsersChatsEntity> usersChatsesByUserId_0) {
        this.usersChatsesByUserId_0 = usersChatsesByUserId_0;
    }
}
