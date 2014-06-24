package org.resthub.identity.core.repository;

import org.resthub.identity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;


public interface PermissionRepository<T extends Permission, I extends Serializable> extends JpaRepository<T, I> {

    /**
     * Find a list of {@link Permission} from code
     *
     * @param code code to search for
     * @return the list of found Permission (empty if not found)
     */
    List<T> findByCode(String code);

}
