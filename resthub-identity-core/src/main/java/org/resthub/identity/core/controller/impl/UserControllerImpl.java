package org.resthub.identity.core.controller.impl;

import org.resthub.common.exception.NotFoundException;
import org.resthub.identity.core.controller.UserController;
import org.resthub.identity.core.security.IdentityRoles;
import org.resthub.identity.core.service.UserService;
import org.resthub.identity.core.tools.PermissionsOwnerTools;
import org.resthub.identity.exception.AlreadyExistingEntityException;
import org.resthub.identity.exception.ExpectationFailedException;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.web.controller.ServiceBasedRestController;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bastien on 03/06/14.
 */
public class UserControllerImpl<T extends User, I extends Serializable, S extends UserService<T, I>> extends ServiceBasedRestController<T, I, S> implements UserController<T, I> {
    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.USER)
    @Override
    public T create(@RequestBody T resource) {
        try {
            return super.create(resource);
        } catch (AlreadyExistingEntityException e) {
            throw new ExpectationFailedException(e.getMessage());
        }
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.USER)
    @Override
    public T update(@PathVariable("id") I id, @RequestBody T resource) {
        try {
            return super.update(id, resource);
        } catch (AlreadyExistingEntityException e) {
            throw new ExpectationFailedException(e.getMessage());
        }
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.USER)
    @Override
    public Iterable<T> findAll() {
        return super.findAll();
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.USER)
    @RequestMapping(value = "/findAllPerPage", method = RequestMethod.GET)
    @Override
    public Page<T> findPaginated(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                 @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction,
                                 @RequestParam(value = "properties", required = false) String properties) {
        return super.findPaginated(page, size, direction, properties);
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.USER)
    @Override
    public T findById(@PathVariable("id") I id) {
        return super.findById(id);
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.USER)
    @Override
    public void delete() {
        super.delete();
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.USER)
    @Override
    public void delete(@PathVariable("id") I id) {
        super.delete(id);
    }

    @Override
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllRoleFromUser(@PathVariable("login") String login) {
        List<Role> roles = this.service.getAllUserRoles(login);
        if (roles == null) {
            throw new NotFoundException();
        }
        for (Role r : roles) {
            this.service.removeRoleFromUser(login, r.getName());
        }

    }

    @Override
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/groups")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllGroupFromUser(@PathVariable("login") String login) {
        List<Group> groups = this.service.getAllUserGroups(login);
        if (groups == null) {
            throw new NotFoundException();
        }
        for (Group grp : groups) {
            this.service.removeGroupFromUser(login, grp.getName());
        }

    }

    /**
     * Return the user identified by the specified login.
     *
     * @param login
     * @return the user, in XML or JSON if the user can be found otherwise HTTP
     * Error 404
     */
    @Override
    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "login/{login}")
    @ResponseBody
    public User getUserByLogin(@PathVariable("login") String login) {
        User user = this.service.findByLogin(login);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    /**
     * Return the currently authentified Used<br/>
     * <p>
     * <p>
     * This is the first method to call once authenticated with Oauth2
     * Currently, the Oauth2 authentication method is the one remaining We can't
     * be log without using OAuth2 The user_id will be override by the filter
     * layer, so we can't get the User object corresponding to another user than
     * the one logged
     * </p>
     *
     * @return the Logged User Object, in XMl or JSON type if everything OK,
     * otherwise (It shouldn't append) an HTTP error 404
     */
    @Override
    @Secured({"IS_AUTHENTICATED_FULLY"})
    @RequestMapping(method = RequestMethod.GET, value = "me")
    @ResponseBody
    public User currentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Assert.notNull(userDetails);

        User user = this.service.findByLogin(userDetails.getUsername());
        if (user == null) {
            throw new NotFoundException();
        }

        List<Permission> permissions = PermissionsOwnerTools.getInheritedPermission(user);
        user.getPermissions().clear();
        user.getPermissions().addAll(permissions);
        return user;
    }

    /**
     * Update the current user *
     */
    @Override
    @Secured({"IS_AUTHENTICATED_FULLY"})
    @RequestMapping(method = RequestMethod.PUT, value = "me")
    @ResponseBody
    public T updateMe(T user) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Assert.notNull(userDetails);
        Assert.isTrue(userDetails.getUsername().equals(user.getLogin()));
        T retreivedUser = this.service.findByLogin(userDetails.getUsername());

        if (retreivedUser == null) {
            throw new NotFoundException();
        }

        retreivedUser = super.update((I) retreivedUser.getId(), user);

        return retreivedUser;
    }

    /**
     * Gets the groups depending of the user
     *
     * @param login the login of the user to search insides groups
     * @return a list of group, in XML or JSON if the group can be found
     * otherwise HTTP Error 404
     */
    @Override
    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "name/{login}/groups")
    @ResponseBody
    public List<Group> getGroupsFromUser(@PathVariable("login") String login) {
        return this.service.getAllUserGroups(login);
    }

    /**
     * Puts a group inside the groups lists of a user
     *
     * @param login the login of the user for which we should add a group
     * @param group the name of the group the be added
     */
    @Override
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, value = "name/{login}/groups/{group}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addGroupToUser(@PathVariable("login") String login, @PathVariable("group") String group) {
        this.service.addGroupToUser(login, group);
    }

    /**
     * Deletes a group from the groups lists of a user
     *
     * @param userLogin the login of the user for which we should remove a group
     * @param groupName the name of the group the be removed
     */
    @Override
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{login}/groups/{groups}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGroupsForUser(@PathVariable("login") String userLogin, @PathVariable("groups") String groupName) {
        this.service.removeGroupFromUser(userLogin, groupName);
    }

    /**
     * Gets the permissions of a user
     *
     * @param login the login of the user to search insides groups
     * @return a list of permissions, in XML or JSON if the group can be found
     * otherwise HTTP Error 404
     */
    @Override
    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "name/{login}/permissions")
    @ResponseBody
    public List<Permission> getPermissionsFromUser(@PathVariable("login") String login) {
        List<Permission> permissions = this.service.getUserPermissions(login);
        if (permissions == null) {
            throw new NotFoundException();
        }
        return permissions;
    }

    /**
     * Gets the permissions of a user related to an application
     *
     * @param login       the login of the user to search insides groups
     * @param application the wanted application
     * @return a list of permissions, in XML or JSON if the group can be found
     * otherwise HTTP Error 404
     */
    @Override
    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "name/{login}/permissions/{application}")
    @ResponseBody
    public List<Permission> getPermissionsFromUserAndApplication(@PathVariable("login") String login, @PathVariable("application") String application) {
        List<Permission> permissions = this.service.getUserPermissions(login, application);
        if (permissions == null) {
            throw new NotFoundException();
        }
        return permissions;
    }

    /**
     * Add a permission to a user
     *
     * @param login      the login of the user in which we should add a group
     * @param permission the permission to be added
     */
    @Override
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, value = "name/{login}/permissions/{permission}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addPermissionsToUser(@PathVariable("login") String login, @PathVariable("permission") Permission permission) {
        this.service.addPermissionToUser(login, permission);
    }

    /**
     * Remove a permisssion for one User
     *
     * @param login      the login of the user in which we should remove a permission
     * @param permission the permisssion to be removed
     */
    @Override
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{login}/permissions/{permission}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermissionsFromUser(@PathVariable("login") String login, @PathVariable("permission") Permission permission) {
        this.service.removePermissionFromUser(login, permission);
    }

    @Override
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.PUT, value = "name/{login}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRoleToUser(@PathVariable("login") String login, @PathVariable("role") String role) {
        this.service.addRoleToUser(login, role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Secured({"IM_USER_ADMIN"})
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{login}/roles/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRoleFromUser(@PathVariable("login") String login, @PathVariable("role") String role) {
        this.service.removeRoleFromUser(login, role);
    }

    /**
     * Get all the roles of a user.
     *
     * @param login Login to check.
     * @return A list of roles the given user has.
     */
    @Override
    @Secured({"IM_USER_ADMIN", "IM_USER_READ"})
    @RequestMapping(method = RequestMethod.GET, value = "name/{login}/roles")
    @ResponseBody
    public List<Role> getAllUserRoles(@PathVariable("login") String login) {
        return this.service.getAllUserRoles(login);
    }

    /**
     * Check the user identity with the given user name and password.
     *
     * @param username The user name.
     * @param password The password of the user.
     */
    @Override
    @RequestMapping(method = RequestMethod.POST, value = "checkuser")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void authenticateUser(@RequestParam("user") String username, @RequestParam("password") String password) {

        User user = this.service.authenticateUser(username, password);
        if (user == null) {
            throw new NotFoundException();
        }
    }
}
