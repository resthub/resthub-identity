package org.resthub.identity.core.repository.impl;

import org.resthub.identity.core.repository.AbstractApplicationRepository;
import org.resthub.identity.model.Application;

import javax.inject.Named;

@Named("applicationRepository")
public interface DefaultApplicationRepository extends AbstractApplicationRepository<Application, Long> {

}
