package org.resthub.identity.core.security;

/**
 * Created by Bastien on 11/06/14.
 */
public class IdentityRoles {
    private static final String ROLE = "ROLE_";
    private static final String CREATE = "CREATE";
    private static final String READ = "READ";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";

    public static final String ROLE_CREATE = ROLE + CREATE;
    public static final String ROLE_READ = ROLE + READ;
    public static final String ROLE_UPDATE = ROLE + UPDATE;
    public static final String ROLE_DELETE = ROLE + DELETE;
}
