package org.resthub.identity.webapp.service;

import org.elasticsearch.client.Client;
import org.resthub.identity.core.event.RoleEvent;
import org.resthub.identity.core.service.AbstractRoleService;
import org.resthub.identity.model.Role;
import org.resthub.identity.webapp.elasticsearch.Indexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Default implementation of a Role Service (can be override by creating a bean with the same name after this one)
 *
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
@Named("webAppRoleService")
public class WebAppRoleServiceImpl extends AbstractRoleService implements ApplicationListener<RoleEvent> {

    @Autowired
    Client client;
    private
    @Value("#{esProp['index.name']}")
    String indexName;
    private
    @Value("#{esProp['index.role.type']}")
    String indexType;
    /**
     * Injection of elasticsearch indexer;
     */
    @Inject
    @Named("elasticIndexer")
    private Indexer indexer;

    @Override
    public void onApplicationEvent(RoleEvent event) {
        Role role = event.getRole();
        if (event.getType() == RoleEvent.RoleEventType.ROLE_CREATION) {
            indexer.add(client, role, indexName, indexType, role.getId().toString());
        } else if (event.getType() == RoleEvent.RoleEventType.ROLE_UPDATE) {
            indexer.edit(client, role, indexName, indexType, role.getId().toString());
        } else if (event.getType() == RoleEvent.RoleEventType.ROLE_DELETION) {
            indexer.delete(client, indexName, indexType, role.getId().toString());
        }
    }

}
