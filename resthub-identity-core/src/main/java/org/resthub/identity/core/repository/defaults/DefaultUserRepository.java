package org.resthub.identity.core.repository.defaults;

import org.resthub.identity.core.repository.UserRepository;
import org.resthub.identity.model.User;
import org.springframework.context.annotation.Profile;

import javax.inject.Named;

@Profile(value = "resthub-identity-user")
@Named("userRepository")
public interface DefaultUserRepository extends UserRepository<User, Long> {

}
