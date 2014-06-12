package org.resthub.identity.core.service.impl;

import org.resthub.identity.core.repository.impl.DefaultGroupRepository;
import org.resthub.identity.core.service.AbstractGroupService;
import org.resthub.identity.model.Group;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("groupService")
public class DefaultGroupService extends AbstractGroupService<Group, Long, DefaultGroupRepository> {
}
