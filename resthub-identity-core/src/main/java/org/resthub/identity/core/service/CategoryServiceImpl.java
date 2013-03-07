package org.resthub.identity.core.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.identity.model.Category;
import org.resthub.identity.core.repository.CategoryRepository;
import org.resthub.identity.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * An implementation of a CategoryService.
 */
@Named("categoryService")
public class CategoryServiceImpl extends AbstractTraceableServiceImpl<Category,CategoryRepository> implements CategoryService {

	@Override
	@Inject
	@Named("categoryRepository")
	public void setRepository(CategoryRepository categoryRepository) {
		super.setRepository(categoryRepository);
	}
	
	/**
	 * Retrieves a category by his name
	 * @param name
	 * 			the name of the category
	 * @return the corresponding Category object if found, null otherwise
	 */
	public Category findByName(String name){
		Assert.notNull(name, "Category name must not be null");
		List<Category> result = this.repository.findByName(name);
		int size = result.size();
		return (size > 0 && size < 2) ? result.get(0) : null;
	}
	
	@Transactional
	public List<Category> findAllTopCategories(){
		List<Category> categories = this.repository.findAllTopCategories();
		for (Category c : categories){
			this.initialize(c);
		}
		return categories;
	}
	
	private void initialize(Category c){
		for (Category child : c.getCategories()){
			this.initialize(child);
		}
	}

}
