package org.resthub.identity.core.repository.impl;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.core.repository.AbstractPermissionRepository;
import org.resthub.identity.model.Permission;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;

@ActiveProfiles("resthub-jpa")
public class DefaultPermissionRepositoryTest extends AbstractTransactionalTest {

    private static final String PERMISSION_CODE = "TestPermission";
    private static final String NEW_PERMISSION_CODE = "NewPermission";

    @Inject
    @Named("permissionRepository")
    private AbstractPermissionRepository<Permission, Long> permissionRepository;

    // Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
        permissionRepository.deleteAll();
    }

    // Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
        permissionRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        Permission permission = new Permission();
        permission.setCode(PERMISSION_CODE);
        permission = permissionRepository.save(permission);
        Assertions.assertThat(permission).as("Permission not created !").isNotNull();
    }

    @Test
    public void testUpdate() {
        // Create category
        Permission permission = new Permission();
        permission.setCode(PERMISSION_CODE);
        permission = permissionRepository.save(permission);
        Assertions.assertThat(permission).as("Permission not created !").isNotNull();
        // Modify the created category
        Permission permission1 = permissionRepository.findOne(permission.getId());
        permission1.setCode(NEW_PERMISSION_CODE);
        permissionRepository.save(permission1);
        // Récupérer le groupe pour vérifier s'il a bien été modifié
        Permission permission2 = permissionRepository.findOne(permission.getId());
        Assertions.assertThat(permission2.getCode()).as("Permission not updated!").isEqualTo(NEW_PERMISSION_CODE);
    }

}
