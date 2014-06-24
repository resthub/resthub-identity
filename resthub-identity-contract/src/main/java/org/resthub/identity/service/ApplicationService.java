package org.resthub.identity.service;

import org.resthub.common.service.CrudService;
import org.resthub.identity.model.Application;

import java.io.Serializable;
import java.util.List;

public interface ApplicationService<T extends Application, I extends Serializable> extends CrudService<T, I> {

    /**
     * Find the application with its name.
     *
     * @param name Name to search.
     * @return The corresponding application.
     */
    T findByName(String name);

    /**
     * Find the application with its name according to a pattern.
     *
     * @param pattern The pattern to look for.
     * @return A list of applications corresponding to the pattern.
     */
    List<T> findByNameLike(String pattern);
}
