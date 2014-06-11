package org.resthub.identity.core.service;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.event.RoleEvent;
import org.resthub.identity.core.event.UserEvent;
import org.resthub.identity.core.repository.AbstractPermissionsOwnerRepository;
import org.resthub.identity.core.repository.AbstractUserRepository;
import org.resthub.identity.service.ApplicationService;
import org.resthub.identity.service.GroupService;
import org.resthub.identity.service.RoleService;
import org.resthub.identity.service.UserService;
import org.resthub.identity.core.tools.PermissionsOwnerTools;
import org.resthub.identity.exception.AlreadyExistingEntityException;
import org.resthub.identity.model.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Default implementation of a User Service (can be override by creating a bean with the same name after this one)
 */
public abstract class AbstractUserService<T extends User, ID extends Serializable, R extends AbstractUserRepository<T, ID>> extends CrudServiceImpl<T, ID, R> implements UserService<T, ID>, ApplicationEventPublisherAware {
    protected AbstractPermissionsOwnerRepository permissionsOwnerRepository;
    protected GroupService groupService;
    protected RoleService roleService;
    protected PasswordEncoder passwordEncoder;
    protected ApplicationService applicationService;

    private ApplicationEventPublisher applicationEventPublisher = null;

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Inject
    @Named("userRepository")
    public void setRepository(R userRepository) {
        super.setRepository(userRepository);
    }

    @Inject
    @Named("permissionsOwnerRepository")
    public void setAbstractPermissionsOwnerRepository(AbstractPermissionsOwnerRepository permissionsOwnerRepository) {
        this.permissionsOwnerRepository = permissionsOwnerRepository;
    }

