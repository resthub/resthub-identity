package org.resthub.identity.core.service.impl;

import org.resthub.identity.core.repository.AbstractApplicationRepository;
import org.resthub.identity.core.repository.impl.DefaultApplicationRepository;
import org.resthub.identity.core.service.AbstractApplicationService;
import org.resthub.identity.model.Application;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("applicationService")
public class DefaultApplicationService extends AbstractApplicationService<Application, Long, AbstractApplicationRepository<Application, Long>> {
}
