package org.resthub.identity.core.service.impl;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Permission;
import org.resthub.identity.core.service.PermissionService;
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
public class DefaultPermissionServiceTest extends AbstractTransactionalTest {

    @Inject
    @Named("permissionService")
    private PermissionService permissionService;

    // Cleanup before each test
    @BeforeMethod
    public void cleanBefore() {
        this.permissionService.deleteAllWithCascade();
    }

    // Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
        this.permissionService.deleteAllWithCascade();
    }

    @Test
    public void testFindAll() {
        this.permissionService.create(new Permission("TEST_PERMISSION_1"));
        this.permissionService.create(new Permission("TEST_PERMISSION_2"));
        this.permissionService.create(new Permission("TEST_PERMISSION_3"));

        List<Permission> permissions = (List<Permission>) this.permissionService.findAll();

        Assertions.assertThat(permissions.size()).as("Permissions not found").isEqualTo(3);
    }

    @Test
    public void testFind() {
        this.permissionService.create(new Permission("TEST_PERMISSION_1"));
        this.permissionService.create(new Permission("TEST_PERMISSION_2"));
        this.permissionService.create(new Permission("TEST_PERMISSION_3"));

        Permission p = this.permissionService.findByCode("TEST_PERMISSION_2");

        Assertions.assertThat(p).as("Permission not found").isNotNull();
        Assertions.assertThat(p.getCode()).as("Permission not found").isEqualTo("TEST_PERMISSION_2");
    }

    @Test
    public void testChildPermission() {
        Permission permissionWithChildren = new Permission("TEST_PERMISSION_WITH_CHILDREN");
        permissionWithChildren.addPermission(new Permission("CHILD_PERMISSION_1"));
        permissionWithChildren.addPermission(new Permission("CHILD_PERMISSION_2"));
        permissionWithChildren.addPermission(new Permission("CHILD_PERMISSION_3"));
        Permission childPermission4 = new Permission("CHILD_PERMISSION_4");
        childPermission4.addPermission(new Permission("CHILD_PERMISSION_LEVEL_2"));
        permissionWithChildren.addPermission(childPermission4);
        this.permissionService.create(permissionWithChildren);

        Permission p = this.permissionService.findByCode("TEST_PERMISSION_WITH_CHILDREN");

        Assertions.assertThat(p).as("Permission not found").isNotNull();
        Assertions.assertThat(p.getCode()).as("Permission not found").isEqualTo("TEST_PERMISSION_WITH_CHILDREN");
        Assertions.assertThat(p.getPermissions().size()).isEqualTo(4);
        Assertions.assertThat(p.getPermissions().get(0).getPermissions().size()).isEqualTo(0);
        Assertions.assertThat(p.getPermissions().get(3).getPermissions().size()).isEqualTo(1);
    }

}
