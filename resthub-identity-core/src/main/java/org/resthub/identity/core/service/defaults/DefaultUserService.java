package org.resthub.identity.core.service.defaults;

import org.resthub.identity.core.repository.PermissionsOwnerRepository;
import org.resthub.identity.core.repository.defaults.DefaultUserRepository;
import org.resthub.identity.core.service.ApplicationService;
import org.resthub.identity.core.service.GroupService;
import org.resthub.identity.core.service.RoleService;
import org.resthub.identity.core.service.impl.UserServiceImpl;
import org.resthub.identity.model.User;
import org.springframework.context.annotation.Profile;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Profile(value = "resthub-identity-user")
@Named("userService")
public class DefaultUserService extends UserServiceImpl<User, Long, DefaultUserRepository> {
    @Inject
    @Named("userRepository")
    @Override
    public void setRepository(DefaultUserRepository userRepository) {
        super.setRepository(userRepository);
    }

    @Inject
    @Named("permissionsOwnerRepository")
    @Override
    public void setPermissionsOwnerRepository(PermissionsOwnerRepository permissionsOwnerRepository) {
        super.setPermissionsOwnerRepository(permissionsOwnerRepository);
    }

    @Inject
    @Named("groupService")
    @Override
    public void setGroupService(GroupService groupService) {
        super.setGroupService(groupService);
    }

    @Inject
    @Named("applicationService")
    @Override
    public void setApplicationService(ApplicationService applicationService) {
        super.setApplicationService(applicationService);
    }

    @Inject
    @Named("roleService")
    @Override
    public void setRoleService(RoleService roleService) {
        super.setRoleService(roleService);
    }

}
