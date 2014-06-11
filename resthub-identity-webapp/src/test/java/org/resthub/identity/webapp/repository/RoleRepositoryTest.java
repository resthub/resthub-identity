package org.resthub.identity.webapp.repository;

import javax.inject.Inject;
import javax.inject.Named;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Role;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ActiveProfiles("resthub-jpa")
public class RoleRepositoryTest extends AbstractTransactionalTest {

	private static final String ROLE_NAME = "TestRole";
	private static final String NEW_ROLE_NAME = "NewRole";

	@Inject
	@Named("roleRepository")
	private org.resthub.identity.core.repository.RoleRepository<Role, Long> roleRepository;
	
	// Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
        roleRepository.deleteAll();
    }
    
	// Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
    	roleRepository.deleteAll();
    }
    
	@Test
	public void testCreate() {
		Role role = new Role(ROLE_NAME + Math.round(Math.random() * 100));
		role = roleRepository.save(role);
		Assertions.assertThat(role).as("Role not created !").isNotNull();
	}

	@Test
	public void testUpdate() {
		// Créer un groupe
		Role role = new Role(ROLE_NAME + Math.round(Math.random() * 100));
		role = roleRepository.save(role);
		Assertions.assertThat(role).as("Role not created !").isNotNull();
		// Récupérer le groupe créé et le modifier
		Role role1 = roleRepository.findOne(role.getId());
		role1.setName(NEW_ROLE_NAME);
		roleRepository.save(role1);
		// Récupérer le groupe pour vérifier s'il a bien été modifié
		Role role2 = roleRepository.findOne(role.getId());
		Assertions.assertThat(role2.getName()).as("Role not updated!").isEqualTo(NEW_ROLE_NAME);
	}
	
}
