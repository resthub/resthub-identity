package org.resthub.identity.core.service.impl;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.repository.PermissionRepository;
import org.resthub.identity.core.service.PermissionService;
import org.resthub.identity.model.Permission;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * An implementation of a PermissionService.
 */
public class PermissionServiceImpl<T extends Permission, I extends Serializable, R extends PermissionRepository<T, I>> extends CrudServiceImpl<T, I, R> implements PermissionService<T, I> {
    public T findByCode(String code) {
        Assert.notNull(code, "Permission code must not be null");
        List<T> result = this.repository.findByCode(code);
        int size = result.size();
        return (size > 0 && size < 2) ? result.get(0) : null;
    }

}
