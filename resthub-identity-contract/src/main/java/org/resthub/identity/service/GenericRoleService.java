package org.resthub.identity.service;

import java.util.List;

import org.resthub.common.service.CrudService;
import org.resthub.identity.model.Role;

/**
 * 
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public interface GenericRoleService<T extends Role> extends CrudService<T, Long> {

    /**
     * Find the role with its name.
     * 
     * @param name
     *            Name to search.
     * @return The corresponding role.
     */
    T findByName(String name);

    /**
     * Find the role with its name according to a pattern.
     * 
     * @param pattern
     *            The pattern to look for.
     * @return A list of roles corresponding to the pattern.
     */
    List<T> findByNameLike(String pattern);
}
