package org.mylivedata.app.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by lubo08 on 9.3.2014.
 */
@Entity
@Table(name = "assoc_user_domain", schema = "", catalog = "mylivedata")
@IdClass(AssocUserDomainEntityPK.class)
public class AssocUserDomainEntity {
    private Integer userId;
    private Integer domainId;
    private UsersEntity usersByUserId;
    private DomainsEntity domainsByDomainId;

    @Id
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "domain_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssocUserDomainEntity that = (AssocUserDomainEntity) o;

        if (domainId != null ? !domainId.equals(that.domainId) : that.domainId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (domainId != null ? domainId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)})
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "domain_id", referencedColumnName = "domain_id", nullable = false, insertable = false, updatable = false)})
    public DomainsEntity getDomainsByDomainId() {
        return domainsByDomainId;
    }

    public void setDomainsByDomainId(DomainsEntity domainsByDomainId) {
        this.domainsByDomainId = domainsByDomainId;
    }
}
