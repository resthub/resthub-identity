package org.resthub.identity.core.service;

import org.resthub.common.service.CrudService;
import org.resthub.identity.exception.AlreadyExistingEntityException;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.Role;
import org.springframework.context.ApplicationEventPublisherAware;

import java.io.Serializable;
import java.util.List;

/**
 * Default implementation of a Group Service (can be override by creating a bean with the same name after this one)<br/>
 */
public interface GroupService<T extends Group, I extends Serializable> extends CrudService<T, I>, ApplicationEventPublisherAware {
    /**
     * Create a new group.
     *
     * @param group Group to create
     * @return new group
     */
    @Override
    T create(T group) throws AlreadyExistingEntityException;

    /**
     * Update existing group.
     *
     * @param group Group to update
     * @return group updated
     */
    @Override
    T update(T group) throws AlreadyExistingEntityException;

    /**
     * Finds group by name.
     *
     * @param name the group's Name
     * @return the group or null if no group with this name is found
     */
    T findByName(String name);

    /**
     * Remove a group from one group's group
     *
     * @param groupName    the name of the group to whom the groups should be removed
     * @param subGroupName the name of the group to remove
     */
    void removeGroupFromGroup(String groupName, String subGroupName);

    /**
     * gets the Groups direct Permissions
     *
     * @param groupName the name of the Group
     * @return permissions of the group.
     */
    List<Permission> getGroupDirectPermissions(String groupName);

    /**
     * Add a permission to a group
     *
     * @param groupName  the name of the group
     * @param permission the permission to be added
     */
    void addPermissionToGroup(String groupName, Permission permission);

    /**
     * Remove the permission for the given group
     *
     * @param groupName  the name of the group
     * @param permission the permission to delete
     */
    void removePermissionFromGroup(String groupName, Permission permission);

    /**
     * Get groups from a group
     *
     * @param groupName the name of the group
     * @return
     */
    List<T> getGroupsFromGroup(String groupName);

    /**
     * Add a group from one group's groups
     *
     * @param groupName    the name of the group to whom to group should be added
     * @param subGroupName the name of the group to add from the group's group list
     */
    void addGroupToGroup(String groupName, String subGroupName);

    /**
     * Get roles from a group.
     *
     * @param groupName Group name.
     */
    List<Role> getRolesFromGroup(String groupName);

    /**
     * Add a role to a group.
     *
     * @param groupName Group to which the role will be added.
     * @param roleName  The role that will be added.
     */
    void addRoleToGroup(String groupName, String roleName);

    /**
     * Remove a role from a group.
     *
     * @param groupName Group to which the role will be removed.
     * @param roleName  The role that will be removed.
     */
    void removeRoleFromGroup(String groupName, String roleName);

}
