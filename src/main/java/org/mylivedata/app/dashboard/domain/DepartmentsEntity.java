package org.mylivedata.app.dashboard.domain;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Created by lubo08 on 9.3.2014.
 */
@Entity
@Table(name = "departments", schema = "", catalog = "mylivedata")
public class DepartmentsEntity {
    private Integer departmentId;
    private Integer accountId;
    private Integer domainId;
    private String keyName;
    private String title;
    private AccountsEntity accountsByAccountId;
    private DomainsEntity domainsByDomainId;
    private Collection<OnlineUsersEntity> onlineUsersesByDepartmentId;
    private Collection<UserPagesEntity> userPagesesByDepartmentId;

    @Id
    @Column(name = "department_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "account_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "domain_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    @Basic
    @Column(name = "key_name", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Basic
    @Column(name = "title", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentsEntity that = (DepartmentsEntity) o;

        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;
        if (domainId != null ? !domainId.equals(that.domainId) : that.domainId != null) return false;
        if (keyName != null ? !keyName.equals(that.keyName) : that.keyName != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = departmentId != null ? departmentId.hashCode() : 0;
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (domainId != null ? domainId.hashCode() : 0);
        result = 31 * result + (keyName != null ? keyName.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)})
    public AccountsEntity getAccountsByAccountId() {
        return accountsByAccountId;
    }

    public void setAccountsByAccountId(AccountsEntity accountsByAccountId) {
        this.accountsByAccountId = accountsByAccountId;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "domain_id", referencedColumnName = "domain_id", insertable = false, updatable = false)})
    public DomainsEntity getDomainsByDomainId() {
        return domainsByDomainId;
    }

    public void setDomainsByDomainId(DomainsEntity domainsByDomainId) {
        this.domainsByDomainId = domainsByDomainId;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "departmentsByDepartmentId")
    public Collection<OnlineUsersEntity> getOnlineUsersesByDepartmentId() {
        return onlineUsersesByDepartmentId;
    }

    public void setOnlineUsersesByDepartmentId(Collection<OnlineUsersEntity> onlineUsersesByDepartmentId) {
        this.onlineUsersesByDepartmentId = onlineUsersesByDepartmentId;
    }

    @OneToMany(mappedBy = "departmentsByDepartmentId")
    public Collection<UserPagesEntity> getUserPagesesByDepartmentId() {
        return userPagesesByDepartmentId;
    }

    public void setUserPagesesByDepartmentId(Collection<UserPagesEntity> userPagesesByDepartmentId) {
        this.userPagesesByDepartmentId = userPagesesByDepartmentId;
    }
}
