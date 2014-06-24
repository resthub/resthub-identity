package org.resthub.identity.core.controller.impl;

import org.resthub.identity.core.controller.AbstractRoleController;
import org.resthub.identity.model.Role;
import org.resthub.identity.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "roleController")
@RequestMapping("/api/role")
public class DefaultRoleController extends AbstractRoleController<Role, Long, RoleService<Role, Long>> {

}
