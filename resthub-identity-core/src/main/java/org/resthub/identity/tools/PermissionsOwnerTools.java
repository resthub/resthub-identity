package org.resthub.identity.tools;

import java.util.ArrayList;
import java.util.List;

import org.resthub.identity.model.AbstractPermissionsOwner;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.Role;

/**
 * An Helper class to deals with permissions
 * 
 * */
public class PermissionsOwnerTools {
    /**
     * Allow to get all the permissions of the entity, coming from both direct
     * permissions or inherited permissions; each permission is reported once
     * even if it appears in different groups, roles or direct permssions
     * 
     * @param p
     *            the permissionOwner (User, Groups, ...) for which we are
     *            requesting permissions
     * 
     * @return the List of permissions
     * */
    public static List<Permission> getInheritedPermission(AbstractPermissionsOwner owner) {
        List<Permission> result = new ArrayList<Permission>();
        List<Permission> tmpPermissions = owner.getPermissions();
        if (tmpPermissions != null) {
            for (Permission permission : tmpPermissions) {
                if (!result.contains(permission)) {
                    result.add(permission);
                }
            }
        }
        for(Role userRole : owner.getRoles()) {
        	tmpPermissions = userRole.getPermissions();
            if (tmpPermissions != null) {
                for (Permission permission : tmpPermissions) {
                    if (!result.contains(permission)) {
                        result.add(permission);
                    }
                }
            }
        }
        
        List<Group> groups = owner.getGroups();
        if (groups != null) {
            for (Group group : groups) {
                if (group != null) {
                    tmpPermissions = getInheritedPermission(group);
                    if (tmpPermissions != null) {
                        for (Permission permission : tmpPermissions) {
                            if (!result.contains(permission)) {
                                result.add(permission);
                            }
                        }
                    }
                }
                for(Role groupRole : group.getRoles()) {
                	tmpPermissions = groupRole.getPermissions();
                    if (tmpPermissions != null) {
                        for (Permission permission : tmpPermissions) {
                            if (!result.contains(permission)) {
                                result.add(permission);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
