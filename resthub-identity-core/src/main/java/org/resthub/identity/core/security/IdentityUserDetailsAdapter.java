package org.resthub.identity.core.security;

import org.resthub.identity.core.tools.PermissionsOwnerTools;
import org.resthub.identity.model.Permission;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class IdentityUserDetailsAdapter implements UserDetails {

    private static final long serialVersionUID = -624970668241079594L;
    List<GrantedAuthority> grantedAuthorities;
    private User user;
    private List<Role> roles;

    public IdentityUserDetailsAdapter(User user) {
        this.user = user;

        // Initialized here in order to avoid a LazyException thrown bythe Group
        // retreival
        grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Permission permission : PermissionsOwnerTools.getInheritedPermission(user)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.getCode()));
        }

    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getLogin();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}
