package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.repository.GroupRepository;
import org.resthub.identity.core.service.AbstractGroupService;
import org.resthub.identity.model.Group;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("groupService")
public class GroupServiceImpl extends AbstractGroupService<Group, Long, GroupRepository<Group, Long>> {
}
