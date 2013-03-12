package org.resthub.identity.core.service;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.core.listener.GroupTestListener;
import org.resthub.identity.core.event.GroupEvent;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.User;
import org.resthub.identity.service.GenericGroupService;
import org.resthub.identity.service.GenericUserService;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ActiveProfiles("resthub-jpa")
public class GroupServiceTest extends AbstractTransactionalTest {

    @Inject
    @Named("userService")
    private GenericUserService<User> userService;
    
    @Inject
    @Named("groupService")
    private GenericGroupService<Group> groupService;

    @Inject
    @Named("groupTestListener")
    private GroupTestListener testListener;

    // Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
    	groupService.deleteAll();
    	userService.deleteAll();
    }
    
	// Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
    	groupService.deleteAll();
    	userService.deleteAll();
    }
    
    public Group createTestEntity() {
        String groupName = "GroupTestGroupName" + Math.round(Math.random() * 100000);
        Group g = new Group();
        g.setName(groupName);
        return g;
    }

//    @Test
    public void testUpdate() {
        /* Given a new group */
        String groupName = "GroupTestGroupUpdate";
        Group g = new Group();
        g.setName(groupName);
        g = groupService.create(g);
        String toString1 = "Group[Id: " + g.getId() + ", Name: " + g.getName() + "]";

        Assertions.assertThat(g.toString()).isEqualTo(toString1);

        String newName = "NewName";
        g.setName(newName);
        /* When we update the group after changing the name */
        g = groupService.update(g);

        /* the name modification is taken into account */
        String toString2 = "Group[Id: " + g.getId() + ", Name: " + newName + "]";
        Assertions.assertThat(g.toString()).isEqualTo(toString2);

        groupService.delete(g);
    }

    @Test
    public void shouldDeleteGroupWithUsers() {
        /* Given a user */
        User testUser = new User();
        testUser.setLogin("testUser");
        testUser.setFirstName("firstName");
        testUser.setLastName("lastName");
        testUser.setPassword("P@ssw0rd");
        testUser.setEmail("test@test.com");
        testUser = userService.create(testUser);

        /* Given a group */
        Group testGroup = new Group();
        testGroup.setName("testGroup");
        testGroup = groupService.create(testGroup);

        /* Given a link between this group and this user */
        userService.addGroupToUser(testUser.getLogin(), testGroup.getName());

        /* When deleting this group */
        groupService.delete(testGroup);

        /* Then the user shouldn't have this group anymore */
        User user = userService.findById(testUser.getId());
        Assertions.assertThat(user.getGroups().contains(testGroup)).as("The user shouldn't contain this group anymore").isFalse();
        /* And the group shouldn't exist */
        Group deleteGroup = groupService.findById(testGroup.getId());
        Assertions.assertThat(deleteGroup).as("The deleted group shouldn't exist anymore").isNull();
    }

    @Test
    public void shouldCreationBeNotified() {

        // Given a user
        Group g = new Group();
        g.setName("group" + new Random().nextInt());

        // When saving it
        g = groupService.create(g);

        // Then a creation notification has been received
        List<GroupEvent> ge = testListener.groupEvents;
        Assertions.assertThat(ge).isNotEmpty();
        Assertions.assertThat(ge.get(ge.size() - 1).getType()).isEqualTo(GroupEvent.GroupEventType.GROUP_CREATION);
        Assertions.assertThat(ge.get(ge.size() - 1).getGroup()).isEqualTo(g);
    }

    @Test
    public void shouldDeletionBeNotifiedById() {

        // Given a created group
        Group g = new Group();
        g.setName("group" + new Random().nextInt());
        g = groupService.create(g);

        // When removing it by id
        groupService.delete(g.getId());

        // Then a deletion notification has been received
        List<GroupEvent> ge = testListener.groupEvents;
        Assertions.assertThat(ge.get(ge.size() - 1).getType()).isEqualTo(GroupEvent.GroupEventType.GROUP_DELETION);
        Assertions.assertThat(ge.get(ge.size() - 1).getGroup()).isEqualTo(g);
    }

    @Test
    public void shouldDeletionBeNotifiedByGroup() {

        // Given a created group
        Group g = new Group();
        g.setName("group" + new Random().nextInt());
        g = groupService.create(g);

        // When removing it
        groupService.delete(g);

        // Then a deletion notification has been received
        List<GroupEvent> ge = testListener.groupEvents;
        Assertions.assertThat(ge.get(ge.size() - 1).getType()).isEqualTo(GroupEvent.GroupEventType.GROUP_DELETION);
        Assertions.assertThat(ge.get(ge.size() - 1).getGroup()).isEqualTo(g);
    }

    @Test
    public void shouldGroupAdditionToGroupBeNotified() {

        // Given a created user
        Group subG = new Group();
        subG.setName("group" + new Random().nextInt());
        subG = groupService.create(subG);

        // Given a group
        Group g = new Group();
        g.setName("group" + new Random().nextInt());
        g = groupService.create(g);

        // When adding the user to the group
        ((GenericGroupService) groupService).addGroupToGroup(g.getName(), subG.getName());

        // Then a deletion notification has been received
        List<GroupEvent> ge = testListener.groupEvents;
        Assertions.assertThat(ge.get(ge.size() - 1).getType()).isEqualTo(GroupEvent.GroupEventType.GROUP_ADDED_TO_GROUP);
        Assertions.assertThat(ge.get(ge.size() - 1).getGroup()).isEqualTo(g);
        Assertions.assertThat(ge.get(ge.size() - 1).getSubGroup()).isEqualTo(subG);

        groupService.delete(g);
    }

    @Test
    public void shouldGroupRemovalFromGroupBeNotified() {

        // Given a group
        Group g = new Group();
        g.setName("group" + new Random().nextInt());
        g = groupService.create(g);

        // Given a created user in this group
        Group subG = new Group();
        subG.setName("group" + new Random().nextInt());
        subG = groupService.create(subG);
        ((GenericGroupService) groupService).addGroupToGroup(g.getName(), subG.getName());

        // When adding the user to the group
        ((GenericGroupService) groupService).removeGroupFromGroup(g.getName(), subG.getName());

        // Then a deletion notification has been received
        List<GroupEvent> ge = testListener.groupEvents;
        Assertions.assertThat(ge.get(ge.size() - 1).getType()).isEqualTo(GroupEvent.GroupEventType.GROUP_REMOVED_FROM_GROUP);
        Assertions.assertThat(ge.get(ge.size() - 1).getGroup()).isEqualTo(g);
        Assertions.assertThat(ge.get(ge.size() - 1).getSubGroup()).isEqualTo(subG);

        groupService.delete(g);
    }
    
}
