package org.resthub.identity.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.model.AbstractPermissionsOwner;
import org.resthub.identity.model.User;
import org.resthub.test.common.AbstractWebTest;
import org.resthub.web.Client;
import org.resthub.web.Client.Response;
import org.resthub.web.Http;
import org.resthub.web.JsonHelper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 */
public class SearchControllerWebTest extends AbstractWebTest {
	
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
    public void tearDown() {
        try {
        	client.url(rootUrl()+"user").delete().get();
        	client.url(rootUrl()+"group").delete().get();
            client.url(rootUrl()+"role").delete().get();
        } catch (InterruptedException | ExecutionException e) {
            Assertions.fail("Exception during delete all request", e);
        }
    }
    
    @Test
    public void shouldIndexesBeReseted() throws InterruptedException, ExecutionException, IOException {
    	Response response = client.url(rootUrl()+"search").put("").get();
        // Then the operation is processed
    	Assertions.assertThat(response.getStatus()).isEqualTo(Http.NO_CONTENT);
    }

    @Test
    public void shouldNullQueryFailed() throws InterruptedException, ExecutionException, IOException {
        // When searching without parameter
    	Response response = client.url(rootUrl()+"search").get().get();
        // Then the result is an error.
    	Assertions.assertThat(response.getStatus()).isEqualTo(Http.BAD_REQUEST);
    }

    @Test
    public void shouldEmptyQueryFailed() throws InterruptedException, ExecutionException, IOException {
        // When searching with empty query
    	Response response = client.url(rootUrl()+"search").setQueryParameter("query", "").get().get();
        // Then the result is empty.
    	Assertions.assertThat(response.getStatus()).isEqualTo(Http.INTERNAL_SERVER_ERROR);
    	Assertions.assertThat(response.getBody().contains("Misformatted queryString")).isTrue();
    }

    @Test
    public void shouldUnmatchingQueryReturnsEmptyResults() throws InterruptedException, ExecutionException, IOException {
        // When searching with an unmatching query
    	Response response = client.url(rootUrl()+"search").setQueryParameter("query", "toto").get().get();
    	AbstractPermissionsOwner[] results = JsonHelper.deserialize(response.getBody(), AbstractPermissionsOwner[].class); 

    	// Then the result is empty.
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.length).isEqualTo(0);
    }

    @Test
    public void shouldQueryReturnsUsers() throws InterruptedException, ExecutionException, IOException {
        // Given a user with jdujardin as login
        User user = new User();
        user.setLogin("jdujardin");
        user.setPassword("pwd");
        
        Response response = client.url(rootUrl()+"user").jsonPost(user).get();
        user = JsonHelper.deserialize(response.getBody(), User.class); 

        // Given a user with jean as name
        User user2 = new User();
        user2.setLogin("user2");
        user2.setLastName("jean");
        user2.setPassword("pwd");
        response = client.url(rootUrl()+"user").jsonPost(user2).get();
        user2 = JsonHelper.deserialize(response.getBody(), User.class);

        // When searching the created user
        response = client.url(rootUrl()+"search").setQueryParameter("query", "j").get().get();
        User[] results = JsonHelper.deserialize(response.getBody(), User[].class);
        // Then the result contains the user.
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.length).isEqualTo(2);
        Assertions.assertThat(results[0]).isEqualTo(user);
        Assertions.assertThat(results[1]).isEqualTo(user2);

        // Cleanup after work
        client.url(rootUrl()+"user/"+user.getId()).delete();
        client.url(rootUrl()+"user/"+user2.getId()).delete();
    }

    @Test
    public void shouldQueryReturnsUsersInJson() throws InterruptedException, ExecutionException, IOException {
        // Given a user with jdujardin as login
        User user = new User();
        user.setLogin("jdujardin");
        user.setPassword("pwd");
        Response response = client.url(rootUrl()+"user").jsonPost(user).get();
        user = JsonHelper.deserialize(response.getBody(), User.class); 

        // Given a user with jean as name
        User user2 = new User();
        user2.setLogin("user2");
        user2.setLastName("jean");
        user2.setPassword("pwd");
        response = client.url(rootUrl()+"user").jsonPost(user2).get();
        user2 = JsonHelper.deserialize(response.getBody(), User.class);

        response = client.url(rootUrl()+"search").setQueryParameter("query", "j").get().get();
        User[] results = JsonHelper.deserialize(response.getBody(), User[].class);

        // Then the result contains the user.
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.length).isEqualTo(2);
        Assertions.assertThat(results[0]).isEqualTo(user);
        Assertions.assertThat(results[1]).isEqualTo(user2);

        // Cleanup after work
        client.url(rootUrl()+"user/"+user.getId()).delete();
        client.url(rootUrl()+"user/"+user2.getId()).delete();
    }

    @Test
    public void shouldQueryWithoutUsersNotReturnsUsers() throws IllegalArgumentException, InterruptedException, ExecutionException, IOException {

        // Given a user
        User user = new User();
        user.setLogin("jdujardin");
        user.setPassword("pwd");
        Response response = client.url(rootUrl()+"user").jsonPost(user).get();
        user = JsonHelper.deserialize(response.getBody(), User.class);

        // When searching the created user without users
        response = client.url(rootUrl()+"search").setQueryParameter("query", "j").setQueryParameter("users", "false").get().get();
        User[] results = JsonHelper.deserialize(response.getBody(), User[].class);
        // Then the result does not contains the user.
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.length).isEqualTo(0);

        // Cleanup after work
        client.url(rootUrl()+"user/"+user.getId()).delete();
    }
}
