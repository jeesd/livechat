package org.mylivedata.app.dashboard.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by lubo08 on 9.3.2014.
 */
public class AssocUserGroupsEntityPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9172016910634229904L;
	private Integer userId;
    private Integer groupId;

    @Column(name = "user_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "group_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssocUserGroupsEntityPK that = (AssocUserGroupsEntityPK) o;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
