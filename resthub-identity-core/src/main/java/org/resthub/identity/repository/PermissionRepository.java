package org.resthub.identity.repository;

import org.resthub.identity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Antoine Neveu
 *
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
