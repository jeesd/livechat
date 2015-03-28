package org.mylivedata.app.dashboard.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Created by lubo08 on 5.9.2014.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "online_users", schema = "", catalog = "mylivedata")
public class OnlineUsersEntity {
    private Integer userId;
    private Integer status;
    private Integer isOnline;
    private Integer departmentId;
    private Integer isChatHistory;
    private Integer isInChat;
    private Integer isOperator;
    private UsersEntity usersByUserId;
    private DepartmentsEntity departmentsByDepartmentId;
    private Integer sessionId;
    private UserSessionsEntity userSessionsBySessionId;
    private Integer isHttpOnline;

    @Id
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "status", insertable = true, updatable = true, nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "is_online", insertable = true, updatable = true, nullable = false)
    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    @Basic
    @Column(name = "department_id", insertable = true, updatable = true, nullable = true)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "is_chat_history", insertable = true, updatable = true, nullable = false)
    public Integer getIsChatHistory() {
        return isChatHistory;
    }

    public void setIsChatHistory(Integer isChatHistory) {
        this.isChatHistory = isChatHistory;
    }

    @Basic
    @Column(name = "is_in_chat", insertable = true, updatable = true, nullable = false)
    public Integer getIsInChat() {
        return isInChat;
    }

    public void setIsInChat(Integer isInChat) {
        this.isInChat = isInChat;
    }

    @Basic
    @Column(name = "is_operator", insertable = true, updatable = true, nullable = false)
    public Integer getIsOperator() {
        return isOperator;
    }

    public void setIsOperator(Integer isOperator) {
        this.isOperator = isOperator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OnlineUsersEntity that = (OnlineUsersEntity) o;

        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;
        if (isChatHistory != null ? !isChatHistory.equals(that.isChatHistory) : that.isChatHistory != null)
            return false;
        if (isInChat != null ? !isInChat.equals(that.isInChat) : that.isInChat != null) return false;
        if (isOnline != null ? !isOnline.equals(that.isOnline) : that.isOnline != null) return false;
        if (isOperator != null ? !isOperator.equals(that.isOperator) : that.isOperator != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isOnline != null ? isOnline.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (isChatHistory != null ? isChatHistory.hashCode() : 0);
        result = 31 * result + (isInChat != null ? isInChat.hashCode() : 0);
        result = 31 * result + (isOperator != null ? isOperator.hashCode() : 0);
        return result;
    }

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({@JoinColumn(name = "department_id", referencedColumnName = "department_id", insertable = false, updatable = false)})
    public DepartmentsEntity getDepartmentsByDepartmentId() {
        return departmentsByDepartmentId;
    }

    public void setDepartmentsByDepartmentId(DepartmentsEntity departmentsByDepartmentId) {
        this.departmentsByDepartmentId = departmentsByDepartmentId;
    }

    @Basic
    @Column(name = "session_id", nullable = true, insertable = true, updatable = true)
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "session_id", insertable = false, updatable = false)
    public UserSessionsEntity getUserSessionsBySessionId() {
        return userSessionsBySessionId;
    }

    public void setUserSessionsBySessionId(UserSessionsEntity userSessionsBySessionId) {
        this.userSessionsBySessionId = userSessionsBySessionId;
    }

    @Basic
    @Column(name = "is_http_online", nullable = false, insertable = true, updatable = true)
    public Integer getIsHttpOnline() {
        return isHttpOnline;
    }

    public void setIsHttpOnline(Integer isHttpOnline) {
        this.isHttpOnline = isHttpOnline;
    }
}
