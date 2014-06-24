package org.resthub.identity.core.controller;

import org.resthub.common.exception.NotFoundException;
import org.resthub.identity.core.security.IdentityRoles;
import org.resthub.identity.exception.AlreadyExistingEntityException;
import org.resthub.identity.exception.ExpectationFailedException;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.identity.service.RoleService;
import org.resthub.identity.service.UserService;
import org.resthub.web.controller.ServiceBasedRestController;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bastien on 03/06/14.
 */
public abstract class AbstractRoleController<T extends Role, I extends Serializable, S extends RoleService<T, I>> extends ServiceBasedRestController<T, I, S> {
    @Inject
    @Named("userService")
    private UserService userService;

    @Inject
    @Named("roleService")
    @Override
    public void setService(S service) {
        this.service = service;
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.ROLE)
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
    @Secured(value = IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.ROLE)
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
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.ROLE)
    @Override
    public Iterable<T> findAll() {
        return super.findAll();
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.ROLE)
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
    @Secured(value = IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.ROLE)
    @Override
    public T findById(@PathVariable("id") I id) {
        return super.findById(id);
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.ROLE)
    @Override
    public void delete() {
        super.delete();
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.ROLE)
    @Override
    public void delete(@PathVariable("id") I id) {
        super.delete(id);
    }

    /**
     * Gets all the users that have a role, direct or inherited.
     *
     * @param name A list of roles to look for.
     * @return A list of users having at least one of the roles defined as
     * parameter.
     */

    @Secured({"IM-ADMIN"})
    @RequestMapping(method = RequestMethod.GET, value = "{name}/users")
    @ResponseBody
    public List<User> findAllUsersWithRole(@PathVariable("name") String name) {
        List<User> usersWithRoles = this.userService.findAllUsersWithRoles(Arrays.asList(name));
        if (usersWithRoles == null) {
            throw new NotFoundException();
        }
        return usersWithRoles;
    }
}
