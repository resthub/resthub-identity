package org.resthub.identity.core.repository.defaults;

import org.resthub.identity.core.repository.PermissionsOwnerRepository;
import org.resthub.identity.model.PermissionsOwner;
import org.springframework.context.annotation.Profile;

import javax.inject.Named;

/**
 * Repository for generic operations on AbstractPermissionsOwner entities.
 *
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
@Profile(value = {"resthub-identity-role", "resthub-identity-user"})
@Named("permissionsOwnerRepository")
public interface DefaultPermissionsOwnerRepository extends PermissionsOwnerRepository<PermissionsOwner, Long> {

}
