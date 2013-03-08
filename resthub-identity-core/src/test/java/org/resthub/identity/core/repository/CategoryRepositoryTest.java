package org.resthub.identity.core.repository;

import javax.inject.Inject;
import javax.inject.Named;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.core.repository.CategoryRepository;
import org.resthub.identity.model.Category;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ActiveProfiles("resthub-jpa")
public class CategoryRepositoryTest extends AbstractTransactionalTest {

	private static final String CATEGORY_NAME = "TestCategory";
	private static final String NEW_CATEGORY_NAME = "NewCategory";

	@Inject
	@Named("categoryRepository")
	private CategoryRepository categoryRepository;

	// Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
    	categoryRepository.deleteAll();
    }
    
	// Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
    	categoryRepository.deleteAll();
    }
    
	@Test
	public void testCreate() {
		Category category = new Category();
		category.setName(CATEGORY_NAME);
		category = categoryRepository.save(category);
		Assertions.assertThat(category).as("Category not created !").isNotNull();
	}

	@Test
	public void testUpdate() {
		// Create category
		Category category = new Category();
		category.setName(CATEGORY_NAME);
		category = categoryRepository.save(category);
		Assertions.assertThat(category).as("Category not created !").isNotNull();
		// Modify the created category
		Category category1 = categoryRepository.findOne(category.getId());
		category1.setName(NEW_CATEGORY_NAME);
		categoryRepository.save(category1);
		// Récupérer le groupe pour vérifier s'il a bien été modifié
		Category category2 = categoryRepository.findOne(category.getId());
		Assertions.assertThat(category2.getName()).as("Category not updated!").isEqualTo(NEW_CATEGORY_NAME);
	}

}
