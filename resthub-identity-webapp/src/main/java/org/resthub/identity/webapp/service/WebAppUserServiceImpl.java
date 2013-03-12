package org.resthub.identity.webapp.service;

import org.resthub.identity.core.event.UserEvent;
import org.resthub.identity.model.User;
import org.resthub.identity.core.repository.UserRepository;
import org.resthub.identity.core.service.GenericUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.elasticsearch.client.Client;
import org.resthub.identity.webapp.elasticsearch.Indexer;
import org.springframework.context.ApplicationListener;

import javax.inject.Inject;
import javax.inject.Named;

public class WebAppUserServiceImpl extends GenericUserServiceImpl<User, UserRepository> implements ApplicationListener<UserEvent> {

    private @Value("#{esProp['index.name']}") String indexName;
    private @Value("#{esProp['index.user.type']}") String indexType;
    @Autowired
    Client client;

    /**
     * Injection of elasticsearch indexer;
     */
    @Inject
    @Named("elasticIndexer")
    private Indexer indexer;

    @Override
	@Inject
	@Named("userRepository")
	public void setRepository(UserRepository userRepository) {
		super.setRepository(userRepository);
	}

    @Override
    public void onApplicationEvent(UserEvent event) {
        User user = event.getUser();
        if(event.getType() == UserEvent.UserEventType.USER_CREATION) {
            indexer.add(client, user, indexName, indexType, user.getId().toString());
        } else if(event.getType() == UserEvent.UserEventType.USER_UPDATE) {
            indexer.edit(client, user, indexName, indexType, user.getId().toString());
        } else if(event.getType() == UserEvent.UserEventType.USER_DELETION) {
            indexer.delete(client, indexName, indexType, user.getId().toString());
        }
    }
}
