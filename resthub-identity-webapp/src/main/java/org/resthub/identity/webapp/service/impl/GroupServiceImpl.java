package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.service.AbstractGroupService;
import org.resthub.identity.model.Group;
import org.resthub.identity.webapp.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Sample for bean override
 */
@Primary
@Named("groupService")
public class GroupServiceImpl extends AbstractGroupService<Group, String, GroupRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Inject
    @Named("groupRepository")
    @Override
    public void setRepository(GroupRepository defaultGroupRepository) {
        LOGGER.info("Entering in specific application service !");
        super.setRepository(defaultGroupRepository);
    }
}
