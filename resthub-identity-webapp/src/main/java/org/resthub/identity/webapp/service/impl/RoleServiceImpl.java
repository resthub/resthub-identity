package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.service.AbstractRoleService;
import org.resthub.identity.model.Role;
import org.resthub.identity.webapp.repository.RoleRepository;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("roleService")
public class RoleServiceImpl extends AbstractRoleService<Role, Long, RoleRepository> {
}
