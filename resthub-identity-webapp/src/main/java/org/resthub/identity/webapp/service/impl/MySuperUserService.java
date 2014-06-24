package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.service.impl.UserServiceImpl;
import org.resthub.identity.model.Group;
import org.resthub.identity.core.service.GroupService;
import org.resthub.identity.model.User;
import org.resthub.identity.webapp.repository.MyUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Sample for bean override
 */
@Named("userService")
public class MySuperUserService extends UserServiceImpl<User, String, MyUserRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySuperUserService.class);

    @Inject
    @Named("userRepository")
    @Override
    public void setRepository(MyUserRepository defaultGroupRepository) {
        LOGGER.info("Entering in specific application service !");
        super.setRepository(defaultGroupRepository);
    }
}
