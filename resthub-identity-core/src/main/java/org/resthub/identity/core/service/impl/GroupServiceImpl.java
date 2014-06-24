package org.resthub.identity.core.service.impl;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.event.GroupEvent;
import org.resthub.identity.core.event.RoleEvent;
import org.resthub.identity.core.repository.GroupRepository;
import org.resthub.identity.core.repository.UserRepository;
import org.resthub.identity.core.service.GroupService;
import org.resthub.identity.core.service.RoleService;
import org.resthub.identity.exception.AlreadyExistingEntityException;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


/**
 * Default implementation of a Group Service (can be override by creating a bean with the same name after this one)
 */
public class GroupServiceImpl<T extends Group, I extends Serializable, R extends GroupRepository<T, I>> extends CrudServiceImpl<T, I, R> implements GroupService<T, I> {
    /**
     * The userRepository<br/>
     * This class need it in order to be able to deal with users
     */
    protected UserRepository userRepository;

    protected RoleService roleService;

    private ApplicationEventPublisher applicationEventPublisher = null;

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    protected void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findByName(String name) {
        Assert.notNull(name, "Group name can't be null");
        return this.repository.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> getGroupsFromGroup(String groupName) {
        return this.repository.findGroupsFromGroup(groupName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addGroupToGroup(String groupName, String subGroupName) {
        if (groupName != null && subGroupName != null) {
            T group = this.findByName(groupName);
            T subGroup = this.findByName(subGroupName);
            if (group != null && subGroup != null) {
                boolean contain = group.getGroups().contains(subGroup);
                if (!contain) {
                    group.getGroups().add(subGroup);
                    this.repository.save(group);
                    // Publish notification
                    this.applicationEventPublisher.publishEvent(new GroupEvent(GroupEvent.GroupEventType.GROUP_ADDED_TO_GROUP, group, subGroup));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addPermissionToGroup(String groupName, Permission permission) {
        if (groupName != null && permission != null) {
            T g = this.findByName(groupName);
            if (g != null) {
                boolean contains = g.getPermissions().contains(permission);
                if (!contains) {
                    g.getPermissions().add(permission);
                    this.repository.save(g);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<Permission> getGroupDirectPermissions(String groupName) {
        List<Permission> permissions = null;
        if (groupName != null) {
            T g = this.findByName(groupName);
            if (g != null) {
                permissions = g.getPermissions();
            }
        }
        return permissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeGroupFromGroup(String groupName, String subGroupName) {
        if (groupName != null && subGroupName != null) {
            T group = this.findByName(groupName);
            T subGroup = this.findByName(subGroupName);
            if (group != null && subGroup != null) {
                boolean contain = group.getGroups().contains(subGroup);
                if (contain) {
                    group.getGroups().remove(subGroup);
                    this.repository.save(group);
                    // Publish notification
                    this.applicationEventPublisher.publishEvent(new GroupEvent(GroupEvent.GroupEventType.GROUP_REMOVED_FROM_GROUP, group, subGroup));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removePermissionFromGroup(String groupName, Permission permission) {
        T g = this.findByName(groupName);
        if (g != null && permission != null) {
            List<Permission> permissions = g.getPermissions();
            while (permissions.contains(permission)) {
                permissions.remove(permission);
            }
            this.repository.save(g);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(I id) {
        // Get the actual group
        T group = this.findById(id);

        // Let the other delete method do the job
        this.delete(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public T create(T group) throws AlreadyExistingEntityException {
        T existingGroup = this.findByName(group.getName());
        if (existingGroup == null) {
            // Overriden method call.
            group = super.create(group);
            // Publish notification
            this.applicationEventPublisher.publishEvent(new GroupEvent(GroupEvent.GroupEventType.GROUP_CREATION, group));
            return group;
        } else {
            throw new AlreadyExistingEntityException("Group " + group.getName() + " already exists.");
        }
    } // create().

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public T update(T group) throws AlreadyExistingEntityException {
        // Check if there is an already existing group with this name with a
        // different I
        T existingGroup = this.findByName(group.getName());
        if (existingGroup == null || existingGroup.getId() == group.getId()) {
            group = super.update(group);
            this.applicationEventPublisher.publishEvent(new GroupEvent(GroupEvent.GroupEventType.GROUP_UPDATE, group));
        } else {
            throw new AlreadyExistingEntityException("Group " + group.getName() + " already exists.");
        }
        return group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(T group) {
        // Find the users who are in this group
        List<User> users = userRepository.getUsersFromGroup(group.getName());

        // Unlink each user from this group
        for (User user : users) {
            user.getGroups().remove(group);
        }
        userRepository.save(users);

        // Proceed with the actual delete
        super.delete(group);
        // Publish notification
        this.applicationEventPublisher.publishEvent(new GroupEvent(GroupEvent.GroupEventType.GROUP_DELETION, group));
    } // delete().

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> getRolesFromGroup(String groupName) {
        return this.repository.findRolesFromGroup(groupName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addRoleToGroup(String groupName, String roleName) {
        T g = this.findByName(groupName);
        if (g != null) {
            Role r = roleService.findByName(roleName);
            if (r != null) {
                if (!g.getRoles().contains(r)) {
                    g.getRoles().add(r);
                    this.repository.save(g);
                    this.applicationEventPublisher.publishEvent(new RoleEvent(RoleEvent.RoleEventType.ROLE_ADDED_TO_GROUP, r, g));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeRoleFromGroup(String groupName, String roleName) {
        T g = this.findByName(groupName);
        if (g != null) {
            Role r = roleService.findByName(roleName);
            if (r != null) {
                if (g.getRoles().contains(r)) {
                    g.getRoles().remove(r);
                    this.repository.save(g);
                    this.applicationEventPublisher.publishEvent(new RoleEvent(RoleEvent.RoleEventType.ROLE_REMOVED_FROM_GROUP, r, g));
                }
            }
        }
    }
}
