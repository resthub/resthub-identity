package org.resthub.identity.webapp.service;

import org.elasticsearch.client.Client;
import org.resthub.identity.core.event.GroupEvent;
import org.resthub.identity.core.service.GenericGroupServiceImpl;
import org.resthub.identity.model.Group;
import org.resthub.identity.webapp.elasticsearch.Indexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * An implementation of a Group Service<br/>
 *
 * It's a bean whose name is "groupService"
 * */
public class WebAppGroupServiceImpl extends GenericGroupServiceImpl implements ApplicationListener<GroupEvent> {

	private @Value("#{esProp['index.name']}") String indexName;
    private @Value("#{esProp['index.group.type']}") String indexType;

    @Autowired Client client;
    
    /**
     * Injection of elasticsearch indexer;
     */
    @Inject
    @Named("elasticIndexer")
    private Indexer indexer;

    @Override
    public void onApplicationEvent(GroupEvent event) {
        Group group = event.getGroup();
        if(event.getType() == GroupEvent.GroupEventType.GROUP_CREATION) {
            indexer.add(client, group, indexName, indexType, group.getId().toString());
        } else if(event.getType() == GroupEvent.GroupEventType.GROUP_UPDATE) {
            indexer.edit(client, group, indexName, indexType, group.getId().toString());
        } else if(event.getType() == GroupEvent.GroupEventType.GROUP_DELETION) {
            indexer.delete(client, indexName, indexType, group.getId().toString());
        }
    }

}
