package org.resthub.identity.repository;

import java.util.List;

import org.resthub.identity.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 
 * @author Antoine Neveu
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	/**
	 * Find a list of {@link Category} by name
	 * @param name
	 * 			name of the category
	 * @return the category list, empty if not found
	 */
	List<Category> findByName(String name);
	
	@Query("SELECT DISTINCT c FROM Category c WHERE c.parent IS NULL")
	List<Category> findAllTopCategories();

}
