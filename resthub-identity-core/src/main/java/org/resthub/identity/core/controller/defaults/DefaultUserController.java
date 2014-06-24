package org.resthub.identity.core.controller.defaults;

import org.resthub.identity.core.controller.impl.UserControllerImpl;
import org.resthub.identity.core.service.RoleService;
import org.resthub.identity.core.service.defaults.DefaultUserService;
import org.resthub.identity.model.User;
import org.resthub.identity.core.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Front controller for User Management<br/>
 * Only ADMINS can access to the globality of this API<br/>
 * Specific permissions are given when useful
 */
@Profile(value = "resthub-identity-user")
@Controller(value = "userController")
@RequestMapping("/api/user")
public class DefaultUserController extends UserControllerImpl<User, Long, DefaultUserService> {
    @Override
    @Inject
    @Named("userService")
    public void setService(DefaultUserService userService) {
        super.setService(userService);
    }
}
