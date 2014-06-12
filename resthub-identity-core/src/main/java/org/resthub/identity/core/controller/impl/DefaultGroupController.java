package org.resthub.identity.core.controller.impl;

import org.resthub.identity.core.controller.AbstractGroupController;
import org.resthub.identity.core.repository.AbstractGroupRepository;
import org.resthub.identity.core.service.AbstractGroupService;
import org.resthub.identity.model.Group;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Front controller for Group Management<br/>
 * Only ADMINS can access to this API
 */
@Controller(value = "groupController")
@RequestMapping("/api/group")
public class DefaultGroupController extends AbstractGroupController<Group, Long, AbstractGroupService<Group, Long, AbstractGroupRepository<Group, Long>>> {

}
