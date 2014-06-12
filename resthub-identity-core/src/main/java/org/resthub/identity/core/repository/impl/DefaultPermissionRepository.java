package org.resthub.identity.core.repository.impl;

import org.resthub.identity.core.repository.AbstractPermissionRepository;
import org.resthub.identity.model.Permission;

import javax.inject.Named;

@Named("permissionRepository")
public interface DefaultPermissionRepository extends AbstractPermissionRepository<Permission, Long> {

}
