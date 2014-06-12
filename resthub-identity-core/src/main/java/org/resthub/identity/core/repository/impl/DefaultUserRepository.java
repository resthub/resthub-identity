package org.resthub.identity.core.repository.impl;

import org.resthub.identity.core.repository.AbstractUserRepository;
import org.resthub.identity.model.User;

import javax.inject.Named;

@Named("userRepository")
public interface DefaultUserRepository extends AbstractUserRepository<User, Long> {

}
