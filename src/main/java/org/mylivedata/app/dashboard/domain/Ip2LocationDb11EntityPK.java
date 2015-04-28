package org.mylivedata.app.dashboard.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by lubo08 on 16.9.2014.
 */
public class Ip2LocationDb11EntityPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3452374593246467205L;
	private Long ipFrom;
    private Long ipTo;

    @Column(name = "ip_from", nullable = false, insertable = true, updatable = true)
    @Basic
    @Id
    public Long getIpFrom() {
        return ipFrom;
    }

    public void setIpFrom(Long ipFrom) {
        this.ipFrom = ipFrom;
    }

    @Column(name = "ip_to", nullable = false, insertable = true, updatable = true)
    @Basic
    @Id
    public Long getIpTo() {
        return ipTo;
    }

    public void setIpTo(Long ipTo) {
        this.ipTo = ipTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ip2LocationDb11EntityPK that = (Ip2LocationDb11EntityPK) o;

        if (ipFrom != null ? !ipFrom.equals(that.ipFrom) : that.ipFrom != null) return false;
        if (ipTo != null ? !ipTo.equals(that.ipTo) : that.ipTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ipFrom != null ? ipFrom.hashCode() : 0;
        result = 31 * result + (ipTo != null ? ipTo.hashCode() : 0);
        return result;
    }
}
