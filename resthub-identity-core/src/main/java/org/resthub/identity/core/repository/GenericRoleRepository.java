package org.resthub.identity.core.repository;

import org.resthub.identity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenericRoleRepository<T extends Role> extends JpaRepository<T, Long> {

	/**
	 * Find a list of {@link org.resthub.identity.model.Role} from name
	 * 
	 * @param name
	 *            name to search for
	 * 
	 * @return the list of found roles (empty if not found)
	 */
	List<T> findByName(String name);

	/**
	 * Find the role with its name according to a pattern.
	 * 
	 * @param pattern
	 *            The pattern to look for.
	 * @return A list of roles corresponding to the pattern.
	 */
	List<T> findByNameLike(String pattern);
}
