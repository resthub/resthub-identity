package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.service.AbstractUserService;
import org.resthub.identity.model.User;
import org.resthub.identity.webapp.repository.UserRepository;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("userService")
public class UserServiceImpl extends AbstractUserService<User, Long, UserRepository> {
}
