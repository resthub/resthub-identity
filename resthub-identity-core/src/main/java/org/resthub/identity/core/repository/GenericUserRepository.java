package org.resthub.identity.core.repository;

import java.util.List;

import org.resthub.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public interface GenericUserRepository<T extends User> extends JpaRepository<T, Long> {

    /**
     * Find a list of {@link User} from login
     *
     * @param login
     *            login to search for
     *
     * @return the list of found users (empty if not found)
     */
    List<T> findByLogin(String login);

    /**
     * Gets the user of a group.
     * Override this methode in order to customize @Query Spring Data annotation
     *
     * @param groupName The name of the group.
     * @return A list of users corresponding to the given group.
     */
    List<T> getUsersFromGroup(@Param("groupName") String groupName);

}
