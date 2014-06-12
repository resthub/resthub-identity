package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.repository.impl.DefaultGroupRepository;
import org.resthub.identity.core.service.AbstractGroupService;
import org.resthub.identity.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Sample for bean override
 */
public class GroupServiceImpl extends AbstractGroupService<Group, Long, DefaultGroupRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Inject
    @Named("groupRepository")
    @Override
    public void setRepository(DefaultGroupRepository defaultGroupRepository) {
        LOGGER.info("Entering in specific application service !");
        super.setRepository(defaultGroupRepository);
    }
}
