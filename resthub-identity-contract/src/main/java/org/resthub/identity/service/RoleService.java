package org.resthub.identity.service;

import org.resthub.common.service.CrudService;
import org.resthub.identity.model.Role;

import java.io.Serializable;
import java.util.List;

/**
 * Default implementation of a Role Service (can be override by creating a bean with the same name after this one)
 *
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public interface RoleService<T extends Role, ID extends Serializable> extends CrudService<T, ID> {
    /**
     * Find the role with its name.
     *
     * @param name Name to search.
     * @return The corresponding role.
     */
    T findByName(String name);

    /**
     * Find the role with its name according to a pattern.
     *
     * @param pattern The pattern to look for.
     * @return A list of roles corresponding to the pattern.
     */
    List<T> findByNameLike(String pattern);

}
