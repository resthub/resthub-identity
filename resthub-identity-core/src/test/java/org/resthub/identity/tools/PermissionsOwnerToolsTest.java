package org.resthub.identity.tools;

import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.User;
import org.testng.annotations.Test;

public class PermissionsOwnerToolsTest {

    @Test
    public void testWithoutPermissions() {
        // Given a new user without permissions nor Groups
        User u = new User();
        List<String> lp = u.getPermissions();
        if (lp == null) {
            lp = new ArrayList<String>();
        }
        // When we retrieve InheritedPermission
        lp = PermissionsOwnerTools.getInheritedPermission(u);

        // There is no permissions
        Assertions.assertThat(lp.size()== 0).isTrue();
    }

    @Test
    public void testWithPermissions() {
        // Given a new user without permissions nor Groups
        String p1 = "permission1";
        String p2 = "permission2";

        User u = new User();
        List<String> lp = u.getPermissions();
        if (lp == null) {
            lp = new ArrayList<String>();
        }
        lp.add(p1);
        lp.add(p2);

        // When we retrieve InheritedPermission
        lp = PermissionsOwnerTools.getInheritedPermission(u);

        // There is the 2 permissions
        Assertions.assertThat(lp.size() == 2).isTrue();
        Assertions.assertThat(lp.contains(p1)).isTrue();
        Assertions.assertThat(lp.contains(p2)).isTrue();

    }

    @Test
    public void testWithPermissionsFromGroup() {
        // Given a new user without permissions nor Groups
        String p1 = "permission1";
        String p2 = "permission2";

        User u = new User();
        Group g = new Group();
        u.getGroups().add(g);

        List<String> lp = u.getPermissions();
        lp.add(p1);
        lp = g.getPermissions();
        lp.add(p2);

        // When we retrieve InheritedPermission
        lp = PermissionsOwnerTools.getInheritedPermission(u);

        // There is no permissions
        Assertions.assertThat(lp.size() == 2).isTrue();
        Assertions.assertThat(lp.contains(p1)).isTrue();
        Assertions.assertThat(lp.contains(p2)).isTrue();
    }

    @Test
    public void testWithPermissionsFromGroupFromGroup() {
        // Given a new user without permissions nor Groups
        String p1 = "permission1";
        String p2 = "permission2";
        String p3 = "permission3";

        User u = new User();
        Group g1 = new Group();
        u.getGroups().add(g1);
        Group g2 = new Group();
        g1.getGroups().add(g2);

        List<String> lp = u.getPermissions();
        lp.add(p1);

        lp = g1.getPermissions();
        lp.add(p2);

        lp = g2.getPermissions();
        lp.add(p3);

        // When we retrieve InheritedPermission
        lp = PermissionsOwnerTools.getInheritedPermission(u);

        // There is no permissions
        Assertions.assertThat(lp.size() == 3).isTrue();
        Assertions.assertThat(lp.contains(p1)).isTrue();
        Assertions.assertThat(lp.contains(p2)).isTrue();
        Assertions.assertThat(lp.contains(p3)).isTrue();
    }

}
