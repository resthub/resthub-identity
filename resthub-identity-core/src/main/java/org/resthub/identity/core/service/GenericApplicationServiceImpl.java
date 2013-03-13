package org.resthub.identity.core.service;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.repository.GenericApplicationRepository;
import org.resthub.identity.model.Application;
import org.resthub.identity.service.GenericApplicationService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

public class GenericApplicationServiceImpl <T extends Application, TRepository extends GenericApplicationRepository<T>> extends CrudServiceImpl<T, Long, TRepository> implements GenericApplicationService<T> {

    @Inject
    @Named("applicationRepository")
    @Override
    public void setRepository(TRepository applicationRepository) {
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
        List<T> applications = this.repository.findByNameLike(pattern);
        return applications;
    }


}
