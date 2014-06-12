package org.resthub.identity.core.repository.impl;

import org.resthub.identity.core.repository.AbstractRoleRepository;
import org.resthub.identity.model.Role;

import javax.inject.Named;

@Named("roleRepository")
public interface DefaultRoleRepository extends AbstractRoleRepository<Role, Long> {

}
