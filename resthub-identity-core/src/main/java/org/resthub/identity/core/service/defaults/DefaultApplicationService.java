package org.resthub.identity.core.service.defaults;

import org.resthub.identity.core.repository.defaults.DefaultApplicationRepository;
import org.resthub.identity.core.service.impl.ApplicationServiceImpl;
import org.resthub.identity.model.Application;
import org.springframework.context.annotation.Profile;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Profile(value = "resthub-identity-user")
@Named("applicationService")
public class DefaultApplicationService extends ApplicationServiceImpl<Application, Long, DefaultApplicationRepository> {
    @Inject
    @Named("applicationRepository")
    @Override
    public void setRepository(DefaultApplicationRepository applicationRepository) {
        super.setRepository(applicationRepository);
    }
}
