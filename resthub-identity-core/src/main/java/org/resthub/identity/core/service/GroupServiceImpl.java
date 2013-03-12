package org.resthub.identity.core.service;

import org.resthub.identity.core.repository.GroupRepository;
import org.resthub.identity.model.Group;
import org.resthub.identity.service.GenericGroupService;


/**
 * Default implementation of a Group Service (can be override by creating a bean with the same name after this one)
 */
public class GroupServiceImpl extends GenericGroupServiceImpl<Group, GroupRepository> implements GenericGroupService<Group> {
	
}
