package org.resthub.identity.service;

import java.util.List;

import org.resthub.common.service.CrudService;
import org.resthub.identity.exception.AlreadyExistingEntityException;
import org.resthub.identity.model.*;
import org.resthub.identity.model.PermissionsOwner;

/**
 * User services interface.
 *
 * @author Guillaume Zurbach
 */
public interface GenericUserService<T extends User> extends CrudService<T, Long> {



    /**
     * Create a new user.
     *
     * @param user
     *            User to create
     * @return new user
     */
    @Override
    T create(T user) throws AlreadyExistingEntityException;

    /**
     * Update existing user.
     *
     * @param user
     *            User to update
     * @return user updated
     */
    @Override
    T update(T user) throws AlreadyExistingEntityException;

    /**
     * Find user by login.
     *
     * @param login
     *            User login
     * @return the user or null if more than one user is found
     */
    T findByLogin(String login);

    /**
     * Update the password for the given user
     *
     * @param user
     *            the user to with the new password
     */
    T updatePassword(T user);

    /**
     * Authenticate the user with Login and password
     *
     * @param login
     * @param password
     * @return the authenticated user or null if no user found with such login
     *         and password
     */
    T authenticateUser(String login, String password);

    /**
     * Remove a group from one user's groups
     *
     * @param userLogin
     *            the login of the user to whom to group should be remove
     * @param groupName
     *            the name of the group to remove from the user's group list
     */
    void removeGroupFromUser(String userLogin, String groupName);

    /**
     * gets the User's inherited Permissions
     *
     * @param login
     *            the login of the user
     * @return permissions of the user. In this implementation inherited
     *         permissions from group to which the user belong are taken into
     *         accounts
     */
    List<Permission> getUserPermissions(String login);

    /**
     * gets the User's inherited Permissions for a given application
     *
     * @param login the login of the user
     * @param application the application name
     * @return permissions of the user. In this implementation inherited
     *         permissions from group to which the user belong are taken into
     *         accounts
     */
    List<Permission> getUserPermissions(String login, String application);

    /**
     * gets the User's direct Permissions
     *
     * @param login
     *            the login of the user
     * @return permissions of the user.
     */
    List<Permission> getUserDirectPermissions(String login);

    /**
     * Add a permission to an user
     *
     * @param userLogin
     *            the login of the user
     * @param permission
     *            the permission to be added
     */
    void addPermissionToUser(String userLogin, Permission permission);

    /**
     * Remove the permission for the given user
     *
     * @param userLogin
     *            the login of the user
     * @param permission
     *            the permission to delete
     */
    void removePermissionFromUser(String userLogin, Permission permission);

    /**
     * Add a group from one user's groups
     *
     * @param userLogin
     *            the login of the user to whom to group should be added
     * @param groupName
     *            the name of the group to add from the user's group list
     */
    void addGroupToUser(String userLogin, String groupName);

    /**
     * Gets the user of a group.
     *
     * @param groupName
     *            The name of the group.
     * @return A list of users corresponding to the given group.
     */
    List<T> getUsersFromGroup(String groupName);

    /**
     * Gets all the users that have a role, direct or inherited.
     *
     * @param roles
     *            A list of roles to look for.
     * @return A list of users having at least one of the roles defined as
     *         parameter.
     */
    List<T> findAllUsersWithRoles(List<String> roles);

    /**
     * Add a role to a user.
     *
     * @param userLogin
     *            User to which the role will be added.
     * @param roleName
     *            The role that will be added.
     */
    void addRoleToUser(String userLogin, String roleName);

    /**
     * Remove a role from a user.
     *
     * @param userLogin
     *            User to which the role will be removed.
     * @param roleName
     *            The role that will be removed.
     */
    void removeRoleFromUser(String userLogin, String roleName);

    /**
     * Get all the groups a user owns.
     *
     * @param userLogin
     *            User to gather groups from.
     * @return A list of groups that the given user has.
     */
    List<Group> getAllUserGroups(String userLogin);

    void getGroupsFromRootElement(List<Group> groups, PermissionsOwner owner);

    /**
     * Get all the roles a user owns.
     *
     * @param userLogin
     *            User to gather roles from.
     * @return A list of roles that the given user has.
     */
    List<Role> getAllUserRoles(String userLogin);

    void getRolesFromRootElement(List<Role> roles, PermissionsOwner owner);
}
