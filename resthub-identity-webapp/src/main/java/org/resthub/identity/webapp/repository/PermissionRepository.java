package org.resthub.identity.webapp.repository;

import org.resthub.identity.core.repository.AbstractPermissionRepository;
import org.resthub.identity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;


public interface PermissionRepository extends AbstractPermissionRepository<Permission, Long> {

}
