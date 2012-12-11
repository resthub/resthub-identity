package org.resthub.identity.controller;


import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.test.AbstractWebTest;
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
	
    public GroupControllerWebTest() {
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

    @Test
    public void testShouldGetUsersFromGroup() {
    	
    	/* Given a new group */
        String groupName = "testGroup";
        Group g = new Group();
        g.setName(groupName);
        
        this.request("api/group").jsonPost(g);
        
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
        Response response = this.request("api/user").jsonPost(u);
        u = JsonHelper.deserialize(response.getBody(), User.class);
        
        /* Given a link between this user and the group */
        this.request("api/user/name/" + u.getLogin() + "/groups/" + g.getName()).put("");
        
        /* When I get the users of the group */
        String usersFromGroup = this.request("api/group/name/" + g.getName() + "/users").get().getBody(); 

        /* Then the list of users contains our user */
        Assertions.assertThat(usersFromGroup.contains(u.getLogin())).as("The list of users should contain our just added user").isTrue();
        
    }
    
    @Test
    public void deleteGroupWithRole() {
    	
    	/* Given a new group */
        String groupName = "testGroup";
        Group g = new Group();
        g.setName(groupName);
        
        Response response = this.request("api/group").jsonPost(g);
        g = JsonHelper.deserialize(response.getBody(), Group.class);
        
        /* Given a new user */
        String roleName = "roleTest";

        Role r = new Role(roleName);
        response = this.request("api/role").jsonPost(r);
        r = JsonHelper.deserialize(response.getBody(), Role.class);
        
        /* Given a link between this user and the group */
        this.request("api/group/name/" + groupName + "/roles/" + r.getName()).put("");
        
        /* When I get the users of the group */
        this.request("api/group/" + g.getId()).delete();

        
    }
}
