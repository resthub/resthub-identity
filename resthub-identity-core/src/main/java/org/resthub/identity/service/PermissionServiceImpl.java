package org.resthub.identity.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.identity.model.Permission;
import org.resthub.identity.repository.PermissionRepository;

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
	
	

}
