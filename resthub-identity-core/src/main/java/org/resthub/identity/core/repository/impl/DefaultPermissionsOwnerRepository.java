package org.resthub.identity.core.repository.impl;

import org.resthub.identity.core.repository.AbstractPermissionsOwnerRepository;
import org.resthub.identity.model.PermissionsOwner;

import javax.inject.Named;

/**
 * Repository for generic operations on AbstractPermissionsOwner entities.
 * 
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
@Named("permissionsOwnerRepository")
public interface DefaultPermissionsOwnerRepository extends AbstractPermissionsOwnerRepository<PermissionsOwner, Long> {

}
