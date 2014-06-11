package org.resthub.identity.core.repository;

import java.io.Serializable;
import java.util.List;

import org.resthub.identity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AbstractPermissionRepository<T extends Permission, ID extends Serializable> extends JpaRepository<T, ID> {
	
	/**
	 * Find a list of {@link Permission} from code
	 * 
	 * @param code
	 *            code to search for
	 * 
	 * @return the list of found Permission (empty if not found)
	 */
	List<T> findByCode(String code);

}
