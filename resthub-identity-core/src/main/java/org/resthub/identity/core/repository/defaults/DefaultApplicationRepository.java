package org.resthub.identity.core.repository.defaults;

import org.resthub.identity.core.repository.ApplicationRepository;
import org.resthub.identity.model.Application;

import javax.inject.Named;

@Named("applicationRepository")
public interface DefaultApplicationRepository extends ApplicationRepository<Application, Long> {

}
