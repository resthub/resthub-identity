package org.resthub.identity.core.controller.impl;

import org.resthub.identity.core.controller.AbstractGroupController;
import org.resthub.identity.core.service.impl.DefaultGroupService;
import org.resthub.identity.model.Group;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Front controller for Group Management<br/>
 * Only ADMINS can access to this API
 */
@Controller
@RequestMapping("/api/group")
public class DefaultGroupController extends AbstractGroupController<Group, Long, DefaultGroupService> {

}
