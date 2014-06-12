package org.resthub.identity.core.controller.impl;

import org.resthub.identity.core.controller.AbstractUserController;
import org.resthub.identity.core.repository.AbstractUserRepository;
import org.resthub.identity.core.service.AbstractUserService;
import org.resthub.identity.core.service.impl.DefaultUserService;
import org.resthub.identity.model.User;
import org.resthub.identity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Front controller for User Management<br/>
 * Only ADMINS can access to the globality of this API<br/>
 * Specific permissions are given when useful
 */
@Controller(value = "userController")
@RequestMapping("/api/user")
public class DefaultUserController extends AbstractUserController<User, Long, UserService<User, Long>> {

}
