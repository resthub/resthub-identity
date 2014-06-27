package org.resthub.identity.core.controller.impl;

import org.resthub.common.exception.NotFoundException;
import org.resthub.identity.core.controller.GroupController;
import org.resthub.identity.core.security.IdentityRoles;
import org.resthub.identity.core.service.GroupService;
import org.resthub.identity.core.service.RoleService;
import org.resthub.identity.core.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bastien on 03/06/14.
 */
public class GroupControllerImpl<T extends Group, I extends Serializable, S extends GroupService<T, I>> extends ServiceBasedRestController<T, I, S> implements GroupController<T, I> {

    protected RoleService roleService;

    protected UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Override this methods in order to secure it *
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.GROUP)
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
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.GROUP)
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
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP)
    public Iterable<T> findAll() {
        return super.findAll();
    }

    /**
     * Override this methods in order to secure it *
     */
    @Override
    @RequestMapping(value = "/findAllPerPage", method = RequestMethod.GET)
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP)
    public Page<T> findPaginated(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                 @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction,
                                 @RequestParam(value = "properties", required = false) String properties) {
        return super.findPaginated(page, size, direction, properties);
    }

    /**
     * Override this methods in order to secure it *
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP)
    public T findById(@PathVariable("id") I id) {
        return super.findById(id);
    }

    /**
     * Override this methods in order to secure it *
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP)
    public void delete() {
        super.delete();
    }

    /**
     * Override this methods in order to secure it *
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP)
    public void delete(@PathVariable("id") I id) {
        super.delete(id);
    }

    /**
     * Find the group identified by the specified name.<br/>
     *
     * @param name the name of the group
     * @return the group, in XML or JSON if the group can be found otherwise
     * HTTP Error 404
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP)
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "name/{name}")
    public T getGroupByName(@PathVariable("name") String name) {
        T group = this.service.findByName(name);
        if (group == null) {
            throw new NotFoundException();
        }
        return group;
    }

    /**
     * Gets the groups depending of the group
     *
     * @param name the name of the group to search insides groups
     * @return a list of group, in XML or JSON if the group can be found
     * otherwise HTTP Error 404
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP)
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "name/{name}/groups")
    public List<T> getGroupsFromGroups(@PathVariable("name") String name) {
        return this.service.getGroupsFromGroup(name);
    }

    /**
     * Puts a group inside the groups lists of one other group
     *
     * @param name  the name of the group in which we should add a group
     * @param group the name of the group the be added
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.GROUP)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "name/{name}/groups/{group}")
    public void addGroupToGroup(@PathVariable("name") String name, @PathVariable("group") String group) {
        this.service.addGroupToGroup(name, group);
    }

    /**
     * Deletes a group from the groups lists of one other group
     *
     * @param name      the name of the group in which we should remove a group
     * @param groupName the name of the gorup the be removed
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/groups/{groups}")
    public void removeGroupsForGroup(@PathVariable("name") String name, @PathVariable("groups") String groupName) {
        this.service.removeGroupFromGroup(name, groupName);
    }

    /**
     * Gets the permissions of one group
     *
     * @param name the name of the group to search insides groups
     * @return a list of permissions, in XML or JSON if the group can be found
     * otherwise HTTP Error 404
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP)
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/name/{name}/permissions")
    public List<Permission> getPermisionsFromGroup(@PathVariable("name") String name) {
        List<Permission> permissions = this.service.getGroupDirectPermissions(name);
        if (permissions == null) {
            throw new NotFoundException();
        }
        return permissions;
    }

    /**
     * Add a permission to a group
     *
     * @param login      the name of the group in which we should add a group
     * @param permission the permission to be added
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.GROUP)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "name/{name}/permissions/{permission}")
    public void addPermissionsToUser(@PathVariable("name") String login, @PathVariable("permission") Permission permission) {
        this.service.addPermissionToGroup(login, permission);
    }

    /**
     * Remove a permission form one Group
     *
     * @param name       the name of the group in which we should remove a permission
     * @param permission the permission to be removed
     */
    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/permissions/{permission}")
    public void deletePermissionsFromUser(@PathVariable("name") String name, @PathVariable("permission") Permission permission) {
        this.service.removePermissionFromGroup(name, permission);
    }

    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP)
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "name/{name}/users")
    public List<User> getUsersFromGroup(@PathVariable("name") String name) {
        List<User> usersFromGroup = this.userService.getUsersFromGroup(name);
        if (usersFromGroup == null) {
            throw new NotFoundException();
        }
        return usersFromGroup;
    }

    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP)
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "name/{name}/roles")
    public List<Role> getRolesFromGroup(@PathVariable("name") String name) {
        List<Role> roles = this.service.getRolesFromGroup(name);
        if (roles == null) {
            throw new NotFoundException();
        }
        return roles;
    }

    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, value = "name/{name}/roles/{role}")
    public void addRoleToGroup(@PathVariable("name") String name, @PathVariable("role") String role) {
        this.service.addRoleToGroup(name, role);
    }

    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/roles/{role}")
    public void removeRoleFromGroup(@PathVariable("name") String name, @PathVariable("role") String role) {
        this.service.removeRoleFromGroup(name, role);
    }

    @Override
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "name/{name}/roles")
    public void removeAllRoleFromGroup(@PathVariable("name") String name) {
        List<Role> roles = this.service.getRolesFromGroup(name);
        if (roles == null) {
            throw new NotFoundException();
        }
        for (Role r : roles) {
            this.service.removeRoleFromGroup(name, r.getName());
        }
    }


}
