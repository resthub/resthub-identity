package org.resthub.identity.webapp.repository;

import org.resthub.identity.core.repository.AbstractGroupRepository;
import org.resthub.identity.model.Group;
import org.springframework.context.annotation.Primary;

import javax.inject.Named;

@Primary
@Named("groupRepository")
public interface GroupRepository extends AbstractGroupRepository<Group, String> {

}
