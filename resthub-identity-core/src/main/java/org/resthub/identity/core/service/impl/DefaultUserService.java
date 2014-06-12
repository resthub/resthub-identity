package org.resthub.identity.core.service.impl;

import org.resthub.identity.core.repository.impl.DefaultUserRepository;
import org.resthub.identity.core.service.AbstractUserService;
import org.resthub.identity.model.User;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("userService")
public class DefaultUserService extends AbstractUserService<User, Long, DefaultUserRepository> {
}
