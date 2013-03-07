package org.resthub.identity.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Category;
import org.resthub.identity.model.Permission;
import org.resthub.identity.core.repository.PermissionRepository;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ActiveProfiles("resthub-jpa")
public class CategoryServiceTest extends AbstractTransactionalTest {
	
	@Inject
	@Named("categoryService")
	private CategoryService categoryService;
	
	@Inject
	@Named("permissionRepository")
	private PermissionRepository permissionRepository;
	
	@BeforeMethod
    public void cleanBefore() {
    	this.categoryService.deleteAll();
    	this.permissionRepository.deleteAll();
    }
    
	// Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
    	this.categoryService.deleteAll();
    	this.permissionRepository.deleteAll();
    }

	@Test
	public void testFindAll(){
		Permission p1 = this.permissionRepository.save(new Permission("TEST_PERMISSION_1"));
		Permission p2 = this.permissionRepository.save(new Permission("TEST_PERMISSION_2"));
		Permission p3 = this.permissionRepository.save(new Permission("TEST_PERMISSION_3"));
		
		Category c1 = new Category("TEST_CATEGORY_1");
		c1.getPermissions().add(p1);
		c1.getPermissions().add(p2);
		c1 = this.categoryService.create(c1);
		Category c2 = new Category("TEST_CATEGORY_2");
		c2.getPermissions().add(p3);
		c2.getCategories().add(c1);
		c2 = this.categoryService.create(c2);
		
		List<Category> categories = this.categoryService.findAll();
		
		Assertions.assertThat(categories.size()).as("Categories not found").isEqualTo(2);
		Assertions.assertThat(categories.contains(c1)).as("Categories not found").isTrue();
		Assertions.assertThat(categories.contains(c2)).as("Categories not found").isTrue();
		
		
	}
	
	@Test
	public void testFind(){
		Permission p1 = this.permissionRepository.save(new Permission("TEST_PERMISSION_1"));
		Permission p2 = this.permissionRepository.save(new Permission("TEST_PERMISSION_2"));
		Permission p3 = this.permissionRepository.save(new Permission("TEST_PERMISSION_3"));
		
		Category c1 = new Category("TEST_CATEGORY_1");
		c1.getPermissions().add(p1);
		c1.getPermissions().add(p2);
		c1 = this.categoryService.create(c1);
		Category c2 = new Category("TEST_CATEGORY_2");
		c2.getPermissions().add(p3);
		c2.getCategories().add(c1);
		c2 = this.categoryService.create(c2);
		
		c2 = this.categoryService.findByName("TEST_CATEGORY_2");
		
		Assertions.assertThat(c2).as("Category not found").isNotNull();
		Assertions.assertThat(c2.getCategories().size()).as("Category not found").isEqualTo(1);
		Assertions.assertThat(c2.getCategories().contains(c1)).as("Category not found").isTrue();
		Assertions.assertThat(c2.getPermissions().size()).as("Permissions not found").isEqualTo(1);
		Assertions.assertThat(c2.getPermissions().contains(p3)).as("Permission not found").isTrue();
		Assertions.assertThat(c2.getCategories().get(0).getPermissions().size()).as("Permissions not found").isEqualTo(2);
		Assertions.assertThat(c2.getCategories().get(0).getPermissions().contains(p1)).as("Permission not found").isTrue();
		Assertions.assertThat(c2.getCategories().get(0).getPermissions().contains(p2)).as("Permission not found").isTrue();
	}
	
	@Test
	public void testFindAllTopCategories(){
		Permission p1 = this.permissionRepository.save(new Permission("TEST_PERMISSION_1"));
		Permission p2 = this.permissionRepository.save(new Permission("TEST_PERMISSION_2"));
		Permission p3 = this.permissionRepository.save(new Permission("TEST_PERMISSION_3"));
		
		Category c1 = new Category("TEST_CATEGORY_1");
		c1.getPermissions().add(p1);
		c1.getPermissions().add(p2);
		Category c2 = new Category("TEST_CATEGORY_2");
		c2.getPermissions().add(p3);
		c2.getCategories().add(c1);
		c1.setParent(c2);
		c2 = this.categoryService.create(c2);
		c1 = this.categoryService.create(c1);
		
		List<Category> parents = this.categoryService.findAllTopCategories();
		
		Assertions.assertThat(parents).as("Category not found").isNotNull();
		Assertions.assertThat(parents.size()).as("Category not found").isEqualTo(1);
		Assertions.assertThat(parents.get(0).getPermissions().size()).as("Permission not found").isEqualTo(1);
		Assertions.assertThat(parents.get(0).getCategories().size()).as("Category not found").isEqualTo(1);
		Assertions.assertThat(parents.get(0).getCategories().get(0).getParent().equals(c2)).as("Category not found").isTrue();
		Assertions.assertThat(parents.get(0).getCategories().get(0).getPermissions().size()).as("Permission not found").isEqualTo(2);
	}
    
}
