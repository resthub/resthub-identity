package org.resthub.identity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Describe a generic class for users and groups. It contains/manage mainly
 * permissions. When possible you SHOULD use method form the class and not from
 * the permissions list
 * </p>
 * <p>
 * Must be an entity to be references by permissions_owner_permissions and
 * permissions_owner_groups
 */
@Entity
@Table(name = "idm_permissions_owner")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PermissionsOwner implements Serializable {

    private static final long serialVersionUID = 5506055534017559927L;
    /**
     * the list of permissions
     */
    protected List<Permission> permissions = new ArrayList<Permission>();
    /**
     * List of Permissions Owner (Group) in which the Permissions Owner is
     */
    protected List<Group> groups = new ArrayList<Group>();
    /**
     * List of roles the permission owner has.
     */
    protected List<Role> roles = new ArrayList<Role>();
    private Long id;

    /**
     * Default constructor
     */
    public PermissionsOwner() {

    }

    public PermissionsOwner(PermissionsOwner i) {
        List<Permission> pPermissions = i.getPermissions();
        permissions.addAll(pPermissions);
    }

    /**
     * Constructor
     *
     * @param permissions list of permission to be assigned to the new Identity
     */
    public PermissionsOwner(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Id
    @GeneratedValue
    @JsonView({IdView.class})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieve the permissions assigned to the identity
     *
     * @return the permissions list if permissions have been assigned, otherwise
     * null
     */
    @ManyToMany
    @JoinTable(name = "permissions_owner_permissions")
    @JsonIgnore
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * <b>Only used by Hibernate</b> Please use getPermissions() instead.
     */
    @SuppressWarnings("unused")
    private void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Retrieve the roles assigned to the identity.
     *
     * @return the list of roles assigned to the identity.
     */
    @ManyToMany
    @JoinTable(name = "permissions_owner_roles")
    @JsonView({ProfileView.class})
    public List<Role> getRoles() {
        return this.roles;
    }

    /**
     * <b>Only used by Hibernate</b> Please use getRoles() instead.
     */
    @SuppressWarnings("unused")
    private void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * gets the user's Groups
     *
     * @return the list of groups in which the user is. The List could be null
     * is the user is in no group
     */
    @ManyToMany
    @JoinTable(name = "permissions_owner_groups",
            joinColumns = @JoinColumn(name = "permissions_owner", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @JsonIgnore
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * <b>Only used by Hibernate</b> Please use getGroups() instead.
     */
    @SuppressWarnings("unused")
    @JsonProperty
    private void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PermissionsOwner other = (PermissionsOwner) obj;

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

    public static interface IdView {
    }

    public static interface ProfileView {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}