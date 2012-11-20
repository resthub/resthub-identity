package org.resthub.identity.model;

import org.fest.assertions.api.Assertions;
import org.testng.annotations.Test;

/**
 * Test class to test behavior of the identity model behavior This is
 * essentially to test permissions management
 * */
public class IdentityTest {

    private class PermissionsOwner extends AbstractPermissionsOwner {
    }

    @Test
    public void testIdentityCreation() {
        // Given a new Identity
        AbstractPermissionsOwner i = new PermissionsOwner();
        // the object should exist and the permissions list being null
        Assertions.assertThat(i).isNotNull().as("The new Identity is null");
    }

    @Test
    public void testPermissionSettlement() {
        // Given a new Identity
        AbstractPermissionsOwner i = new PermissionsOwner();
        String p1 = "Permission1";

        // Once we set a permissions list
        i.getPermissions().add(p1);

        // The permission list defined should be the one given
        Assertions.assertThat(i.getPermissions().get(0)).isEqualTo(p1).as("the permissions list is not properly setted");
    }

    @Test
    public void testRoleSettlement() {
        AbstractPermissionsOwner owner = new PermissionsOwner();

        Assertions.assertThat(owner.getRoles()).isNotNull().as("Role list shouldn't be null");
        Assertions.assertThat(owner.getRoles().size()).isEqualTo(0).as("Role list should be empty");
        
        final String testRoleName = "testRole";
        Role testRole = new Role(testRoleName);
        Assertions.assertThat(testRole.getName()).isEqualTo(testRoleName).as("Name of the role should be the one set in constructor");
        Assertions.assertThat(testRole.getPermissions()).isNotNull().as("Permissions of the role shouldn't be null");
        Assertions.assertThat(testRole.getPermissions().size()).isEqualTo(0).as("Newly created role shouldn't have permissions");

        final String testPermissionName = "testPerm";
        testRole.getPermissions().add(testPermissionName);

        Assertions.assertThat(testRole.getPermissions().contains(testPermissionName)).isTrue().as("Permissions of the role should contain the newly added permission");

        owner.getRoles().add(testRole);
        Assertions.assertThat(owner.getRoles().contains(testRole)).isTrue().as("Permission owner should contain the newly created role");

    }
}
