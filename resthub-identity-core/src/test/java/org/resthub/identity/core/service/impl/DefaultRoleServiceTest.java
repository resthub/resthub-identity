package org.resthub.identity.core.service.impl;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Role;
import org.resthub.identity.service.RoleService;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@ContextConfiguration(locations = {"classpath:hikariCPContext.xml"})
@ActiveProfiles({"resthub-jpa", "resthub-pool-hikaricp"})
public class DefaultRoleServiceTest extends AbstractTransactionalTest {

    @Inject
    @Named("roleService")
    private RoleService<Role, Long> roleService;

    /**
     * Generate a random role name based on a string and a randomized number.
     *
     * @return A unique role name.
     */
    private String generateRandomRoleName() {
        return "RoleName" + Math.round(Math.random() * 100000);
    }

    // Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
        roleService.deleteAll();
    }

    // Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
        roleService.deleteAll();
    }

    public Role createTestEntity() {
        Role testRole = new Role(generateRandomRoleName());
        return testRole;
    }

    @Test
    public void testUpdate() {
        // Given a new role
        Role testRole = this.createTestEntity();
        testRole = roleService.create(testRole);

        // When I update this role
        final String newRoleName = this.generateRandomRoleName();
        testRole.setName(newRoleName);
        testRole = roleService.update(testRole);

        // Then the modification is done.
        Assertions.assertThat(testRole.getName()).as("Role not updated!").isEqualTo(newRoleName);
    }

    @Test
    public void shouldFindByName() {
        // Given a new role
        Role r = this.createTestEntity();
        r = roleService.create(r);

        // When I find it by name
        Role roleFromName = roleService.findByName(r.getName());

        // Then I can find it
        Assertions.assertThat(roleFromName).as("The role should be found").isNotNull();
        Assertions.assertThat(roleFromName).as("The role found should be the same as the one created").isEqualTo(r);
    }

    @Test
    public void shouldNotFindRoleWithWeirdName() {
        // Given a new role
        Role r = this.createTestEntity();
        r = roleService.create(r);

        // When I find it with a weird name
        Role roleFromName = roleService.findByName("InventedNameThatShouldntBringAnyResult");

        // Then I can find it
        Assertions.assertThat(roleFromName).as("No role should be found").isNull();
    }

    @Test
    public void shouldFindNameWithWildcard() {
        // Given a new role
        Role r = this.createTestEntity();
        r = roleService.create(r);

        // When I search for a part of its name
        List<Role> roles = roleService.findByNameLike(r.getName().substring(0, 9) + "%");

        // Then the list is not empty and contains our role
        Assertions.assertThat(roles.isEmpty()).as("The list of roles shouldn't be empty").isFalse();
        Assertions.assertThat(roles.contains(r)).as("The list of roles should contain our role").isTrue();
    }

}
