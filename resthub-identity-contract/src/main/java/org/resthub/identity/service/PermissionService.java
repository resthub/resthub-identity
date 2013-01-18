package org.resthub.identity.service;

import org.resthub.common.service.CrudService;
import org.resthub.identity.model.Permission;
import org.resthub.identity.service.tracability.TracableService;

/**
 * Permission service interface
 *
 */
public interface PermissionService extends CrudService<Permission, Long>, TracableService {

	public Permission findByCode(String code);
}
