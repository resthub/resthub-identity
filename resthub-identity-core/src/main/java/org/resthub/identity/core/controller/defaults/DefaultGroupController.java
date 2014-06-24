package org.resthub.identity.core.controller.defaults;

import org.resthub.identity.core.controller.impl.GroupControllerImpl;
import org.resthub.identity.core.service.RoleService;
import org.resthub.identity.core.service.UserService;
import org.resthub.identity.core.service.defaults.DefaultGroupService;
import org.resthub.identity.model.Group;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Front controller for Group Management<br/>
 * Only ADMINS can access to this API
 */
@RequestMapping("/api/group")
@Controller(value = "groupController")
@Profile(value = "resthub-identity-group")
public class DefaultGroupController extends GroupControllerImpl<Group, Long, DefaultGroupService> {

    @Override
    @Inject
    @Named("groupService")
    public void setService(DefaultGroupService groupService) {
        super.setService(groupService);
    }

    @Override
    @Inject
    @Named("userService")
    public void setUserService(UserService userService) {
        super.setUserService(userService);
    }

    @Override
    @Inject
    @Named("roleService")
    public void setRoleService(RoleService roleService) {
        super.setRoleService(roleService);
    }
}
