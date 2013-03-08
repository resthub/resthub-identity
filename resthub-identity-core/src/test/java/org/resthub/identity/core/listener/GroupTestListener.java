package org.resthub.identity.core.listener;

import org.resthub.identity.core.event.GroupEvent;
import org.springframework.context.ApplicationListener;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple listener for test purposes
 */
@Named("groupTestListener")
public class GroupTestListener implements ApplicationListener<GroupEvent> {

    public List<GroupEvent> groupEvents = new ArrayList<GroupEvent>();

    @Override
    public void onApplicationEvent(GroupEvent event) {
        groupEvents.add(event);
    }

}
