package org.resthub.identity.core.repository.impl;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.core.repository.AbstractGroupRepository;
import org.resthub.identity.model.*;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("resthub-jpa")
public class DefaultUserRepositoryTest extends AbstractTransactionalTest {

	private static final String GROUP_NAME = "TestGroup";

	private static final String USER_LOGIN = "TestUserLogin";
	private static final String USER_FIRST_NAME = "TestUserFirstName";
	private static final String USER_LAST_NAME = "TestUserLastName";
	private static final String USER_PASSWORD = "P@ssw0rd";

	private static final String USER_PERMISSION_1 = "ADMIN";
	private static final String USER_PERMISSION_2 = "USER";

	@Inject
	@Named("groupRepository")
	protected AbstractGroupRepository<Group, Long> groupRepository;
	
	@Inject
	@Named("roleRepository")
	protected DefaultRoleRepository roleRepository;
	
	@Inject
	@Named("permissionsOwnerRepository")
	protected DefaultPermissionsOwnerRepository permissionsOwnerRepository;

	@Inject
	@Named("userRepository")
	private DefaultUserRepository userRepository;
	
	@Inject
	@Named("permissionRepository")
	private DefaultPermissionRepository permissionRepository;
	
	// Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
        roleRepository.deleteAll();
        userRepository.deleteAll();
        permissionRepository.deleteAll();
    }
    
	// Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
    	roleRepository.deleteAll();
        userRepository.deleteAll();
        permissionRepository.deleteAll();
    }
    
	/*
	 * Tests
	 */
	
	@Test
	public void testUpdate() {
		User user = userRepository.save(createTestUser());
		
		User user1 = userRepository.findOne(user.getId());
		user1.setEmail("test@plop.fr");
		userRepository.save(user1);

		User user2 = userRepository.findOne(user.getId());
		Assertions.assertThat(user2.getEmail()).as("User not updated!").isEqualTo("test@plop.fr");
	}

	@Test
	public void testGetAndPermissions() {
		User user = createTestUser();
		user = userRepository.save(user);

		// when we search him by his login and password
		List<User> users = userRepository.findByLogin(user.getLogin());
		Assertions.assertThat(users).as("Users list is null!").isNotNull();
		Assertions.assertThat(users.get(0)).as("First element is null!").isNotNull();
		Assertions.assertThat(users.get(0).getLogin()).as("Login is not good!").isEqualTo(user.getLogin());
		Assertions.assertThat(users.get(0).getPermissions()).isNotNull();
		Assertions.assertThat(users.get(0).getPermissions().isEmpty()).isFalse();
		Assertions.assertThat(users.get(0).getPermissions().get(0)).as("First permission should be equals to "+user.getPermissions().get(0)+"!").isEqualTo(user.getPermissions().get(0));
		Assertions.assertThat(users.get(0).getPermissions().get(1)).as("Second permission should be equals to "+user.getPermissions().get(1)+"!").isEqualTo(user.getPermissions().get(1));
	}

	@Test
	public void testGetUsersFromGroup() {
		Group group = createTestGroup();
		group = groupRepository.save(group);

		User user = createTestUser();
		user.getGroups().add(group);
		userRepository.save(user);

		List<User> users = userRepository.getUsersFromGroup(group.getName());
		Assertions.assertThat(users).as("Users should not be null").isNotNull();
		Assertions.assertThat(users.isEmpty()).as("Users should not empty").isFalse();
	}
	
	@Test
	public void testGetWithRoles() {
		Role role = createTestRole();
		role = roleRepository.save(role);

		User user = createTestUser();
		user.getRoles().add(role);
		userRepository.save(user);

		List<PermissionsOwner> users = permissionsOwnerRepository.getWithRoles(Arrays.asList(role.getName()));
		Assertions.assertThat(users).as("Users should not be null").isNotNull();
		Assertions.assertThat(users.isEmpty()).as("Users should not empty").isFalse();
	}
	
	
	/*
	 * Méthodes génériques
	 */
	
	private Group createTestGroup() {
		Group group = new Group();
		group.setName(GROUP_NAME + Math.round(Math.random() * 100));
		return group;
	}
	
	private Role createTestRole() {
		Role role = new Role(GROUP_NAME + Math.round(Math.random() * 100));
		return role;
	}

	private User createTestUser() {
		Permission p1 = permissionRepository.save(new Permission(USER_PERMISSION_1));
		Permission p2 = permissionRepository.save(new Permission(USER_PERMISSION_2));
		User user = new User();
		user.setLogin(USER_LOGIN + Math.round(Math.random() * 100));
		user.setFirstName(USER_FIRST_NAME);
		user.setLastName(USER_LAST_NAME);
		user.setPassword(USER_PASSWORD);
		user.setEmail(USER_LOGIN + Math.round(Math.random() * 100) + "@test.fr");

		user.getPermissions().addAll(new ArrayList<Permission>(Arrays.asList(p1, p2)));
		return user;
	}
	
}
