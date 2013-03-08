package org.resthub.identity.core.event;

import org.resthub.identity.model.Group;
import org.resthub.identity.model.Role;
import org.resthub.identity.model.User;
import org.springframework.context.ApplicationEvent;

public class RoleEvent extends ApplicationEvent {

    /**
     * Kind of changes notified for role events
     */
    public enum RoleEventType {

        /**
         * Role creation. Arguments : 1- created role.
         */
        ROLE_CREATION,
        /**
         * Role update. Arguments : 1- updated role.
         */
        ROLE_UPDATE,
        /**
         * Role deletion. Arguments : 1- deleted role.
         */
        ROLE_DELETION,
        /**
         * Role addition to a Group. Arguments : 1- added role. 2- concerned
         * parent group.
         */
        ROLE_ADDED_TO_GROUP,
        /**
         * Role removal from a Group. Arguments : 1- removed role. 2- concerned
         * parent group.
         */
        ROLE_REMOVED_FROM_GROUP,
        /**
         * Role addition to a user. Arguments : 1- added role. 2- concerned
         * parent user.
         */
        ROLE_ADDED_TO_USER,
        /**
         * Role removal from a user. Arguments : 1- removed role. 2- concerned
         * parent user.
         */
        ROLE_REMOVED_FROM_USER
    };

    private RoleEventType type;
    private User user;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private Group group;

    public Role getRole() {
        return (Role)this.source;
    }

    public RoleEventType getType() {
        return type;
    }

    public void setType(RoleEventType type) {
        this.type = type;
    }

    public RoleEvent(RoleEventType type, Role role) {
        super(role);
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoleEvent(RoleEventType type, Role role, User user) {
        super(role);
        this.type = type;
        this.user = user;
    }

    public RoleEvent(RoleEventType type, Role role, Group group) {
        super(role);
        this.type = type;
        this.group = group;
    }

}
