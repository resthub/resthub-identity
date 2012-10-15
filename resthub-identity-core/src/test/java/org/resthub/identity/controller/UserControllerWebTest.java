package org.resthub.identity.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.identity.model.UserWithPassword;
import org.resthub.test.common.AbstractWebTest;
import org.resthub.web.Client;
import org.resthub.web.Http;
import org.resthub.web.JsonHelper;
import org.resthub.web.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * 
 * @author Guillaume Zurbach
 */
public class UserControllerWebTest extends AbstractWebTest {

	Client client = new Client();
        
        public UserControllerWebTest() {
        this.activeProfiles = "resthub-web-server,resthub-jpa";
    }
	
	protected String rootUrl() {
        return "http://localhost:9797/api/";
    }
	
	// Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
       	client.url(rootUrl()+"user").delete();
       	client.url(rootUrl()+"group").delete();
        client.url(rootUrl()+"role").delete();
    }
    
	// Cleanup after each test
    @AfterMethod
    public void tearDown() {
      	client.url(rootUrl()+"user").delete();
       	client.url(rootUrl()+"group").delete();
        client.url(rootUrl()+"role").delete();
    }

    private String generateRandomLogin() {
        return "Login" + Math.round(Math.random() * 1000000);
    }

    protected User createTestResource() {
        String userLogin = generateRandomLogin();
        String userPassword = "UserTestUserPassword";
        User u = new User();
        u.setLogin(userLogin);
        u.setPassword(userPassword);
        return u;
    }

    protected User udpateTestResource(User u) {
        u.setLogin(generateRandomLogin());
        return u;
    }

    @Test
    public void shouldAddRoleToUser() throws Exception {
        // Given a new role
        Role r = new Role("Role" + Math.round(Math.random() * 100000));
        Response response = client.url(rootUrl()+"role").jsonPost(r);
        r = JsonHelper.deserialize(response.getBody(), Role.class);

        // Given a new user
        User u = this.createTestResource();
        response = client.url(rootUrl()+"user").jsonPost(u);
        u = JsonHelper.deserialize(response.getBody(), User.class);

        // When I associate the user and the role
        response = client.url(rootUrl()+"user/name/" + u.getLogin() + "/roles/" + r.getName()).jsonPut(u);

        // Then I get the user with this role
        String userWithRole = client.url(rootUrl()+"user/name/" + u.getLogin()+"/roles").get().getBody();
        Assertions.assertThat(userWithRole.contains(r.getName())).as("The user should contain the role").isTrue();
    }

    @Test
    public void shouldRemoveRoleFromUser() throws Exception {
        // Given a new role
    	Role r = new Role("Role" + Math.round(Math.random() * 100000));
        Response response = client.url(rootUrl()+"role").jsonPost(r);
        r = JsonHelper.deserialize(response.getBody(), Role.class);

        // Given a new user
        User u = this.createTestResource();
        response = client.url(rootUrl()+"user").jsonPost(u);
        u = JsonHelper.deserialize(response.getBody(), User.class);
        
        client.url(rootUrl()+"user/name/" + u.getLogin() + "/roles/" + r.getName()).jsonPut(u);
        client.url(rootUrl()+"user/name/" + u.getLogin() + "/roles/" + r.getName()).delete();

        // Then I get the user with this role
        String userWithRole = client.url(rootUrl()+"user/login/" + u.getLogin()).get().getBody();
        Assertions.assertThat(userWithRole.contains(r.getName())).as("The user shouldn't contain the role").isFalse();
    }

    @Test
    public void shouldGetRolesFromUsers() throws Exception {
        // Given some new roles
        Role r1 = new Role("role1");
        Role r2 = new Role("role2");
        Response response = client.url(rootUrl()+"role").jsonPost(r1);
        r1 = JsonHelper.deserialize(response.getBody(), Role.class);
        response = client.url(rootUrl()+"role").jsonPost(r2);
        r2 = JsonHelper.deserialize(response.getBody(), Role.class);
        
        // Given some new users
        User u1 = this.createTestResource();
        User u2 = this.createTestResource();
        User u3 = this.createTestResource();
        User u4 = this.createTestResource();
        response = client.url(rootUrl()+"user").jsonPost(u1);
        u1 = JsonHelper.deserialize(response.getBody(), User.class);
        response = client.url(rootUrl()+"user").jsonPost(u2);
        u2 = JsonHelper.deserialize(response.getBody(), User.class);
        response = client.url(rootUrl()+"user").jsonPost(u3);
        u3 = JsonHelper.deserialize(response.getBody(), User.class);
        response = client.url(rootUrl()+"user").jsonPost(u4);
        u4 = JsonHelper.deserialize(response.getBody(), User.class);
        
        // Given the association of the users with the roles
        // u1 with role1
        client.url(rootUrl()+"user/name/" + u1.getLogin() + "/roles/" + r1.getName()).jsonPut(u1);
        // u3 with role2
        client.url(rootUrl()+"user/name/" + u3.getLogin() + "/roles/" + r2.getName()).jsonPut(u3);
        // u4 with both role1 and role2
        client.url(rootUrl()+"user/name/" + u4.getLogin() + "/roles/" + r1.getName()).jsonPut(u4);
        client.url(rootUrl()+"user/name/" + u4.getLogin() + "/roles/" + r2.getName()).jsonPut(u4);

        // When I look for users with roles
        String user1Roles = client.url(rootUrl()+"user/name/" + u1.getLogin() + "/roles").get().getBody();
        String user2Roles = client.url(rootUrl()+"user/name/" + u2.getLogin() + "/roles").get().getBody();
        String user3Roles = client.url(rootUrl()+"user/name/" + u3.getLogin() + "/roles").get().getBody();
        String user4Roles = client.url(rootUrl()+"user/name/" + u4.getLogin() + "/roles").get().getBody();

        // Then the lists should only contain what I asked for
        Assertions.assertThat(user1Roles.contains(r1.getName())).as("The list of roles for user1 should contain role1").isTrue();
        Assertions.assertThat(user2Roles).as("The list of roles for user2 should be empty").isEqualTo("[]");
        Assertions.assertThat(user3Roles.contains(r2.getName())).as("The list of roles for user3 should contain role2").isTrue();
        Assertions.assertThat(user4Roles.contains(r1.getName()) && user4Roles.contains(r2.getName())).as("The list of roles for user4 should contain role1 and role2").isTrue();
    }

    @Test
    public void cannotCreateTwiceTheSameUser() throws Exception {
        // Given a new user
        User u = this.createTestResource();

        // When I create it twice
        client.url(rootUrl()+"user").jsonPost(u);
        Response response = client.url(rootUrl()+"user").jsonPost(u);

        // Then the response should be a 409 error
        Assertions.assertThat(response.getStatus()).isEqualTo(Http.CONFLICT);
        
    }

    @Test
    public void shouldManageToCheckUserIdentity() throws IllegalArgumentException, InterruptedException, ExecutionException, IOException {
        // Given a created user
    	UserWithPassword u = new UserWithPassword(this.createTestResource());
        String password = u.getPassword();
        Response response = client.url(rootUrl()+"user").jsonPost(u);
        User user = JsonHelper.deserialize(response.getBody(), User.class);

        // When I check his identity
        Response postAnswerCorrectPass = client.url(rootUrl()+"user/checkuser").setQueryParameter("user", user.getLogin()).setQueryParameter("password", password).post();
        Response postAnswerWrongPass = client.url(rootUrl()+"user/checkuser").setQueryParameter("user", user.getLogin()).setQueryParameter("password", "wrongpassword").post();

        Assertions.assertThat(postAnswerCorrectPass.getStatus()).as("The identity check should be successful").isEqualTo(Http.NO_CONTENT);
        Assertions.assertThat(postAnswerWrongPass.getStatus()).as("The identity check should fail").isEqualTo(Http.NOT_FOUND);
        
        response = client.url(rootUrl()+"user").get();
    }
}
