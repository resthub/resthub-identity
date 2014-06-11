package org.resthub.identity.webapp.repository;

import org.resthub.identity.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface GroupRepository<T extends Group, ID extends Serializable> extends JpaRepository<T, ID> {

	/**
	 * Gets the groups of a group.
	 * 
	 * @param groupName The name of the group.
	 * @return A list of groups corresponding to the given group.
	 */
	@Query("SELECT DISTINCT g.groups FROM Group g WHERE g.name = :groupName")
	List<T> findGroupsFromGroup(@Param("groupName") String groupName);
	
	/**
	 * Gets the groups of a group.
	 * 
	 * @param groupName
	 *            The name of the group.
	 * @return A list of groups corresponding to the given group.
	 */
    @Query("SELECT DISTINCT g.roles FROM Group g WHERE g.name = :groupName")
	List<T> findRolesFromGroup(@Param("groupName") String groupName);

    /**
     * Find a {@link org.resthub.identity.model.Group} from its name
     *
     * @param name name to search for
     *
     * @return the found Group
     */
    T findByName(String name);
}
