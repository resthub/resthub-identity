package org.resthub.identity.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.identity.model.User;
import org.resthub.identity.repository.UserRepository;

@Named("userService")
public class UserServiceImpl extends AbstractUserServiceImpl<User, UserRepository> implements UserService {
	
	@Override
	@Inject
	@Named("userRepository")
	public void setRepository(UserRepository userRepository) {
		super.setRepository(userRepository);
	}

}
