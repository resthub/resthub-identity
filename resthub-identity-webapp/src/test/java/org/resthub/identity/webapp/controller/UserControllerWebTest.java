package org.resthub.identity.webapp.controller;


import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.identity.model.UserWithPassword;
import org.resthub.test.AbstractWebTest;
import org.resthub.web.Http;
import org.resthub.web.JsonHelper;
import org.resthub.web.Response;
import org.resthub.web.exception.ConflictClientException;
import org.resthub.web.exception.NotFoundClientException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserControllerWebTest extends AbstractWebTest {

    public UserControllerWebTest() {
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
    public void tearDown() {
      	this.request("api/user").delete();
       	this.request("api/group").delete();
        this.request("api/role").delete();
    }

    private String generateRandomLogin() {
        return "Login" + Math.round(Math.random() * 1000000);
    }

    protected User createTestResource() {
        String userLogin = generateRandomLogin();
        String userPassword = "P@ssw0rd";
        User u = new User();
        u.setLogin(userLogin);
        u.setPassword(userPassword);
        u.setFirstName("Test");
        u.setLastName("Test");
        u.setEmail(userLogin+"@test.com");
        return u;
    }

    protected User udpateTestResource(User u) {
        u.setLogin(generateRandomLogin());
        return u;
    }

    @Test
    public void shouldAddRoleToUser() {
        // Given a new role
        Role r = new Role("Role" + Math.round(Math.random() * 100000));
        Response response = this.request("api/role").jsonPost(r);
        r = JsonHelper.deserialize(response.getBody(), Role.class);

        // Given a new user
        User u = this.createTestResource();
        response = this.request("api/user").jsonPost(u);
        u = JsonHelper.deserialize(response.getBody(), User.class);

        // When I associate the user and the role
        response = this.request("api/user/name/" + u.getLogin() + "/roles/" + r.getName()).jsonPut(u);

        // Then I get the user with this role
        String userWithRole = this.request("api/user/name/" + u.getLogin()+"/roles").get().getBody();
        Assertions.assertThat(userWithRole.contains(r.getName())).as("The user should contain the role").isTrue();
    }

    @Test
    public void shouldRemoveRoleFromUser() {
        // Given a new role
    	Role r = new Role("Role" + Math.round(Math.random() * 100000));
        Response response = this.request("api/role").jsonPost(r);
        r = JsonHelper.deserialize(response.getBody(), Role.class);

        // Given a new user
        User u = this.createTestResource();
        response = this.request("api/user").jsonPost(u);
        u = JsonHelper.deserialize(response.getBody(), User.class);
        
        this.request("api/user/name/" + u.getLogin() + "/roles/" + r.getName()).jsonPut(u);
        this.request("api/user/name/" + u.getLogin() + "/roles/" + r.getName()).delete();

        // Then I get the user with this role
        String userWithRole = this.request("api/user/login/" + u.getLogin()).get().getBody();
        Assertions.assertThat(userWithRole.contains(r.getName())).as("The user shouldn't contain the role").isFalse();
    }

    @Test
    public void shouldGetRolesFromUsers() {
        // Given some new roles
        Role r1 = new Role("role1");
        Role r2 = new Role("role2");
        Response response = this.request("api/role").jsonPost(r1);
        r1 = JsonHelper.deserialize(response.getBody(), Role.class);
        response = this.request("api/role").jsonPost(r2);
        r2 = JsonHelper.deserialize(response.getBody(), Role.class);
        
        // Given some new users
        User u1 = this.createTestResource();
        User u2 = this.createTestResource();
        User u3 = this.createTestResource();
        User u4 = this.createTestResource();
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
        this.request("api/user/name/" + u1.getLogin() + "/roles/" + r1.getName()).jsonPut(u1);
        // u3 with role2
        this.request("api/user/name/" + u3.getLogin() + "/roles/" + r2.getName()).jsonPut(u3);
        // u4 with both role1 and role2
        this.request("api/user/name/" + u4.getLogin() + "/roles/" + r1.getName()).jsonPut(u4);
        this.request("api/user/name/" + u4.getLogin() + "/roles/" + r2.getName()).jsonPut(u4);

        // When I look for users with roles
        String user1Roles = this.request("api/user/name/" + u1.getLogin() + "/roles").get().getBody();
        String user2Roles = this.request("api/user/name/" + u2.getLogin() + "/roles").get().getBody();
        String user3Roles = this.request("api/user/name/" + u3.getLogin() + "/roles").get().getBody();
        String user4Roles = this.request("api/user/name/" + u4.getLogin() + "/roles").get().getBody();

        // Then the lists should only contain what I asked for
        Assertions.assertThat(user1Roles.contains(r1.getName())).as("The list of roles for user1 should contain role1").isTrue();
        Assertions.assertThat(user2Roles).as("The list of roles for user2 should be empty").isEqualTo("[ ]");
        Assertions.assertThat(user3Roles.contains(r2.getName())).as("The list of roles for user3 should contain role2").isTrue();
        Assertions.assertThat(user4Roles.contains(r1.getName()) && user4Roles.contains(r2.getName())).as("The list of roles for user4 should contain role1 and role2").isTrue();
    }

    @Test(expectedExceptions=ConflictClientException.class)
    public void cannotCreateTwiceTheSameUser() {
        // Given a new user
        User u = this.createTestResource();

        // When I create it twice
        this.request("api/user").jsonPost(u);
        this.request("api/user").jsonPost(u);
    }

    @Test(expectedExceptions=NotFoundClientException.class)
    public void shouldManageToCheckUserIdentity() {
        // Given a created user
    	UserWithPassword u = new UserWithPassword(this.createTestResource());
        String password = u.getPassword();
        Response response = this.request("api/user").jsonPost(u);
        User user = JsonHelper.deserialize(response.getBody(), User.class);

        // When I check his identity
        Response postAnswerCorrectPass = this.request("api/user/checkuser").setQueryParameter("user", user.getLogin()).setQueryParameter("password", password).post();
        Assertions.assertThat(postAnswerCorrectPass.getStatus()).as("The identity check should be successful").isEqualTo(Http.NO_CONTENT);
        this.request("api/user/checkuser").setQueryParameter("user", user.getLogin()).setQueryParameter("password", "wrongpassword").post();

    }
}
