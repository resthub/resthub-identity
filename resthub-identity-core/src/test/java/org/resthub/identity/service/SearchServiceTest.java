package org.resthub.identity.service;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import org.elasticsearch.client.Client;
import org.fest.assertions.api.Assertions;
import org.resthub.identity.elasticsearch.Deleter;
import org.resthub.identity.elasticsearch.Requester;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.User;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@ActiveProfiles("resthub-jpa")
public class SearchServiceTest extends AbstractTransactionalTest {

    /**
     * Injection of the User service.
     */
    @Inject
    @Named("userService")
    protected UserService userService;

    /**
     * Injection of elasticsearch requester;
     */
    @Inject
    @Named("elasticRequester")
    private Requester requester;
    
    /**
     * Injection of elasticsearch deleter;
     */
    @Inject
    @Named("elasticDeleter")
    private Deleter deleter;
    
    /**
     * Injection of the Group service.
     */
    @Inject
    @Named("groupService")
    protected GroupService groupService;

    
    private @Value("#{esProp['index.name']}") String indexName;
    private @Value("#{esProp['index.group.type']}") String groupIndex;
    private @Value("#{esProp['index.user.type']}") String userIndex;
    private @Value("#{esProp['index.role.type']}") String roleIndex;
    // Inject your client...
 	@Autowired
 	Client client;
 	
 	@AfterMethod
	public void setup(){
		deleter.deleteIndex(client, indexName, userIndex);
		deleter.deleteIndex(client, indexName, roleIndex);
		deleter.deleteIndex(client, indexName, groupIndex);
	}
    
   /* @Test
    public void shouldExistingResourcesBeReIndexed() {
        // Given an existing group
        Group g1 = new Group();
        g1.setName("group 1" + new Random().nextInt());
        g1 = groupService.create(g1);

        // Given an existing user
        User u1 = new User();
        u1.setLogin("user 1" + new Random().nextInt());
        u1.setPassword(u1.getLogin());
        u1 = userService.create(u1);

        // TODO Given an existing role

        // When reindexing
        searchService.resetIndexes();

        // Delete
        groupService.deleteAll();
        
        // Then everything's fine
    } // shouldExistingResourcesBeReIndexed().*/
	
 	
 	@Test
	public void shouldUsersBeRetrieved() {
		String pwd = "password";
		String login = "login";

		// Given a user with login 'jdujardin'
		User u1 = new User();
		u1.setLogin("jdujardin" + new Random().nextInt());
		u1.setPassword(pwd);
		u1 = userService.create(u1);

		// Given a user with email 'jdujardin@test.com'
		User u2 = new User();
		u2.setLogin(login + new Random().nextInt());
		u2.setEmail("jdujardin@test.com");
		u2.setPassword(pwd);
		u2 = userService.create(u2);

		// Given a user with first name 'jean'
		User u3 = new User();
		u3.setLogin(login + new Random().nextInt());
		u3.setFirstName("jean");
		u3.setPassword(pwd);
		u3 = userService.create(u3);

		// Given a user with last name 'dujardin'
		User u4 = new User();
		u4.setLogin(login + new Random().nextInt());
		u4.setLastName("dujardin");
		u4.setPassword(pwd);
		u4 = userService.create(u4);

		// Given a user with login 'adurand'
		User u5 = new User();
		u5.setLogin(login + new Random().nextInt());
		u5.setLogin("adurand");
		u5.setPassword(pwd);
		u5 = userService.create(u5);

		// When requesting j on users
		// List<AbstractPermissionsOwner> results = searchService.search("j",
		// true, false);
		List<User> results = requester.requestSimpleOr(client,indexName, userIndex,
				"j*", User.class);
		// Then the first user is retrieved
		Assertions.assertThat(results.contains(u1))
				.as("login 'jdujardin' did not match").isTrue();

		// Then the second user is retrieved
		Assertions.assertThat(results.contains(u2))
				.as("email 'jdujardin@test.com' did not match").isTrue();

		// Then the third user is retrieved
		Assertions.assertThat(results.contains(u3))
				.as("first name 'jean' did not match").isTrue();

		// Then the fourth user is not retrived
		Assertions.assertThat(results.contains(u4))
				.as("last name 'dujardin' did match").isFalse();

		// Then the fifth user is not retrived
		Assertions.assertThat(results.contains(u5))
				.as("login 'adurand' did match").isFalse();

		// Delete
		userService.deleteAll();

	} // shouldUsersBeRetrieved().
 	