    @Inject
    @Named("groupService")
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Inject
    @Named("applicationService")
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Inject
    @Named("roleService")
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Inject
    @Named("passwordEncoder")
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public T create(T user) throws AlreadyExistingEntityException {
        User existingUser = this.findByLogin(user.getLogin());
        if (existingUser == null) {
            if (user.getPassword() == null) {
                user.setPassword(user.generateDefaultPassword());
            }
            user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
            // Overloaded method call
            T created = super.create(user);
            // Publish notification
            this.applicationEventPublisher.publishEvent(new UserEvent(UserEvent.UserEventType.USER_CREATION, created));
            return created;
        } else {
            throw new AlreadyExistingEntityException("User " + user.getLogin() + " already exists.");
        }
    } // create().

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public T update(T user) throws AlreadyExistingEntityException {
        // Check if there is an already existing user with this login with a
        // different ID
        User existingUser = this.findById((ID) user.getId());
        if (user.getPassword() == null) {
            user.setPassword(existingUser.getPassword());
        } else if (!user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        }

        T userRet = super.update(user);
        this.applicationEventPublisher.publishEvent(new UserEvent(UserEvent.UserEventType.USER_UPDATE, userRet));

        return userRet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(ID id) {
        User deleted = findById(id);
        // Overloaded method call
        super.delete(id);
        // Publish notification
        this.applicationEventPublisher.publishEvent(new UserEvent(UserEvent.UserEventType.USER_DELETION, deleted));
    } // delete().

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(T user) {
        super.delete(user);
        // Publish notification
        this.applicationEventPublisher.publishEvent(new UserEvent(UserEvent.UserEventType.USER_DELETION, user));
    } // delete().

    /**
     * Retrieves a user by his login
     *
     * @param login the login to look for
     * @return the corresponding User object if founded, null otherwise
     */
    @Override
    public T findByLogin(String login) {
        Assert.notNull(login, "User login can't be null");
        List<T> result = this.repository.findByLogin(login);
        int size = result.size();
        return (size > 0 && size < 2) ? result.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Permission> getUserPermissions(String login) {
        List<Permission> p = null;
        User u = this.findByLogin(login);
        if (u != null) {
            p = PermissionsOwnerTools.getInheritedPermission(u);
        }
        return p;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Permission> getUserPermissions(String login, String applicationName) {
        List<Permission> permissions = null;
        User u = this.findByLogin(login);
        Assert.notNull(u);
        permissions = PermissionsOwnerTools.getInheritedPermission(u);
        Application application = applicationService.findByName(applicationName);
        Assert.notNull(application);
        for (int i = permissions.size() - 1; i >= 0; i--) {
            Permission p = permissions.get(i);
            if (p.getApplication() != application) {
                permissions.remove(p);
            }
        }
        return permissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Permission> getUserDirectPermissions(String login) {
        List<Permission> p = null;
        User u = this.findByLogin(login);
        if (u != null) {
            p = u.getPermissions();
        }
        return p;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = false)
    @Override
    public void addPermissionToUser(String userLogin, Permission permission) {
        if (userLogin != null && permission != null) {
            T u = this.findByLogin(userLogin);
            if (u != null) {
                boolean alreadyAllowed = u.getPermissions().contains(permission);
                if (!alreadyAllowed) {
                    u.getPermissions().add(permission);
                }
                this.update(u);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = false)
    @Override
    public void removePermissionFromUser(String userLogin, Permission permission) {
        if (userLogin != null && permission != null) {
            T u = this.findByLogin(userLogin);
            if (u != null) {
                while (u.getPermissions().contains(permission)) {
                    u.getPermissions().remove(permission);
                    this.update(u);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = false)
    @Override
    public void addGroupToUser(String userLogin, String groupName) {
        if (userLogin != null && groupName != null) {
            T u = this.findByLogin(userLogin);
            Group g = groupService.findByName(groupName);
            if (u != null && g != null) {
                u.getGroups().add(g);
                this.repository.save(u);
            }
            // Publish notification
            this.applicationEventPublisher.publishEvent(new UserEvent(UserEvent.UserEventType.USER_ADDED_TO_GROUP, u, g));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = false)
    @Override
    public void removeGroupFromUser(String userLogin, String groupName) {
        if (userLogin != null && groupName != null) {
            T u = this.findByLogin(userLogin);
            Group g = groupService.findByName(groupName);
            if (u != null && g != null) {
                u.getGroups().remove(g);
                this.repository.save(u);
            }
            // Publish notification
            this.applicationEventPublisher.publishEvent(new UserEvent(UserEvent.UserEventType.USER_REMOVED_FROM_GROUP, u, g));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> getUsersFromGroup(String groupName) {
        return this.repository.getUsersFromGroup(groupName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findAllUsersWithRoles(List<String> roles) {
        List<T> usersWithRole = new ArrayList<T>(); // this list will hold
        // all the users for
        // the result

        // Start by finding the entities directly linked to the roles
        List<PermissionsOwner> withRoles = permissionsOwnerRepository.getWithRoles(roles);

        // The query may have brought a mix of users and groups,
        // this loop will process them individually to form the final result.
        for (PermissionsOwner owner : withRoles) {
            this.getUsersFromRootElement(usersWithRole, owner);
        }

        return usersWithRole;
    }

    /**
     * Recursive method to get all the users in an AbstractPermissionsOwner, if the owner is a user, it will be directly
     * added to the list, if the owner is a group, his subgroups will be explored to find users.
     *
     * @param users User list to add users into, must not be null.
     * @param owner Root element to begin exploration.
     */
    private void getUsersFromRootElement(List<T> users, PermissionsOwner owner) {
        // Stop the processing if one of the parameters is null
        if (users != null && owner != null) {
            // The root element may be user or a group
            if (owner instanceof User) {
                T user = (T) owner;
                // If we have a user, we can't go further so add it if needed
                // and finish.
                if (!users.contains(user)) {
                    users.add(user);
                }
            } else if (owner instanceof Group) {
                // If we have a group, we must get both users and groups having
                // this group as parent
                List<PermissionsOwner> withGroupAsParent = permissionsOwnerRepository
                        .getWithGroupAsParent((Group) owner);

                // Each result will be recursively evaluated using this method.
                for (PermissionsOwner child : withGroupAsParent) {
                    this.getUsersFromRootElement(users, child);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addRoleToUser(String userLogin, String roleName) {
        T u = this.findByLogin(userLogin);
        if (u != null) {
            Role r = roleService.findByName(roleName);
            if (r != null) {
                if (!u.getRoles().contains(r)) {
                    u.getRoles().add(r);
                    this.update(u);
                    this.applicationEventPublisher.publishEvent(new RoleEvent(RoleEvent.RoleEventType.ROLE_ADDED_TO_USER, r, u));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeRoleFromUser(String userLogin, String roleName) {
        T u = this.findByLogin(userLogin);
        if (u != null) {
            Role r = roleService.findByName(roleName);
            if (r != null) {
                if (u.getRoles().contains(r)) {
                    u.getRoles().remove(r);
                    this.update(u);
                    this.applicationEventPublisher.publishEvent(new RoleEvent(RoleEvent.RoleEventType.ROLE_REMOVED_FROM_USER, r, u));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Group> getAllUserGroups(String userLogin) {
        List<Group> roles = new ArrayList<Group>();
        User u = this.findByLogin(userLogin);
        if (u != null) {
            this.getGroupsFromRootElement(roles, u);
        }

        return roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getGroupsFromRootElement(List<Group> groups, PermissionsOwner owner) {
        // Stop the processing if one of the parameters is null
        if (groups != null && owner != null) {
            // Add the roles we find on our path if needed.
            for (Group g : owner.getGroups()) {
                if (!groups.contains(g)) {
                    groups.add(g);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllUserRoles(String userLogin) {
        List<Role> roles = new ArrayList<Role>();
        User u = this.findByLogin(userLogin);
        if (u != null) {
            this.getRolesFromRootElement(roles, u);
        }

        return roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getRolesFromRootElement(List<Role> roles, PermissionsOwner owner) {
        // Stop the processing if one of the parameters is null
        if (roles != null && owner != null) {
            // Add the roles we find on our path if needed.
            for (Role r : owner.getRoles()) {
                if (!roles.contains(r)) {
                    roles.add(r);
                }
            }

            // Climbing up the hierarchy of groups recursively.
            for (Group g : owner.getGroups()) {
                this.getRolesFromRootElement(roles, g);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public T updatePassword(T user) {
        T retrievedUser = this.findByLogin(user.getLogin());
        String newPassword = user.getPassword();
        retrievedUser.setPassword(passwordEncoder.encodePassword(newPassword, null));
        return repository.save(retrievedUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T authenticateUser(String login, String password) {
        T retrievedUser = this.findByLogin(login);
        if ((retrievedUser != null) && passwordEncoder.isPasswordValid(retrievedUser.getPassword(), password, null)) {
            return retrievedUser;
        }
        return null;
    }
}
