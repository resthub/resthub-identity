package org.resthub.identity.core.service;

import org.resthub.common.service.CrudService;
import org.resthub.identity.model.Permission;

import java.io.Serializable;

/**
 * Permission service interface
 */
public interface PermissionService<T extends Permission, I extends Serializable> extends CrudService<T, I> {

    public T findByCode(String code);
}
