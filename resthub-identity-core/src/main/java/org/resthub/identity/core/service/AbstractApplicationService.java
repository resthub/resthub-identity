package org.resthub.identity.core.service;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.repository.ApplicationRepository;
import org.resthub.identity.model.Application;
import org.resthub.identity.service.ApplicationService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractApplicationService<T extends Application, ID extends Serializable, R extends ApplicationRepository<T, ID>> extends CrudServiceImpl<T, ID, R> implements ApplicationService<T, ID> {
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
