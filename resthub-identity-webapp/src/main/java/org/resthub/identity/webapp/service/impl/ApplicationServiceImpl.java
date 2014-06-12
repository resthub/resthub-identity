package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.repository.impl.DefaultApplicationRepository;
import org.resthub.identity.core.service.AbstractApplicationService;
import org.resthub.identity.model.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Sample for bean override
 */
public class ApplicationServiceImpl extends AbstractApplicationService<Application, Long, DefaultApplicationRepository> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Inject
    @Named("applicationRepository")
    @Override
    public void setRepository(DefaultApplicationRepository applicationRepository) {
        LOGGER.info("Entering in specific application service !");
        super.setRepository(applicationRepository);
    }
}
