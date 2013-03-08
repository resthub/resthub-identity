package org.resthub.identity.service;

import org.resthub.common.service.CrudService;
import org.resthub.identity.model.Permission;

/**
 * Permission service interface
 *
 */
public interface PermissionService extends CrudService<Permission, Long> {

	public Permission findByCode(String code);
}
