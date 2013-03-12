package org.resthub.identity.core.service;

import org.resthub.common.service.CrudServiceImpl;
import org.resthub.identity.core.event.RoleEvent;
import org.resthub.identity.core.repository.GenericRoleRepository;
import org.resthub.identity.core.repository.PermissionsOwnerRepository;
import org.resthub.identity.core.repository.RoleRepository;
import org.resthub.identity.exception.AlreadyExistingEntityException;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.PermissionsOwner;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.identity.service.GenericGroupService;
import org.resthub.identity.service.GenericRoleService;
import org.resthub.identity.service.GenericUserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 * Default implementation of a Role Service (can be override by creating a bean with the same name after this one)
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public class GenericRoleServiceImpl<T extends Role, TRepository extends GenericRoleRepository<T>> extends CrudServiceImpl<T, Long, TRepository> implements GenericRoleService<T>, ApplicationEventPublisherAware {

	protected PermissionsOwnerRepository permissionsOwnerRepository;
	protected GenericUserService userService;
	protected GenericGroupService groupService;

    private ApplicationEventPublisher applicationEventPublisher = null;

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

	@Override
	@Inject
	@Named("roleRepository")
	public void setRepository(TRepository roleRepository) {
		super.setRepository(roleRepository);
	}
	
    @Inject
    @Named("permissionsOwnerRepository")
    public void setPermissionsOwnerRepository(PermissionsOwnerRepository permissionsOwnerRepository) {
		this.permissionsOwnerRepository = permissionsOwnerRepository;
	}   
	
	@Inject
	@Named("userService")
	protected void setUserService(GenericUserService userService) {
		this.userService = userService;
	}

	@Inject
	@Named("groupService")
	protected void setGroupService(GenericGroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		// Get the actual group
		T role = this.findById(id);

		// Let the other delete method do the job
		this.delete(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(T role) {
		// Find the elements with this role
		List<PermissionsOwner> withRole = permissionsOwnerRepository.getWithRoles(Arrays.asList(role
				.getName()));

		for (PermissionsOwner owner : withRole) {
			if (owner instanceof Group) {
				this.groupService.removeRoleFromGroup(((Group) owner).getName(), role.getName());
			} else if (owner instanceof User) {
				this.userService.removeRoleFromUser(((User) owner).getLogin(), role.getName());
			}
		}

		// Proceed with the actual delete
		super.delete(role);
        this.applicationEventPublisher.publishEvent(new RoleEvent(RoleEvent.RoleEventType.ROLE_DELETION, role));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findByName(String name) {
		List<T> roles = this.repository.findByName(name);
		int size = roles.size();
		return (size > 0) ? roles.get(0) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findByNameLike(String pattern) {
		List<T> roles = this.repository.findByNameLike(pattern);
		return roles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false)
	public T create(T resource) {
		// Call the standard role creation
		T createdRole = super.create(resource);
		// Publish the creation event
        this.applicationEventPublisher.publishEvent(new RoleEvent(RoleEvent.RoleEventType.ROLE_CREATION, createdRole));
		return createdRole;
	}

    @Override
    @Transactional(readOnly = false)
    public T update(T role) throws AlreadyExistingEntityException {
        // Check if there is an already existing role with this name with a
        // different ID
        T existingRole = this.findByName(role.getName());
        if (existingRole == null || existingRole.getId() == role.getId()) {
            role = super.update(role);
            this.applicationEventPublisher.publishEvent(new RoleEvent(RoleEvent.RoleEventType.ROLE_UPDATE, role));
        } else {
            throw new AlreadyExistingEntityException("Role " + role.getName() + " already exists.");
        }
        return role;
    }

}
