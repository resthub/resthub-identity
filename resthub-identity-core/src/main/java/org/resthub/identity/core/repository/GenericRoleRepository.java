package org.resthub.identity.core.repository;

import org.resthub.identity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenericRoleRepository<T extends Role> extends JpaRepository<T, Long> {

	/**
	 * Find a {@link org.resthub.identity.model.Role} from its name
	 * 
	 * @param name name to search for
	 * 
	 * @return the found role
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
