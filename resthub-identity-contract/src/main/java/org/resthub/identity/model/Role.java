package org.resthub.identity.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "idm_roles")
public class Role {

    protected Long id;
    protected String name;
    protected List<Permission> permissions = new ArrayList<Permission>();

    /**
     * Hide default constructor not to be able to create roles w/o a name.
     */
    protected Role() {
    }

    /**
     * @param roleName
     *            Name of the role to create.
     */
    public Role(String roleName) {
        this.setName(roleName);
    }


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieve the name of the role.
     * 
     * @return the name of the role.
     */
    @Column(nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    /**
     * Change the name of the role.
     * 
     * @param roleName
     *            Name of the role.
     */
    public void setName(String roleName) {
        this.name = roleName;
    }

    /**
     * Retrieve the permissions assigned to the role.
     * 
     * @return the permissions assigned to the role.
     * */
    @ManyToMany
    @JoinTable(name = "role_permission")
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Define the list of permissions of the role.
     * 
     * @param permissions
     *            List of permissions to assign to the role.
     * */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;

        if ((this.id == null) ? (other.getId() != null) : !this.id.equals(other.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (this.id == null ? 0 : this.id.hashCode());
        return hash;
    }
}
