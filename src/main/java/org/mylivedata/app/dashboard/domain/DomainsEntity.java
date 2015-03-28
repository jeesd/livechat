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

/**
 * Created by lubo08 on 9.3.2014.
 */
@Entity
@Table(name = "domains", schema = "", catalog = "mylivedata")
public class DomainsEntity {
    private Integer domainId;
    private String domain;
    private String description;
    private Integer accountId;
    private String visibleOffline;
    private Collection<AssocUserDomainEntity> assocUserDomainsByDomainId;
    private Collection<DepartmentsEntity> departmentsesByDomainId;
    private AccountsEntity accountsByAccountId;

    @Id
    @Column(name = "domain_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    @Basic
    @Column(name = "domain", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "visible_offline", nullable = true, insertable = true, updatable = true, length = 4, precision = 0)
    public String getVisibleOffline() {
        return visibleOffline;
    }

    public void setVisibleOffline(String visibleOffline) {
        this.visibleOffline = visibleOffline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainsEntity that = (DomainsEntity) o;

        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (domain != null ? !domain.equals(that.domain) : that.domain != null) return false;
        if (domainId != null ? !domainId.equals(that.domainId) : that.domainId != null) return false;
        if (visibleOffline != null ? !visibleOffline.equals(that.visibleOffline) : that.visibleOffline != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = domainId != null ? domainId.hashCode() : 0;
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (visibleOffline != null ? visibleOffline.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "domainsByDomainId")
    public Collection<AssocUserDomainEntity> getAssocUserDomainsByDomainId() {
        return assocUserDomainsByDomainId;
    }

    public void setAssocUserDomainsByDomainId(Collection<AssocUserDomainEntity> assocUserDomainsByDomainId) {
        this.assocUserDomainsByDomainId = assocUserDomainsByDomainId;
    }

    @OneToMany(mappedBy = "domainsByDomainId")
    public Collection<DepartmentsEntity> getDepartmentsesByDomainId() {
        return departmentsesByDomainId;
    }

    public void setDepartmentsesByDomainId(Collection<DepartmentsEntity> departmentsesByDomainId) {
        this.departmentsesByDomainId = departmentsesByDomainId;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)})
    public AccountsEntity getAccountsByAccountId() {
        return accountsByAccountId;
    }

    public void setAccountsByAccountId(AccountsEntity accountsByAccountId) {
        this.accountsByAccountId = accountsByAccountId;
    }
}
