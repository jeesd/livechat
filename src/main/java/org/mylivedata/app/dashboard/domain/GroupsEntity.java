package org.mylivedata.app.dashboard.domain;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by lubo08 on 9.3.2014.
 */
@Entity
@Table(name = "groups", schema = "", catalog = "mylivedata")
public class GroupsEntity {
    private Integer groupId;
    private String groupName;
    private String description;
    private Collection<AssocGroupRoleEntity> assocGroupRolesByGroupId;
    private Collection<AssocUserGroupsEntity> assocUserGroupsesByGroupId;

    @Id
    @Column(name = "group_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupsEntity that = (GroupsEntity) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "groupsByGroupId")
    public Collection<AssocGroupRoleEntity> getAssocGroupRolesByGroupId() {
        return assocGroupRolesByGroupId;
    }

    public void setAssocGroupRolesByGroupId(Collection<AssocGroupRoleEntity> assocGroupRolesByGroupId) {
        this.assocGroupRolesByGroupId = assocGroupRolesByGroupId;
    }

    @OneToMany(mappedBy = "groupsByGroupId")
    public Collection<AssocUserGroupsEntity> getAssocUserGroupsesByGroupId() {
        return assocUserGroupsesByGroupId;
    }

    public void setAssocUserGroupsesByGroupId(Collection<AssocUserGroupsEntity> assocUserGroupsesByGroupId) {
        this.assocUserGroupsesByGroupId = assocUserGroupsesByGroupId;
    }
}
