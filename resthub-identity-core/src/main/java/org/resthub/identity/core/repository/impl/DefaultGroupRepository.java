package org.resthub.identity.core.repository.impl;

import org.resthub.identity.core.repository.AbstractGroupRepository;
import org.resthub.identity.model.Group;

import javax.inject.Named;

@Named("groupRepository")
public interface DefaultGroupRepository extends AbstractGroupRepository<Group, Long> {

}
