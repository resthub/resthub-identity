package org.resthub.identity.core.repository.defaults;

import org.resthub.identity.core.repository.RoleRepository;
import org.resthub.identity.model.Role;
import org.springframework.context.annotation.Profile;

import javax.inject.Named;

@Profile(value = "resthub-identity-role")
@Named("roleRepository")
public interface DefaultRoleRepository extends RoleRepository<Role, Long> {

}
