package org.resthub.identity.service;

import java.util.List;

import org.resthub.common.service.CrudService;
import org.resthub.identity.model.Category;

/**
 * Category service interface
 *
 */
public interface CategoryService extends CrudService<Category, Long> {

	/**
	 * Retrieves a category by his name
	 * @param name
	 * 			the name of the category
	 * @return the corresponding Category object if found, null otherwise
	 */
	public Category findByName(String name);
	
	public List<Category> findAllTopCategories();
}
