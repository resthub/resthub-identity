package org.resthub.identity.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.User;
import org.resthub.test.common.AbstractWebTest;
import org.resthub.web.Client;
import org.resthub.web.JsonHelper;
import org.resthub.web.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import com.ning.http.client.Response;

/**
 * 
 * @author Guillaume Zurbach
 */
public class GroupControllerWebTest extends AbstractWebTest {
	
    Client client = new Client();

    public GroupControllerWebTest() {
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
    public void cleanAfter() {
        client.url(rootUrl()+"user").delete();
      	client.url(rootUrl()+"group").delete();
        client.url(rootUrl()+"role").delete();
    }
	
    private String generateRandomGroupName() {
        return "GroupName" + Math.round(Math.random() * 10000000);
    }

    protected Group createTestResource() {
        String groupName = this.generateRandomGroupName();
        Group g = new Group();
        g.setName(groupName);
        return g;
    }

    protected Group udpateTestResource(Group r) {
        r.setName(this.generateRandomGroupName());
        return r;
    }

    protected Long getResourceId(Group resource) {
        return resource.getId();
    }

    @Test
    public void testShouldGetUsersFromGroup() throws IllegalArgumentException, InterruptedException, ExecutionException, IOException {
    	
    	/* Given a new group */
        String groupName = "testGroup";
        Group g = new Group();
        g.setName(groupName);
        
        Response response = client.url(rootUrl()+"group").jsonPost(g);
        
        /* Given a new user */
        String firstName = "first";
        String lastName = "last";
        String password = "pass";
        String login = "testLogin";

        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setPassword(password);
        u.setLogin(login);
        response = client.url(rootUrl()+"user").jsonPost(u);
        u = JsonHelper.deserialize(response.getBody(), User.class);
        System.out.println("Id user : "+u.getId());
        
        /* Given a link between this user and the group */
        client.url(rootUrl()+"user/name/" + u.getLogin() + "/groups/" + g.getName()).put("");
        
        /* When I get the users of the group */
        String usersFromGroup = client.url(rootUrl()+"group/name/" + g.getName() + "/users").get().getBody(); 

        /* Then the list of users contains our user */
        Assertions.assertThat(usersFromGroup.contains(u.getLogin())).as("The list of users should contain our just added user").isTrue();

        /* Cleanup */
//        client.url(rootUrl()+"group/"+g.getId()).delete();
//        client.url(rootUrl()+"user/name"+u.getLogin() + "/groups/" + g.getName()).delete();
//        client.url(rootUrl()+"user/"+u.getId()).delete();
        
    }
}
