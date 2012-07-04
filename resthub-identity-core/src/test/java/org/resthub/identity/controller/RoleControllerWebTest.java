package org.resthub.identity.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.test.common.AbstractWebTest;
import org.resthub.web.Client;
import org.resthub.web.Client.Response;
import org.resthub.web.JsonHelper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Test class for <tt>RoleController</tt>.
 * 
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public class RoleControllerWebTest extends AbstractWebTest {

	Client client = new Client();
	
	protected String rootUrl() {
        return "http://localhost:9797/api/";
    }
	
	// Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
        try {
        	client.url(rootUrl()+"user").delete().get();
        	client.url(rootUrl()+"group").delete().get();
            client.url(rootUrl()+"role").delete().get();
        } catch (InterruptedException | ExecutionException e) {
            Assertions.fail("Exception during delete all request", e);
        }
    }
    
	// Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
        try {
        	client.url(rootUrl()+"user").delete().get();
        	client.url(rootUrl()+"group").delete().get();
            client.url(rootUrl()+"role").delete().get();
        } catch (InterruptedException | ExecutionException e) {
            Assertions.fail("Exception during delete all request", e);
        }
    }

    /**
     * Generate a random role name based on a string and a randomized number.
     * 
     * @return A unique role name.
     */
    private String generateRandomRoleName() {
        return "RoleName" + Math.round(Math.random() * 10000000);
    }

    protected Role udpateTestResource(Role r) {
        r.setName(generateRandomRoleName());
        return r;
    }

    protected Role createTestResource() {
        Role testRole = new Role(generateRandomRoleName());
        return testRole;
    }

    protected User createTestUser(int id) {
        String userLogin = "UserTestUserLogin" + id;
        String userPassword = "UserTestUserPassword";
        User u = new User();
        u.setLogin(userLogin);
        u.setPassword(userPassword);
        return u;
    }

    @Test
    public void shouldGetUsersWithDirectRole() throws IOException, InterruptedException, ExecutionException {
    	// Given some new roles
        Role r1 = new Role("role1");
        Role r2 = new Role("role2");
        
        Response response = client.url(rootUrl()+"role").jsonPost(r1).get();
        r1 = JsonHelper.deserialize(response.getBody(), Role.class); 
        
        response = client.url(rootUrl()+"role").jsonPost(r2).get();
        r2 = JsonHelper.deserialize(response.getBody(), Role.class);
        
        // Given some new users
        User u1 = this.createTestUser(1);
        User u2 = this.createTestUser(2);
        User u3 = this.createTestUser(3);
        User u4 = this.createTestUser(4);
        
        response = client.url(rootUrl()+"user").jsonPost(u1).get();
        u1 = JsonHelper.deserialize(response.getBody(), User.class);
        response = client.url(rootUrl()+"user").jsonPost(u2).get();
        u2 = JsonHelper.deserialize(response.getBody(), User.class);
        response = client.url(rootUrl()+"user").jsonPost(u3).get();
        u3 = JsonHelper.deserialize(response.getBody(), User.class);
        response = client.url(rootUrl()+"user").jsonPost(u4).get();
        u4 = JsonHelper.deserialize(response.getBody(), User.class);
        
        // Given the association of the users with the roles
        // u1 with role1
        client.url(rootUrl()+"user/name/" + u1.getLogin() + "/roles/" + r1.getName()).put(JsonHelper.serialize(u1));
        // u3 with role2
        client.url(rootUrl()+"user/name/" + u3.getLogin() + "/roles/" + r2.getName()).put(JsonHelper.serialize(u3));
        // u4 with both role1 and role2
        client.url(rootUrl()+"user/name/" + u4.getLogin() + "/roles/" + r1.getName()).put(JsonHelper.serialize(u4));
        client.url(rootUrl()+"user/name/" + u4.getLogin() + "/roles/" + r2.getName()).put(JsonHelper.serialize(u4));

        // When I look for users with roles
        String notExistingRoleUsers = client.url(rootUrl()+"role/inventedRole/users").get().get().getBody();
        String role1Users = client.url(rootUrl()+"role/role1/users").get().get().getBody();
        String role2Users = client.url(rootUrl()+"role/role2/users").get().get().getBody();
        
        // Then the lists should only contain what I asked for
        Assertions.assertThat(notExistingRoleUsers).as("A search with an unknown role shouldn't bring anything").isEqualTo("[]");

        Assertions.assertThat(role1Users.contains(u1.getLogin())).as("The list of users with role1 should contain user1").isTrue();
        Assertions.assertThat(role1Users.contains(u2.getLogin())).as("The list of users with role1 shouldn't contain user2").isFalse();
        Assertions.assertThat(role1Users.contains(u3.getLogin())).as("The list of users with role1 shouldn't contain user3").isFalse();
        Assertions.assertThat(role1Users.contains(u4.getLogin())).as("The list of users with role1 should contain user4").isTrue();

        Assertions.assertThat(role2Users.contains(u1.getLogin())).as("The list of users with role2 shouldn't contain user1").isFalse();
        Assertions.assertThat(role2Users.contains(u2.getLogin())).as("The list of users with role2 shouldn't contain user2").isFalse();
        Assertions.assertThat(role2Users.contains(u3.getLogin())).as("The list of users with role2 should contain user3").isTrue();
        Assertions.assertThat(role2Users.contains(u4.getLogin())).as("The list of users with role2 should contain user4").isTrue();
    }
}
