package org.resthub.identity.core.service;

import org.resthub.identity.model.User;
import org.resthub.identity.core.repository.UserRepository;
import org.resthub.identity.service.UserService;


/**
 * Default implementation of a User Service (can be override by creating a bean with the same name after this one)
 */
public class UserServiceImpl extends AbstractUserServiceImpl<User, UserRepository> implements UserService<User> {
	
}
