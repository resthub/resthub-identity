package org.resthub.identity.webapp.service;

import org.elasticsearch.client.Client;
import org.resthub.identity.core.service.GroupServiceImpl;
import org.resthub.identity.model.Group;
import org.resthub.identity.service.*;
import org.resthub.identity.service.tracability.ServiceListener;
import org.resthub.identity.webapp.elasticsearch.Indexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * An implementation of a Group Service<br/>
 *
 * It's a bean whose name is "groupService"
 * */
public class WebAppGroupServiceImpl extends GroupServiceImpl implements ServiceListener {

	private @Value("#{esProp['index.name']}") String indexName;
    private @Value("#{esProp['index.group.type']}") String indexType;

    @Autowired Client client;
    
    /**
     * Injection of elasticsearch indexer;
     */
    @Inject
    @Named("elasticIndexer")
    private Indexer indexer;

    public WebAppGroupServiceImpl() {
        this.addListener(this);
    }

    @Override
    public void onChange(String type, Object... arguments) {
        Assert.notEmpty(arguments);
        Group group = (Group)arguments[0];
        if(type.equals(GroupService.GroupServiceChange.GROUP_CREATION.name())) {
            indexer.add(client, group, indexName, indexType, group.getId().toString());
        } else if(type.equals(GroupService.GroupServiceChange.GROUP_UPDATE.name())) {
            indexer.edit(client, group, indexName, indexType, group.getId().toString());
        } else if(type.equals(GroupService.GroupServiceChange.GROUP_DELETION.name())) {
            indexer.delete(client, indexName, indexType, group.getId().toString());
        }
    }

}
