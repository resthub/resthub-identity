package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.service.AbstractGroupService;
import org.resthub.identity.model.Group;
import org.resthub.identity.webapp.repository.GroupRepository;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("groupService")
public class GroupServiceImpl extends AbstractGroupService<Group, Long, GroupRepository> {
}
