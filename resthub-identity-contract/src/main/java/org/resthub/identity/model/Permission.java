package org.resthub.identity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe a permission.
 */
@Entity
@Table(name = "idm_permissions")
public class Permission implements Serializable {

    private static final long serialVersionUID = 9093076599125616233L;

    private Long id;
    private String code;
    private String title;
    private String description;
    private List<Permission> permissions;
    private Permission parent;
    private Application application;

    /**
     * Default constructor
     */
    public Permission() {

    }

    /**
     * @param code code of the Permission to create
     */
    public Permission(String code) {
        this.code = code;
    }

    /**
     * @param code        code of the Permission to create
     * @param application application of the Permission to create
     */
    public Permission(String code, Application application) {
        this.code = code;
        this.application = application;
    }

    /**
     * @param code  code of the Permission to create
     * @param title title of the Permission to create
     */
    public Permission(String code, String title) {
        this.code = code;
        this.title = title;
    }

    /**
     * @param code        code of the Permission to create
     * @param title       title of the Permission to create
     * @param application application of the Permission to create
     */
    public Permission(String code, String title, Application application) {
        this.code = code;
        this.title = title;
        this.application = application;
    }

    /**
     * @param code        name of the Permission to create
     * @param title       title of the Permission to create
     * @param description description of the Permission to create
     */
    public Permission(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    /**
     * @param code        name of the Permission to create
     * @param title       title of the Permission to create
     * @param description description of the Permission to create
     * @param application application of the Permission to create
     */
    public Permission(String code, String title, String description, Application application) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.application = application;
    }

    /**
     * Retrieve the id of the permission
     *
     * @return
     */
    @Id
    @GeneratedValue
    @JsonView({IdView.class})
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the permission
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieve the code of the permission
     *
     * @return
     */
    @Column(nullable = false, unique = true)
    @JsonView({SummarizeView.class})
    public String getCode() {
        return code;
    }

    /**
     * Set the code of the permission
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Retrieve the title of the permission
     *
     * @return
     */
    @Column
    @JsonView({SummarizeView.class})
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the permission
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieve the description of the permission
     *
     * @return
     */
    @Column
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the permission
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieve the permissions of the category
     *
     * @return the permissions of the category
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    public List<Permission> getPermissions() {
        if (this.permissions == null) {
            this.permissions = new ArrayList<Permission>();
        }
        return permissions;
    }

    /**
     * Set the permissions of the category
     *
     * @param permissions
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @ManyToOne(optional = true)
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Add a child permission
     */
    public void addPermission(Permission permission) {
        getPermissions().add(permission);
    }

    @ManyToOne
    @JsonIgnore
    public Permission getParent() {
        return parent;
    }

    /**
     * Set the parent permission
     *
     * @param parent
     */
    public void setParent(Permission parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Permission other = (Permission) obj;

        if ((this.code == null) ? (other.getCode() != null) : !this.code.equals(other.getCode())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    public interface IdView {
    }

    public interface SummarizeView extends IdView {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
