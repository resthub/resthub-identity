package org.resthub.identity.core.security;

import org.resthub.identity.core.event.RoleEvent;
import org.resthub.identity.service.UserService;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named("identityUserDetailsService")
@Transactional
public class IdentityUserDetailsService implements UserDetailsService, ApplicationListener<RoleEvent> {

    @Inject
    @Named("userService")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        IdentityUserDetailsAdapter userDetails = null;

        try {
            User user = userService.findByLogin(username);

            if (null != user) {
                userDetails = new IdentityUserDetailsAdapter(user);
                userDetails.setRoles(userService.getAllUserRoles(user.getLogin()));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        if (userDetails == null) {
            throw new UsernameNotFoundException("Returned user is null");
        }
        return userDetails;
    }

    @Override
    public void onApplicationEvent(RoleEvent event) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        IdentityUserDetailsAdapter userDetails = null;

        if (auth != null && (auth.getPrincipal() instanceof IdentityUserDetailsAdapter)) {
            userDetails = (IdentityUserDetailsAdapter) auth.getPrincipal();

            // Update roles for logged users
            if (userDetails != null && (event.getType() == RoleEvent.RoleEventType.ROLE_ADDED_TO_USER
                    || event.getType() == RoleEvent.RoleEventType.ROLE_REMOVED_FROM_USER)) {
                User user = event.getUser();
                if (userDetails.getUsername().equals(user.getLogin())) {
                    List<Role> roles = new ArrayList<Role>();
                    userService.getRolesFromRootElement(roles, user);
                    userDetails.setRoles(roles);
                }

                // TODO : test if update is necessary
                // TODO : do the same for group or user update (permission)
            }
        }
    }
}