 	@Test
	public void shouldGroupsBeRetrieved() {
		// Given a group with name 'jeans'
		Group g1 = new Group();
		g1.setName("jdujardin" + new Random().nextInt());
		g1 = groupService.create(g1);

		// Given a group with name 'dujeu'
		Group g2 = new Group();
		g2.setName("dujeu" + new Random().nextInt());
		g2 = groupService.create(g2);

		// Given a group with login 'other'
		Group g3 = new Group();
		g3.setName("other" + new Random().nextInt());
		g3 = groupService.create(g3);

		// When requesting j on users
		// List<AbstractPermissionsOwner> results = searchService.search("j",
		// false, true);
		List<Group> results = requester.requestSimpleOr(client,indexName,
				groupIndex, "j*", Group.class);

		// Then the first group is retrieved
		Assertions.assertThat(results.contains(g1))
				.as("name 'jdujardin' did not match").isTrue();

		// Then the second group is not retrived
		Assertions.assertThat(results.contains(g2))
				.as("name 'dujeu' did match").isFalse();

		// Then the third group is not retrived
		Assertions.assertThat(results.contains(g3))
				.as("name 'other' did match").isFalse();

		// Delete
		groupService.deleteAll();

	} // shouldGroupsBeRetrieved().

 	
 	@Test
	public void shouldUserAndGroupsBeRetrieved() {
		String pwd = "password";

		// Given a user with login 'jdujardin'
		User u1 = new User();
		u1.setLogin("jdujardin" + new Random().nextInt());
		u1.setPassword(pwd);
		u1 = userService.create(u1);

		// Given a user with first name 'other'
		User u2 = new User();
		u2.setLogin("other" + new Random().nextInt());
		u2.setPassword(pwd);
		u2 = userService.create(u2);

		// Given a group with name 'jeans'
		Group g1 = new Group();
		g1.setName("jeans" + new Random().nextInt());
		g1 = groupService.create(g1);

		// Given a group with login 'other-group'
		Group g2 = new Group();
		g2.setName("other-group" + new Random().nextInt());
		g2 = groupService.create(g2);

		// When requesting j on users and groups
		// List<AbstractPermissionsOwner> results = searchService.search("j",
		// true, true);
		List<User> resultsU = requester.requestSimpleOr(client, indexName, userIndex,
				"j*", User.class);

		List<Group> resultsG = requester.requestSimpleOr(client, indexName,
				groupIndex, "j*", Group.class);
		// Then the first user is retrieved
		Assertions.assertThat(resultsU.contains(u1))
				.as("login 'jdujardin' did not match").isTrue();

		// Then the second user is not retrieved
		Assertions.assertThat(resultsU.contains(u2))
				.as("login 'other' did match").isFalse();

		// Then the first group is retrieved
		Assertions.assertThat(resultsG.contains(g1))
				.as("login 'jeans' did not match").isTrue();

		// Then the second group is not retrived
		Assertions.assertThat(resultsG.contains(g2))
				.as("name 'other-group' did match").isFalse();

		// Delete
		userService.deleteAll();
		groupService.deleteAll();

	} // shouldUserAndGroupsBeRetrieved()
 	
 	@Test
	public void shouldComplexQueriesBeExecuted() {
		String pwd = "password";

		// Given a user with login 'dujardin' and first name 'jean'
		User u1 = new User();
		u1.setLogin("dujardin" + new Random().nextInt());
		u1.setFirstName("jean");
		u1.setPassword(pwd);
		u1 = userService.create(u1);

		// Given a user with login 'other' abd last name 'george'
		User u2 = new User();
		u2.setLogin("other" + new Random().nextInt());
		u2.setLastName("george");
		u2.setPassword(pwd);
		u2 = userService.create(u2);

		// Given a group with name 'admin'
		Group g1 = new Group();
		g1.setName("admin" + new Random().nextInt());
		g1 = groupService.create(g1);

		// Given a group with login 'users'
		Group g2 = new Group();
		g2.setName("users" + new Random().nextInt());
		g2 = groupService.create(g2);

		// When requesting j on users and groups
		// List<AbstractPermissionsOwner> results =
		// searchService.search("lastName:g* OR firstName:j* OR name:admin*",
		// true, true);
		List<User> resultsU = requester.requestSimpleOr(client, indexName, userIndex,
				"lastName:g* firstName:j* name:admin*", User.class);

		List<Group> resultsG = requester.requestSimpleOr(client, indexName,
				groupIndex, "lastName:g* firstName:j* name:admin*", Group.class);
		// Then the first user is retrieved
		Assertions.assertThat(resultsU.contains(u1))
				.as("last name 'jean' did not match").isTrue();

		// Then the second user is retrieved
		Assertions.assertThat(resultsU.contains(u2))
				.as("first name 'george' did not match").isTrue();

		// Then the first group is retrieved
		Assertions.assertThat(resultsG.contains(g1))
				.as("name 'admin' did not match").isTrue();

		// Then the second group is not retrived
		Assertions.assertThat(resultsG.contains(g2))
				.as("name 'users' did match").isFalse();

		// Delete
		userService.deleteAll();
		groupService.deleteAll();

	} // shouldComplexQueriesBeExecuted()
 	
 	@Test
	public void shouldFailedOnNullQuery() {
		try {
			// When performing a search with a null query

			requester.requestSimpleOr(client, indexName, userIndex, null, User.class);
			Assertions.fail("An IllegalArgumentException may have been raised");
		} catch (IllegalArgumentException exc) { // Then an exception is raised
			Assertions
					.assertThat(exc.getMessage().contains("must not be null"))
					.isTrue();
		}
	} // shouldFailedOnNullQuery() 


} // Class SearchDaoTest.
