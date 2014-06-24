package org.resthub.identity.core.service.impl;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.repository.ApplicationRepository;
import org.resthub.identity.core.service.ApplicationService;
import org.resthub.identity.model.Application;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

public class ApplicationServiceImpl<T extends Application, I extends Serializable, R extends ApplicationRepository<T, I>> extends CrudServiceImpl<T, I, R> implements ApplicationService<T, I> {

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
