package org.resthub.identity.core.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named("testIdentityUserDetailsService")
@Transactional
public class TestIdentityUserDetailsService extends IdentityUserDetailsService {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        // use the credentials to try to authenticate against the third party system

        if ("test".equals(name) && "test".equals(password)) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.USER));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.USER));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.USER));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.USER));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.GROUP));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.GROUP));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.GROUP));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.GROUP));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.CREATE + IdentityRoles.ROLE));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.READ + IdentityRoles.ROLE));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.UPDATE + IdentityRoles.ROLE));
            authorities.add(new SimpleGrantedAuthority(IdentityRoles.PFX + IdentityRoles.DELETE + IdentityRoles.ROLE));
            return new UsernamePasswordAuthenticationToken(name, password, authorities);
        } else {
            throw new BadCredentialsException("Unable to auth against third party systems (test class)");
        }
    }
}
