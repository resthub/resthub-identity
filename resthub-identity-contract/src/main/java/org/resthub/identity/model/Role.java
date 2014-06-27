package org.resthub.identity.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "idm_roles")
public class Role implements Serializable {

    private static final long serialVersionUID = -34014168150871855L;

    protected Long id;

    protected String name;
    protected List<Permission> permissions = new ArrayList<Permission>();

    /**
     * Hide default constructor not to be able to create roles w/o a name.
     */
    protected Role() {
    }

    /**
     * @param roleName Name of the role to create.
     */
    public Role(String roleName) {
        this.setName(roleName);
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
     * Retrieve the name of the role.
     *
     * @return the name of the role.
     */
    @NotNull
    @JsonView({SummarizeView.class})
    public String getName() {
        return this.name;
    }

    /**
     * Change the name of the role.
     *
     * @param roleName Name of the role.
     */
    public void setName(String roleName) {
        this.name = roleName;
    }

    /**
     * Retrieve the permissions assigned to the role.
     *
     * @return the permissions assigned to the role.
     */
    @ManyToMany
    @JoinTable(name = "role_permission")
    @JsonView({SummarizeView.class})
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Define the list of permissions of the role.
     *
     * @param permissions List of permissions to assign to the role.
     */
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

    public static interface IdView {
    }

    public static interface SummarizeView extends IdView, Permission.SummarizeView {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
