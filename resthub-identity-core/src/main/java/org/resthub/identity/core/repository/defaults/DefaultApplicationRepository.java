package org.resthub.identity.core.repository.defaults;

import org.resthub.identity.core.repository.ApplicationRepository;
import org.resthub.identity.model.Application;
import org.springframework.context.annotation.Profile;

import javax.inject.Named;

@Profile(value = "resthub-identity-user")
@Named("applicationRepository")
public interface DefaultApplicationRepository extends ApplicationRepository<Application, Long> {

}
