package org.resthub.identity.webapp.controller;

import org.resthub.identity.core.controller.AbstractGroupController;
import org.resthub.identity.model.Group;
import org.resthub.identity.service.GroupService;
import org.resthub.identity.webapp.service.impl.GroupServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 */
@Primary
@Controller
@RequestMapping("/api/group")
public class GroupController extends AbstractGroupController<Group, String, GroupService<Group, String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Inject
    @Named("groupService")
    @Override
    public void setService(GroupService service) {
        LOGGER.info("Entering in specific group controller !");
        super.setService(service);
    }

}
