package org.resthub.identity.webapp.repository;

import org.resthub.identity.core.repository.AbstractPermissionsOwnerRepository;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.PermissionsOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for generic operations on AbstractPermissionsOwner entities.
 * 
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public interface PermissionsOwnerRepository extends AbstractPermissionsOwnerRepository<PermissionsOwner, Long> {

}
