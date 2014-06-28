package org.resthub.identity.webapp.initializer;

import org.resthub.common.util.PostInitialize;
import org.resthub.identity.core.security.IdentityRoles;
import org.resthub.identity.core.service.defaults.DefaultPermissionService;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.User;
import org.resthub.identity.webapp.service.impl.MySuperUserService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Bastien on 28/06/14.
 */
@Named("dataInitializer")
public class DataInitializer {

    @Inject
    @Named("userService")
    private MySuperUserService userService;

    @Inject
    @Named("permissionService")
    private DefaultPermissionService permissionService;

    @PostInitialize
    public void postInit() {
        User demoUser = new User();
        demoUser.setLogin("demo");
        demoUser.setPassword("demo");
        demoUser.setEmail("demo@resthub.org");
        demoUser.setFirstName("Demo");
        demoUser.setLastName("Demo");

        User adminUser = new User();
        adminUser.setLogin("admin");
        adminUser.setPassword("admin");
        adminUser.setEmail("admin@resthub.org");
        adminUser.setFirstName("Admin");
        adminUser.setLastName("Admin");


        Permission permCREATE_USER = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.USER));
        Permission permREAD_USER = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.USER));
        Permission permUPDATE_USER = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.USER));
        Permission permDELETE_USER = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.USER));
        Permission permCREATE_GROUP = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.GROUP));
        Permission permREAD_GROUP = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP));
        Permission permUPDATE_GROUP = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.GROUP));
        Permission permDELETE_GROUP = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP));
        Permission permCREATE_ROLE = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.ROLE));
        Permission permREAD_ROLE = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.ROLE));
        Permission permUPDATE_ROLE = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.ROLE));
        Permission permDELETE_ROLE = permissionService.create(new Permission(IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.ROLE));

        demoUser.getPermissions().add(permREAD_GROUP);
        demoUser.getPermissions().add(permREAD_ROLE);
        demoUser.getPermissions().add(permREAD_USER);

        adminUser.getPermissions().add(permCREATE_USER);
        adminUser.getPermissions().add(permCREATE_ROLE);
        adminUser.getPermissions().add(permCREATE_GROUP);
        adminUser.getPermissions().add(permREAD_USER);
        adminUser.getPermissions().add(permREAD_ROLE);
        adminUser.getPermissions().add(permREAD_GROUP);
        adminUser.getPermissions().add(permUPDATE_USER);
        adminUser.getPermissions().add(permUPDATE_ROLE);
        adminUser.getPermissions().add(permUPDATE_GROUP);
        adminUser.getPermissions().add(permDELETE_USER);
        adminUser.getPermissions().add(permDELETE_ROLE);
        adminUser.getPermissions().add(permDELETE_GROUP);

        userService.create(demoUser);
        userService.create(adminUser);
    }
}
