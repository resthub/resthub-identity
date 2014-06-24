package org.resthub.identity.core.service.defaults;

import org.resthub.identity.core.repository.UserRepository;
import org.resthub.identity.core.repository.defaults.DefaultGroupRepository;
import org.resthub.identity.core.service.RoleService;
import org.resthub.identity.core.service.impl.GroupServiceImpl;
import org.resthub.identity.model.Group;
import org.springframework.context.annotation.Profile;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Profile(value = "resthub-identity-group")
@Named("groupService")
public class DefaultGroupService extends GroupServiceImpl<Group, Long, DefaultGroupRepository> {
    @Inject
    @Named("groupRepository")
    @Override
    public void setRepository(DefaultGroupRepository groupRepository) {
        super.setRepository(groupRepository);
    }

    @Inject
    @Named("userRepository")
    @Override
    public void setUserRepository(UserRepository userRepository) {
        super.setUserRepository(userRepository);
    }

    @Inject
    @Named("roleService")
    @Override
    public void setRoleService(RoleService roleService) {
        super.setRoleService(roleService);
    }
}
