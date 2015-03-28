package org.mylivedata.app.dashboard.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by lubo08 on 9.3.2014.
 */
public class AssocGroupRoleEntityPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4752040595675809653L;
	private Integer roleId;
    private Integer groupId;

    @Column(name = "role_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

        AssocGroupRoleEntityPK that = (AssocGroupRoleEntityPK) o;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
