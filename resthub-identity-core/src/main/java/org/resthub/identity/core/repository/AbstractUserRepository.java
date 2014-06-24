package org.resthub.identity.core.repository;

import org.resthub.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface AbstractUserRepository<T extends User, I extends Serializable> extends JpaRepository<T, I> {

    /**
     * Find a list of {@link User} from login
     *
     * @param login login to search for
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
    @Query("SELECT DISTINCT u FROM User u JOIN u.groups g WHERE g.name = :groupName")
    List<T> getUsersFromGroup(@Param("groupName") String groupName);

}
