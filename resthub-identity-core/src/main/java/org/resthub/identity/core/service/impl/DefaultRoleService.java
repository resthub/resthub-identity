package org.resthub.identity.core.service.impl;

import org.resthub.identity.core.repository.impl.DefaultRoleRepository;
import org.resthub.identity.core.service.AbstractRoleService;
import org.resthub.identity.model.Role;
import org.resthub.identity.service.RoleService;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("roleService")
public class DefaultRoleService extends AbstractRoleService<Role, Long, DefaultRoleRepository> implements RoleService<Role, Long> {
}