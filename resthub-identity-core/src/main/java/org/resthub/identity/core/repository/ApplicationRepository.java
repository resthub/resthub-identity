package org.resthub.identity.core.repository;

import org.resthub.identity.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface ApplicationRepository<T extends Application, I extends Serializable> extends JpaRepository<T, I> {
    /**
     * Find an {@link org.resthub.identity.model.Application} from its name
     *
     * @param name name to search for
     * @return the application found
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
