package org.resthub.identity.webapp.controller;

import org.resthub.identity.core.controller.AbstractUserController;
import org.resthub.identity.core.service.impl.DefaultUserService;
import org.resthub.identity.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Front controller for User Management<br/>
 * Only ADMINS can access to the globality of this API<br/>
 * Specific permissions are given when useful
 */
@Primary
@Controller
@RequestMapping("/api/user")
public class UserController extends AbstractUserController<User, Long, DefaultUserService> {
    /**
     * Override this methods in order to secure it *
     */
    @Secured(value = "ROLE_OVERRIDEN")
    @Override
    public User create(@RequestBody User resource) {
        return super.create(resource);
    }
}
