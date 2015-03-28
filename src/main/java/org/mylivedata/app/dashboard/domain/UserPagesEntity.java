package org.mylivedata.app.dashboard.domain;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by lubo08 on 5.9.2014.
 */
@Entity
@Table(name = "user_pages", schema = "", catalog = "mylivedata")
public class UserPagesEntity {
    private Integer vpId;
    private String url;
    private Timestamp accessedOn;
    private DepartmentsEntity departmentsByDepartmentId;
    private Integer userId;
    private Integer sessionId;
    private Integer departmentId;
    private UserSessionsEntity userSessionsBySessionId;
    private UsersEntity usersByUserId;

    @Id
    @Column(name = "vp_id", nullable = false, insertable = true, updatable = true)
    public Integer getVpId() {
        return vpId;
    }

    public void setVpId(Integer vpId) {
        this.vpId = vpId;
    }

    @Basic
    @Column(name = "url", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "accessed_on", nullable = false, insertable = true, updatable = true)
    public Timestamp getAccessedOn() {
        return accessedOn;
    }

    public void setAccessedOn(Timestamp accessedOn) {
        this.accessedOn = accessedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPagesEntity that = (UserPagesEntity) o;

        if (accessedOn != null ? !accessedOn.equals(that.accessedOn) : that.accessedOn != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (vpId != null ? !vpId.equals(that.vpId) : that.vpId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vpId != null ? vpId.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (accessedOn != null ? accessedOn.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id", insertable = false, updatable = false)
    public DepartmentsEntity getDepartmentsByDepartmentId() {
        return departmentsByDepartmentId;
    }

    public void setDepartmentsByDepartmentId(DepartmentsEntity departmentsByDepartmentId) {
        this.departmentsByDepartmentId = departmentsByDepartmentId;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "session_id", nullable = false, insertable = true, updatable = true)
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "department_id", nullable = true, insertable = true, updatable = true)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", nullable = false, insertable = false, updatable = false)
    public UserSessionsEntity getUserSessionsBySessionId() {
        return userSessionsBySessionId;
    }

    public void setUserSessionsBySessionId(UserSessionsEntity userSessionsBySessionId) {
        this.userSessionsBySessionId = userSessionsBySessionId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
