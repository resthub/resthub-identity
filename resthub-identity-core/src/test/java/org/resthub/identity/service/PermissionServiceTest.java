package org.resthub.identity.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Permission;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ActiveProfiles("resthub-jpa")
public class PermissionServiceTest extends AbstractTransactionalTest {
		
	@Inject
	@Named("permissionService")
	private PermissionService permissionService;
	
	// Cleanup before each test
	@BeforeMethod
    public void cleanBefore() {
    	this.permissionService.deleteAll();
    }
    
	// Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
    	this.permissionService.deleteAll();
    }

	@Test
	public void testFindAll(){
		this.permissionService.create(new Permission("TEST_PERMISSION_1"));
		this.permissionService.create(new Permission("TEST_PERMISSION_2"));
		this.permissionService.create(new Permission("TEST_PERMISSION_3"));
		
		List<Permission> permissions = this.permissionService.findAll();
		
		Assertions.assertThat(permissions.size()).as("Permissions not found").isEqualTo(3);
	}
	
	@Test
	public void testFind(){
		this.permissionService.create(new Permission("TEST_PERMISSION_1"));
		this.permissionService.create(new Permission("TEST_PERMISSION_2"));
		this.permissionService.create(new Permission("TEST_PERMISSION_3"));
		
		Permission p = this.permissionService.findByCode("TEST_PERMISSION_2");
		
		Assertions.assertThat(p).as("Permission not found").isNotNull();
		Assertions.assertThat(p.getCode()).as("Permission not found").isEqualTo("TEST_PERMISSION_2");
	}
    
}
