package org.resthub.identity.core.listener;

import org.resthub.identity.core.event.UserEvent;
import org.springframework.context.ApplicationListener;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple listener for test purposes
 */
@Named("userTestListener")
public class UserTestListener implements ApplicationListener<UserEvent> {

    public List<UserEvent> userEvents = new ArrayList<UserEvent>();

    @Override
    public void onApplicationEvent(UserEvent event) {
        userEvents.add(event);
    }


}
