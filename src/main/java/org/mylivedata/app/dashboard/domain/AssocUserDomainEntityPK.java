package org.mylivedata.app.dashboard.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by lubo08 on 9.3.2014.
 */
public class AssocUserDomainEntityPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7221257114181624640L;
	private Integer userId;
    private Integer domainId;

    @Column(name = "user_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "domain_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
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

        AssocUserDomainEntityPK that = (AssocUserDomainEntityPK) o;

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
}
