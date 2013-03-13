package org.resthub.identity.core.service;

import org.resthub.identity.core.repository.ApplicationRepository;
import org.resthub.identity.model.Application;
import org.resthub.identity.service.GenericApplicationService;

public class ApplicationServiceImpl extends GenericApplicationServiceImpl<Application, ApplicationRepository> implements GenericApplicationService<Application> {

}
