package org.resthub.identity.webapp.repository;

import org.resthub.identity.core.repository.AbstractRoleRepository;
import org.resthub.identity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface RoleRepository extends AbstractRoleRepository<Role, Long> {

}
