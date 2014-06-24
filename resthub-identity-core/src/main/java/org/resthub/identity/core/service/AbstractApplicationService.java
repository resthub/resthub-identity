package org.resthub.identity.core.service;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.repository.AbstractApplicationRepository;
import org.resthub.identity.model.Application;
import org.resthub.identity.service.ApplicationService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractApplicationService<T extends Application, I extends Serializable, R extends AbstractApplicationRepository<T, I>> extends CrudServiceImpl<T, I, R> implements ApplicationService<T, I> {
    @Inject
    @Named("applicationRepository")
    @Override
    public void setRepository(R applicationRepository) {
        super.setRepository(applicationRepository);
    }

    @Override
    public T findByName(String name) {
        return this.repository.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findByNameLike(String pattern) {
        return this.repository.findByNameLike(pattern);
    }
}
