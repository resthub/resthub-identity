package org.resthub.identity.controller;


import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.test.AbstractWebTest;
import org.resthub.web.JsonHelper;
import org.resthub.web.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Test class for <tt>RoleController</tt>.
 * 
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public class RoleControllerWebTest extends AbstractWebTest {

    public RoleControllerWebTest() {
        super("resthub-web-server,resthub-jpa");
        this.useOpenEntityManagerInViewFilter = true;
    }
	
	// Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
       	this.request("api/user").delete();
       	this.request("api/group").delete();
        this.request("api/role").delete();
    }
    
	// Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
   	this.request("api/user").delete();
     	this.request("api/group").delete();
        this.request("api/role").delete();
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
        String userPassword = "UserTestPassword" + id;
        String userEmail = "test" + id + "@test.com";
        User u = new User();
        u.setLogin(userLogin);
        u.setPassword(userPassword);
        u.setLastName("Test");
        u.setFirstName("Test");
        u.setEmail(userEmail);
        return u;
    }

    @Test
    public void shouldGetUsersWithDirectRole() {
    	// Given some new roles
        Role r1 = new Role("role1");
        Role r2 = new Role("role2");
        
        Response response = this.request("api/role").jsonPost(r1);
        r1 = JsonHelper.deserialize(response.getBody(), Role.class); 
        
        response = this.request("api/role").jsonPost(r2);
        r2 = JsonHelper.deserialize(response.getBody(), Role.class);
        
        // Given some new users
        User u1 = this.createTestUser(1);
        User u2 = this.createTestUser(2);
        User u3 = this.createTestUser(3);
        User u4 = this.createTestUser(4);
        
        response = this.request("api/user").jsonPost(u1);
        u1 = JsonHelper.deserialize(response.getBody(), User.class);
        response = this.request("api/user").jsonPost(u2);
        u2 = JsonHelper.deserialize(response.getBody(), User.class);
        response = this.request("api/user").jsonPost(u3);
        u3 = JsonHelper.deserialize(response.getBody(), User.class);
        response = this.request("api/user").jsonPost(u4);
        u4 = JsonHelper.deserialize(response.getBody(), User.class);
        
        // Given the association of the users with the roles
        // u1 with role1
        this.request("api/user/name/" + u1.getLogin() + "/roles/" + r1.getName()).put(JsonHelper.serialize(u1));
        // u3 with role2
        this.request("api/user/name/" + u3.getLogin() + "/roles/" + r2.getName()).put(JsonHelper.serialize(u3));
        // u4 with both role1 and role2
        this.request("api/user/name/" + u4.getLogin() + "/roles/" + r1.getName()).put(JsonHelper.serialize(u4));
        this.request("api/user/name/" + u4.getLogin() + "/roles/" + r2.getName()).put(JsonHelper.serialize(u4));

        // When I look for users with roles
        String notExistingRoleUsers = this.request("api/role/inventedRole/users").get().getBody();
        String role1Users = this.request("api/role/role1/users").get().getBody();
        String role2Users = this.request("api/role/role2/users").get().getBody();
        
        // Then the lists should only contain what I asked for
        Assertions.assertThat(notExistingRoleUsers).as("A search with an unknown role shouldn't bring anything").isEqualTo("[ ]");

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
