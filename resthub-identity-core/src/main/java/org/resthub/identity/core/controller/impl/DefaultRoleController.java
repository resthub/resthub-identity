package org.resthub.identity.core.controller.impl;

import org.resthub.identity.core.controller.AbstractRoleController;
import org.resthub.identity.core.repository.AbstractRoleRepository;
import org.resthub.identity.core.service.AbstractRoleService;
import org.resthub.identity.core.service.impl.DefaultRoleService;
import org.resthub.identity.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/role")
public class DefaultRoleController extends AbstractRoleController<Role, Long, AbstractRoleService<Role, Long, AbstractRoleRepository<Role, Long>>> {

}
