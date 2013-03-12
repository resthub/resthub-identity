package org.resthub.identity.core.repository;

import org.resthub.identity.model.Group;
import org.resthub.identity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenericGroupRepository<T extends Group> extends JpaRepository<T, Long> {

	/**
	 * Find a list of {@link org.resthub.identity.model.Group} from name
	 * 
	 * @param name
	 *            name to search for
	 * 
	 * @return the list of found Group (empty if not found)
	 */
	List<T> findByName(String name);
	
	/**
	 * Gets the groups of a group.
	 *
	 * @param groupName The name of the group.
	 * @return A list of groups corresponding to the given group.
	 */
	List<T> findGroupsFromGroup(@Param("groupName") String groupName);
	
	/**
	 * Gets the groups of a group.
	 *
	 * @param groupName The name of the group.
	 * @return A list of groups corresponding to the given group.
	 */
	List<Role> findRolesFromGroup(@Param("groupName") String groupName);
}
