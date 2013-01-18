package org.resthub.identity.repository;

import java.util.List;

import org.resthub.identity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
	
	/**
	 * Find a list of {@link Permission} from code
	 * 
	 * @param code
	 *            code to search for
	 * 
	 * @return the list of found Permission (empty if not found)
	 */
	List<Permission> findByCode(String code);

}
