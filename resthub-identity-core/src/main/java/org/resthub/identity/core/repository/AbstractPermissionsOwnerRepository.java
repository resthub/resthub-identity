package org.resthub.identity.core.repository;

import org.resthub.identity.model.Group;
import org.resthub.identity.model.PermissionsOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Repository for generic operations on AbstractPermissionsOwner entities.
 *
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public interface AbstractPermissionsOwnerRepository<T extends PermissionsOwner, I extends Serializable> extends JpaRepository<T, I> {

    /**
     * Gets all the AbstractPermissionsOwners that have a role.
     *
     * @param roles A list of roles to look for.
     * @return A list of AbstractPermissionsOwners having at least one of the roles defined as parameter.
     */
    @Query("SELECT DISTINCT e FROM PermissionsOwner e JOIN e.roles r WHERE r.name IN :roles")
    List<T> getWithRoles(@Param("roles") List<String> roles);

    /**
     * Gets all the AbstractPermissionsOwners that have the specified group as parent.
     *
     * @param group The parent group.
     * @return The list of AbstractPermissionsOwners that are associated with the group.
     */
    @Query("SELECT DISTINCT e FROM PermissionsOwner e JOIN e.groups g WHERE g = :group")
    List<T> getWithGroupAsParent(@Param("group") Group group);
}
