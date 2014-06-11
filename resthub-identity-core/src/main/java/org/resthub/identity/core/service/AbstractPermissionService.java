package org.resthub.identity.core.service;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.repository.AbstractPermissionRepository;
import org.resthub.identity.service.PermissionService;
import org.resthub.identity.model.Permission;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * An implementation of a PermissionService.
 */
public abstract class AbstractPermissionService<T extends Permission, ID extends Serializable, R extends AbstractPermissionRepository<T, ID>> extends CrudServiceImpl<T, ID, R> implements PermissionService<T, ID> {

    @Override
    @Inject
    @Named("permissionRepository")
    public void setRepository(R permissionRepository) {
        super.setRepository(permissionRepository);
    }

    public Permission findByCode(String code) {
        Assert.notNull(code, "Permission code must not be null");
        List<T> result = this.repository.findByCode(code);
        int size = result.size();
        return (size > 0 && size < 2) ? result.get(0) : null;
    }

}
