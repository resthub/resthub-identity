package org.resthub.identity.core.service.impl;

import org.resthub.identity.core.repository.impl.DefaultPermissionRepository;
import org.resthub.identity.core.service.AbstractPermissionService;
import org.resthub.identity.model.Permission;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("permissionService")
public class DefaultPermissionService extends AbstractPermissionService<Permission, Long, DefaultPermissionRepository> {
}
