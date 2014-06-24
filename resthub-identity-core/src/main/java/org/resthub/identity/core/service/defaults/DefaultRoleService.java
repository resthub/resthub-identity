package org.resthub.identity.core.service.defaults;

import org.resthub.identity.core.repository.PermissionsOwnerRepository;
import org.resthub.identity.core.repository.defaults.DefaultApplicationRepository;
import org.resthub.identity.core.repository.defaults.DefaultRoleRepository;
import org.resthub.identity.core.service.GroupService;
import org.resthub.identity.core.service.UserService;
import org.resthub.identity.core.service.impl.RoleServiceImpl;
import org.resthub.identity.model.Role;
import org.springframework.context.annotation.Profile;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Profile(value = "resthub-identity-role")
@Named("roleService")
public class DefaultRoleService extends RoleServiceImpl<Role, Long, DefaultRoleRepository> {
    @Inject
    @Named("roleRepository")
    @Override
    public void setRepository(DefaultRoleRepository roleRepository) {
        super.setRepository(roleRepository);
    }

    @Inject
    @Named("permissionsOwnerRepository")
    @Override
    public void setPermissionsOwnerRepository(PermissionsOwnerRepository permissionsOwnerRepository) {
        super.setPermissionsOwnerRepository(permissionsOwnerRepository);
    }

    @Inject
    @Named("userService")
    @Override
    public void setUserService(UserService userService) {
        super.setUserService(userService);
    }

    @Inject
    @Named("groupService")
    @Override
    public void setGroupService(GroupService groupService) {
        super.setGroupService(groupService);
    }
}
