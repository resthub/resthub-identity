package org.resthub.identity.webapp.service;

import org.resthub.identity.model.User;
import org.resthub.identity.core.repository.UserRepository;
import org.resthub.identity.core.service.AbstractUserServiceImpl;
import org.resthub.identity.service.UserService;
import org.resthub.identity.service.tracability.ServiceListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.elasticsearch.client.Client;
import org.resthub.identity.webapp.elasticsearch.Indexer;
import org.springframework.util.Assert;


import javax.inject.Inject;
import javax.inject.Named;

public class WebAppUserServiceImpl extends AbstractUserServiceImpl<User, UserRepository> implements UserService, ServiceListener {

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

    public WebAppUserServiceImpl() {
        this.addListener(this);
    }

    @Override
	@Inject
	@Named("userRepository")
	public void setRepository(UserRepository userRepository) {
		super.setRepository(userRepository);
	}

    @Override
    public void onChange(String type, Object... arguments) {
        Assert.notEmpty(arguments);
        User user = (User)arguments[0];
        if(type.equals(UserServiceChange.USER_CREATION.name())) {
            indexer.add(client, user, indexName, indexType, user.getId().toString());
        } else if(type.equals(UserServiceChange.USER_UPDATE.name())) {
            indexer.edit(client, user, indexName, indexType, user.getId().toString());
        } else if(type.equals(UserServiceChange.USER_DELETION.name())) {
            indexer.delete(client, indexName, indexType, user.getId().toString());
        }
    }
}
