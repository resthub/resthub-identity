package org.resthub.identity.core.security;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Bastien on 11/06/14.
 */
public class IdentityRoles {
    @Value("${roles.prefix}")
    public static final String PFX = "ROLE_";

    @Value("${roles.crud.create}")
    public static final String CREATE = "CREATE_";

    @Value("${roles.crud.read}")
    public static final String READ = "READ_";

    @Value("${roles.crud.update}")
    public static final String UPDATE = "UPDATE_";

    @Value("${roles.crud.delete}")
    public static final String DELETE = "DELETE_";

    @Value("${roles.application.user}")
    public static final String USER = "USER";

    @Value("${roles.application.group}")
    public static final String GROUP = "GROUP";

    @Value("${roles.application.role}")
    public static final String ROLE = "ROLE";
}
