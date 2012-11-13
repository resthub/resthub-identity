package org.resthub.identity.controller;


import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.AbstractPermissionsOwner;
import org.resthub.identity.model.User;
import org.resthub.test.AbstractWebTest;
import org.resthub.web.Http;
import org.resthub.web.JsonHelper;
import org.resthub.web.Response;
import org.resthub.web.exception.BadRequestClientException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchControllerWebTest extends AbstractWebTest {
	        
    /*public SearchControllerWebTest() {
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
    
    @Test
    public void shouldIndexesBeReseted() {
    	Response response = this.request("api/search").put("");
        // Then the operation is processed
    	Assertions.assertThat(response.getStatus()).isEqualTo(Http.NO_CONTENT);
    }

    @Test(expectedExceptions=BadRequestClientException.class)
    public void shouldNullQueryFailed() {
        // When searching without parameter
    	this.request("api/search").get();
    }

    @Test(expectedExceptions=BadRequestClientException.class)
    public void shouldEmptyQueryFailed() {
        // When searching with empty query
    	this.request("api/search").setQueryParameter("query", "").get();
    }

    @Test
    public void shouldUnmatchingQueryReturnsEmptyResults() {
        // When searching with an unmatching query
    	Response response = this.request("api/search").setQueryParameter("query", "toto").get();
    	AbstractPermissionsOwner[] results = JsonHelper.deserialize(response.getBody(), AbstractPermissionsOwner[].class); 

    	// Then the result is empty.
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.length).isEqualTo(0);
    }

    @Test
    public void shouldQueryReturnsUsers() {
        // Given a user with jdujardin as login
        User user = new User();
        user.setLogin("jdujardin");
        user.setPassword("pwd");
        
        Response response = this.request("api/user").jsonPost(user);
        user = JsonHelper.deserialize(response.getBody(), User.class); 

        // Given a user with jean as name
        User user2 = new User();
        user2.setLogin("user2");
        user2.setLastName("jean");
        user2.setPassword("pwd");
        response = this.request("api/user").jsonPost(user2);
        user2 = JsonHelper.deserialize(response.getBody(), User.class);

        // When searching the created user
        response = this.request("api/search").setQueryParameter("query", "j").get();
        User[] results = JsonHelper.deserialize(response.getBody(), User[].class);
        // Then the result contains the user.
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.length).isEqualTo(2);
        Assertions.assertThat(results[0]).isEqualTo(user);
        Assertions.assertThat(results[1]).isEqualTo(user2);

        // Cleanup after work
        this.request("api/user/"+user.getId()).delete();
        this.request("api/user/"+user2.getId()).delete();
    }

    @Test
    public void shouldQueryReturnsUsersInJson() {
        // Given a user with jdujardin as login
        User user = new User();
        user.setLogin("jdujardin");
        user.setPassword("pwd");
        Response response = this.request("api/user").jsonPost(user);
        user = JsonHelper.deserialize(response.getBody(), User.class); 

        // Given a user with jean as name
        User user2 = new User();
        user2.setLogin("user2");
        user2.setLastName("jean");
        user2.setPassword("pwd");
        response = this.request("api/user").jsonPost(user2);
        user2 = JsonHelper.deserialize(response.getBody(), User.class);

        response = this.request("api/search").setQueryParameter("query", "j").get();
        User[] results = JsonHelper.deserialize(response.getBody(), User[].class);

        // Then the result contains the user.
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.length).isEqualTo(2);
        Assertions.assertThat(results[0]).isEqualTo(user);
        Assertions.assertThat(results[1]).isEqualTo(user2);

        // Cleanup after work
        this.request("api/user/"+user.getId()).delete();
        this.request("api/user/"+user2.getId()).delete();
    }

    @Test
    public void shouldQueryWithoutUsersNotReturnsUsers() {

        // Given a user
        User user = new User();
        user.setLogin("jdujardin");
        user.setPassword("pwd");
        Response response = this.request("api/user").jsonPost(user);
        user = JsonHelper.deserialize(response.getBody(), User.class);

        // When searching the created user without users
        response = this.request("api/search").setQueryParameter("query", "j").setQueryParameter("users", "false").get();
        User[] results = JsonHelper.deserialize(response.getBody(), User[].class);
        // Then the result does not contains the user.
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.length).isEqualTo(0);

        // Cleanup after work
        this.request("api/user/"+user.getId()).delete();
    }*/
}
