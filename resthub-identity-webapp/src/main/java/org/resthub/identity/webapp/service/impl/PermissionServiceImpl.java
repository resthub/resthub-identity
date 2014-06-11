package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.service.AbstractPermissionService;
import org.resthub.identity.model.Permission;
import org.resthub.identity.webapp.repository.PermissionRepository;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("permissionService")
public class PermissionServiceImpl extends AbstractPermissionService<Permission, Long, PermissionRepository> {
}
