package org.resthub.identity.webapp.repository;

import org.resthub.identity.core.repository.UserRepository;
import org.resthub.identity.model.User;
import org.springframework.context.annotation.Primary;

import javax.inject.Named;

@Named("userRepository")
public interface MyUserRepository extends UserRepository<User, String> {

}
