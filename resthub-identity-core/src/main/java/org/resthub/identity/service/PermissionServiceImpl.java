package org.resthub.identity.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.identity.model.Permission;
import org.resthub.identity.repository.PermissionRepository;
import org.springframework.util.Assert;

/**
 * An implementation of a PermissionService.
 */
@Named("permissionService")
public class PermissionServiceImpl extends AbstractTraceableServiceImpl<Permission,PermissionRepository> implements PermissionService {

	@Override
	@Inject
	@Named("permissionRepository")
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
