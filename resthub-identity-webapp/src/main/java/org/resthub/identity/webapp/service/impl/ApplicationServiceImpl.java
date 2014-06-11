package org.resthub.identity.webapp.service.impl;

import org.resthub.identity.core.service.AbstractApplicationService;
import org.resthub.identity.model.Application;
import org.resthub.identity.webapp.repository.ApplicationRepository;

import javax.inject.Named;

/**
 * Created by Bastien on 11/06/14.
 */
@Named("applicationService")
public class ApplicationServiceImpl extends AbstractApplicationService<Application, Long, ApplicationRepository> {
}
