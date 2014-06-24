package org.resthub.identity.core.repository.defaults;

import org.resthub.identity.core.repository.GroupRepository;
import org.resthub.identity.model.Group;
import org.springframework.context.annotation.Profile;

import javax.inject.Named;

@Named("groupRepository")
@Profile(value = "resthub-identity-group")
public interface DefaultGroupRepository extends GroupRepository<Group, Long> {

}
