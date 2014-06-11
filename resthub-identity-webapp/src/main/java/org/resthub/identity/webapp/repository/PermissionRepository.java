package org.resthub.identity.webapp.repository;

import org.resthub.identity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;


public interface PermissionRepository<T extends Permission, ID extends Serializable> extends JpaRepository<T, ID> {
	
	/**
	 * Find a list of {@link org.resthub.identity.model.Permission} from code
	 * 
	 * @param code
	 *            code to search for
	 * 
	 * @return the list of found Permission (empty if not found)
	 */
	List<T> findByCode(String code);

}
