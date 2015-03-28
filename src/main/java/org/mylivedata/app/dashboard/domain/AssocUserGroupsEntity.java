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
@Table(name = "assoc_user_groups", schema = "", catalog = "mylivedata")
@IdClass(AssocUserGroupsEntityPK.class)
public class AssocUserGroupsEntity {
    private Integer userId;
    private Integer groupId;
    private UsersEntity usersByUserId;
    private GroupsEntity groupsByGroupId;

    @Id
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "group_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
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

        AssocUserGroupsEntity that = (AssocUserGroupsEntity) o;

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

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)})
    public UsersEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UsersEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false, insertable = false, updatable = false)})
    public GroupsEntity getGroupsByGroupId() {
        return groupsByGroupId;
    }

    public void setGroupsByGroupId(GroupsEntity groupsByGroupId) {
        this.groupsByGroupId = groupsByGroupId;
    }
}
