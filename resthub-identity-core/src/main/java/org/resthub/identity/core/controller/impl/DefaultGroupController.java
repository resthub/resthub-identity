package org.resthub.identity.core.controller.impl;

import org.resthub.identity.core.controller.AbstractGroupController;
import org.resthub.identity.model.Group;
import org.resthub.identity.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Front controller for Group Management<br/>
 * Only ADMINS can access to this API
 */
@Controller(value = "groupController")
@RequestMapping("/api/group")
public class DefaultGroupController extends AbstractGroupController<Group, Long, GroupService<Group, Long>> {

}
