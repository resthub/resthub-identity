package org.resthub.identity.core.event;

import org.resthub.identity.model.Group;
import org.springframework.context.ApplicationEvent;

public class GroupEvent extends ApplicationEvent {

    /**
     * Kind of changes notified by this service
     */
    public enum GroupEventType {

        /**
         * Group creation. Arguments : 1- created group.
         */
        GROUP_CREATION,
        /**
         * Group update. Arguments : 1- updated group.
         */
        GROUP_UPDATE,
        /**
         * Group deletion. Arguments : 1- deleted group.
         */
        GROUP_DELETION,
        /**
         * Group addition to a group. Arguments : 1- added group. 2- concerned parent group.
         */
        GROUP_ADDED_TO_GROUP,
        /**
         * Group removal from a group. Arguments : 1- removed group. 2- concerned parent group.
         */
        GROUP_REMOVED_FROM_GROUP
    };

    private GroupEventType type;
    private Group subGroup;

    public Group getGroup() {
        return (Group)this.source;
    }

    public GroupEventType getType() {
        return type;
    }

    public void setType(GroupEventType type) {
        this.type = type;
    }

    public GroupEvent(GroupEventType type, Group group) {
        super(group);
        this.type = type;
    }

    public Group getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(Group subGroup) {
        this.subGroup = subGroup;
    }

    public GroupEvent(GroupEventType type, Group group, Group subGroup) {
        super(group);
        this.type = type;
        this.subGroup = subGroup;

    }
}
