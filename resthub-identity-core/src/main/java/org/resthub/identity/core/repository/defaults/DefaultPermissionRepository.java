package org.resthub.identity.core.repository.defaults;

import org.resthub.identity.core.repository.PermissionRepository;
import org.resthub.identity.model.Permission;

import javax.inject.Named;

@Named("permissionRepository")
public interface DefaultPermissionRepository extends PermissionRepository<Permission, Long> {

}
