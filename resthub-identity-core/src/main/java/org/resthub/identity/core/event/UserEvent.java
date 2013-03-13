package org.resthub.identity.core.event;

import org.resthub.identity.model.Group;
import org.resthub.identity.model.User;
import org.springframework.context.ApplicationEvent;

public class UserEvent extends ApplicationEvent {

    /**
     * Kind of changes notified by this service
     */
    public enum UserEventType {

        /**
         * User creation. Arguments : 1- created user.
         */
        USER_CREATION,

        /**
         * User update. Arguments : 1- updated user.
         */
        USER_UPDATE,
        /**
         * User deletion. Arguments : 1- deleted user.
         */
        USER_DELETION,
        /**
         * User addition to a group. Arguments : 1- added user. 2- concerned
         * group.
         */
        USER_ADDED_TO_GROUP,
        /**
         * User removal from a group. Arguments : 1- removed user. 2- concerned
         * group.
         */
        USER_REMOVED_FROM_GROUP
    };

    private UserEventType type;
    private Group group;

    public User getUser() {
        return (User)this.source;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UserEventType getType() {
        return type;
    }

    public void setType(UserEventType type) {
        this.type = type;
    }

    public UserEvent(UserEventType type, User user) {
        super(user);
        this.type = type;
    }

    public UserEvent(UserEventType type, User user, Group group) {
        super(user);
        this.type = type;
        this.group = group;
    }

}
