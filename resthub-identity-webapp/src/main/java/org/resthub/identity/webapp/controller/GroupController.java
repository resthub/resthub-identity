package org.resthub.identity.webapp.controller;

import org.resthub.identity.core.controller.AbstractGroupController;
import org.resthub.identity.model.Group;
import org.resthub.identity.webapp.service.impl.GroupServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 */
@Controller
@RequestMapping("/api/group")
public class GroupController extends AbstractGroupController<Group, Long, GroupServiceImpl> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Inject
    @Named("groupService")
    @Override
    public void setService(GroupServiceImpl service) {
        LOGGER.info("Entering in specific group controller !");
        super.setService(service);
    }

}
