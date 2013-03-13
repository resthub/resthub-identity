package org.resthub.identity.core.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.repository.PermissionRepository;
import org.resthub.identity.model.Permission;
import org.resthub.identity.service.PermissionService;
import org.springframework.util.Assert;

/**
 * An implementation of a PermissionService.
 */
public class PermissionServiceImpl extends CrudServiceImpl<Permission, Long, PermissionRepository> implements PermissionService {

	@Override @Inject @Named("permissionRepository")
	public void setRepository(PermissionRepository permissionRepository) {
		super.setRepository(permissionRepository);
	}
	
	public Permission findByCode(String code){
		Assert.notNull(code, "Permission code must not be null");
		List<Permission> result = this.repository.findByCode(code);
		int size = result.size();
		return (size > 0 && size < 2) ? result.get(0) : null;
	}

}
