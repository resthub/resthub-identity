package org.resthub.identity.core.repository;

import java.util.List;

import org.resthub.identity.model.Group;
import org.resthub.identity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long> {

	/**
	 * Find a list of {@link Group} from name
	 * 
	 * @param name
	 *            name to search for
	 * 
	 * @return the list of found Group (empty if not found)
	 */
	List<Group> findByName(String name);
	
	/**
	 * Gets the groups of a group.
	 * 
	 * @param groupName
	 *            The name of the group.
	 * @return A list of groups corresponding to the given group.
	 */
	@Query("SELECT DISTINCT g.groups FROM Group g WHERE g.name = :groupName")
	List<Group> findGroupsFromGroup(@Param("groupName") String groupName);
	
	/**
	 * Gets the groups of a group.
	 * 
	 * @param groupName
	 *            The name of the group.
	 * @return A list of groups corresponding to the given group.
	 */
	@Query("SELECT DISTINCT g.roles FROM Group g WHERE g.name = :groupName")
	List<Role> findRolesFromGroup(@Param("groupName") String groupName);
}
