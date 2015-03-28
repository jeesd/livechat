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
@Table(name = "roles", schema = "", catalog = "mylivedata")
public class RolesEntity {
    private Integer roleId;
    private String roleName;
    private String description;
    private Collection<AssocGroupRoleEntity> assocGroupRolesByRoleId;

    @Id
    @Column(name = "role_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "role_name", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
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

        RolesEntity that = (RolesEntity) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "rolesByRoleId")
    public Collection<AssocGroupRoleEntity> getAssocGroupRolesByRoleId() {
        return assocGroupRolesByRoleId;
    }

    public void setAssocGroupRolesByRoleId(Collection<AssocGroupRoleEntity> assocGroupRolesByRoleId) {
        this.assocGroupRolesByRoleId = assocGroupRolesByRoleId;
    }
}
