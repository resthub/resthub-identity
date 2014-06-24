package org.resthub.identity.core.repository.impl;

import org.fest.assertions.api.Assertions;
import org.resthub.identity.core.repository.AbstractGroupRepository;
import org.resthub.identity.model.Group;
import org.resthub.test.AbstractTransactionalTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;

@ContextConfiguration(locations = {"classpath:hikariCPContext.xml"})
@ActiveProfiles({"resthub-jpa", "resthub-pool-hikaricp"})
public class DefaultGroupRepositoryTest extends AbstractTransactionalTest {

    private static final String GROUP_NAME = "TestGroup";
    private static final String NEW_GROUP_NAME = "NewGroup";

    @Inject
    @Named("groupRepository")
    private AbstractGroupRepository<Group, Long> groupRepository;

    // Cleanup after each test
    @BeforeMethod
    public void cleanBefore() {
        groupRepository.deleteAll();
    }

    // Cleanup after each test
    @AfterMethod
    public void cleanAfter() {
        groupRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        Group group = new Group();
        group.setName(GROUP_NAME + Math.round(Math.random() * 100));
        group = groupRepository.save(group);
        Assertions.assertThat(group).as("Group not created !").isNotNull();
    }

    @Test
    public void testUpdate() {
        // Créer un groupe
        Group group = new Group();
        group.setName(GROUP_NAME + Math.round(Math.random() * 100));
        group = groupRepository.save(group);
        Assertions.assertThat(group).as("Group not created !").isNotNull();
        // Récupérer le groupe créé et le modifier
        Group group1 = groupRepository.findOne(group.getId());
        group1.setName(NEW_GROUP_NAME);
        groupRepository.save(group1);
        // Récupérer le groupe pour vérifier s'il a bien été modifié
        Group group2 = groupRepository.findOne(group.getId());
        Assertions.assertThat(group2.getName()).as("Group not updated!").isEqualTo(NEW_GROUP_NAME);
    }

}
