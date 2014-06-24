package org.resthub.identity.core.controller.defaults;

import org.resthub.identity.core.controller.impl.RoleControllerImpl;
import org.resthub.identity.core.service.UserService;
import org.resthub.identity.core.service.defaults.DefaultRoleService;
import org.resthub.identity.model.Role;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;

@Profile(value = "resthub-identity-role")
@Controller(value = "roleController")
@RequestMapping("/api/role")
public class DefaultRoleController extends RoleControllerImpl<Role, Long, DefaultRoleService> {

    @Override
    @Inject
    @Named("roleService")
    public void setService(DefaultRoleService roleService) {
        super.setService(roleService);
    }

    @Override
    @Inject
    @Named("userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
