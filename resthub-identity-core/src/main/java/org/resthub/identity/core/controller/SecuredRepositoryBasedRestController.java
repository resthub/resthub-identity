package org.resthub.identity.core.controller;

import org.resthub.identity.core.security.IdentityRoles;
import org.resthub.web.controller.RepositoryBasedRestController;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * Created by bastien on 03/06/14.
 */
public abstract class SecuredRepositoryBasedRestController<T, ID extends Serializable, R extends PagingAndSortingRepository> extends RepositoryBasedRestController<T, ID, R> {
    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.ROLE_CREATE)
    @Override
    public T create(@RequestBody T resource) {
        return super.create(resource);
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.ROLE_UPDATE)
    @Override
    public T update(@PathVariable("id") ID id, @RequestBody T resource) {
        return super.update(id, resource);
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.ROLE_READ)
    @Override
    public Iterable<T> findAll() {
        return super.findAll();
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.ROLE_READ)
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
    @Secured(value = IdentityRoles.ROLE_READ)
    @Override
    public T findById(@PathVariable("id") ID id) {
        return super.findById(id);
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.ROLE_DELETE)
    @Override
    public void delete() {
        super.delete();
    }

    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = IdentityRoles.ROLE_DELETE)
    @Override
    public void delete(@PathVariable("id") ID id) {
        super.delete(id);
    }
}
