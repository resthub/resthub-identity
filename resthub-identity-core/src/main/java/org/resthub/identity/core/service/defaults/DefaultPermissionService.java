package org.resthub.identity.core.service.defaults;

import org.resthub.identity.core.repository.defaults.DefaultPermissionRepository;
import org.resthub.identity.core.service.impl.PermissionServiceImpl;
import org.resthub.identity.model.Permission;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("permissionService")
public class DefaultPermissionService extends PermissionServiceImpl<Permission, Long, DefaultPermissionRepository> {
    @Inject
    @Named("permissionRepository")
    @Override
    public void setRepository(DefaultPermissionRepository permissionRepository) {
        super.setRepository(permissionRepository);
    }
}
