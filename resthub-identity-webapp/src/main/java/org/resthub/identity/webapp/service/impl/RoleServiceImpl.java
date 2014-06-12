package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.repository.impl.DefaultRoleRepository;
import org.resthub.identity.core.service.AbstractRoleService;
import org.resthub.identity.model.Role;
import org.resthub.identity.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Sample for bean override
 */
@Primary
@Named("roleService")
public class RoleServiceImpl extends AbstractRoleService<Role, Long, DefaultRoleRepository> implements RoleService<Role, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Inject
    @Named("roleRepository")
    @Override
    public void setRepository(DefaultRoleRepository roleRepository) {
        LOGGER.info("Entering in specific role service !");
        super.setRepository(roleRepository);
    }
}
