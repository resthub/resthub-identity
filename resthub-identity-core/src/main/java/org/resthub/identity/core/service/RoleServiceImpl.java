package org.resthub.identity.core.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.resthub.identity.core.repository.AbstractPermissionsOwnerRepository;
import org.resthub.identity.core.repository.RoleRepository;
import org.resthub.identity.exception.AlreadyExistingEntityException;
import org.resthub.identity.model.AbstractPermissionsOwner;
import org.resthub.identity.model.Group;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.resthub.identity.service.GroupService;
import org.resthub.identity.service.RoleService;
import org.resthub.identity.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of a Role Service (can be override by creating a bean with the same name after this one)
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public class RoleServiceImpl extends AbstractTraceableServiceImpl<Role, RoleRepository> implements RoleService {

	protected AbstractPermissionsOwnerRepository abstractPermissionsOwnerRepository;
	protected UserService userService;
	protected GroupService groupService;

	@Override
	@Inject
	@Named("roleRepository")
	public void setRepository(RoleRepository roleRepository) {
		super.setRepository(roleRepository);
	}
	
    @Inject
    @Named("abstractPermissionsOwnerRepository")
    public void setAbstractPermissionsOwnerRepository(AbstractPermissionsOwnerRepository abstractPermissionsOwnerRepository) {
		this.abstractPermissionsOwnerRepository = abstractPermissionsOwnerRepository;
	}   
	
	@Inject
	@Named("userService")
	protected void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Inject
	@Named("groupService")
	protected void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		// Get the actual group
		Role role = this.findById(id);

		// Let the other delete method do the job
		this.delete(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(Role role) {
		// Find the elements with this role
		List<AbstractPermissionsOwner> withRole = abstractPermissionsOwnerRepository.getWithRoles(Arrays.asList(role
				.getName()));

		for (AbstractPermissionsOwner owner : withRole) {
			if (owner instanceof Group) {
				this.groupService.removeRoleFromGroup(((Group) owner).getName(), role.getName());
			} else if (owner instanceof User) {
				this.userService.removeRoleFromUser(((User) owner).getLogin(), role.getName());
			}
		}

		// Proceed with the actual delete
		super.delete(role);
		this.publishChange(RoleChange.ROLE_DELETION.name(), role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findByName(String name) {
		List<Role> roles = this.repository.findByName(name);
		int size = roles.size();
		return (size > 0) ? roles.get(0) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> findByNameLike(String pattern) {
		List<Role> roles = this.repository.findByNameLike(pattern);
		return roles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = false)
	public Role create(Role resource) {
		// Call the standard role creation
		Role createdRole = super.create(resource);
		// Publish the creation event
		this.publishChange(RoleChange.ROLE_CREATION.name(), createdRole);
		return createdRole;
	}

    @Override
    @Transactional(readOnly = false)
    public Role update(Role role) throws AlreadyExistingEntityException {
        // Check if there is an already existing role with this name with a
        // different ID
        Role existingRole = this.findByName(role.getName());
        if (existingRole == null || existingRole.getId() == role.getId()) {
            role = super.update(role);
            publishChange(RoleChange.ROLE_UPDATE.name(), role);
        } else {
            throw new AlreadyExistingEntityException("Role " + role.getName() + " already exists.");
        }
        return role;
    }

}
