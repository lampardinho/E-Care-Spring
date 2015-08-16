package com.tsystems.javaschool.ecare.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Kolia on 27.06.2015.
 */
@Entity
@Table(name = "roles", schema = "", catalog = "ecare")
public class Role implements Serializable
{
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int roleId;

    @Column(name = "role_name")
    @NotNull
    private String roleName;



    public Role()
    {
    }

    public Role(String roleName)
    {
        this.roleName = roleName;

    }

    public int getRoleId()
    {
        return roleId;
    }

    public void setRoleId(int userId)
    {
        this.roleId = userId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String name)
    {
        this.roleName = name;
    }



    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role that = (Role) o;

        if (roleId != that.roleId) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = roleId;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }


}
