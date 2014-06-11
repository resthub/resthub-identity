package org.resthub.identity.service;

import org.resthub.common.service.CrudService;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;

import java.io.Serializable;

/**
 * Permission service interface
 *
 */
public interface PermissionService<T extends Permission, ID extends Serializable> extends CrudService<T, ID> {

	public Permission findByCode(String code);
}
