package org.resthub.identity.webapp.service;

import org.elasticsearch.client.Client;
import org.resthub.identity.core.service.RoleServiceImpl;
import org.resthub.identity.model.Role;
import org.resthub.identity.service.*;
import org.resthub.identity.service.tracability.ServiceListener;
import org.resthub.identity.webapp.elasticsearch.Indexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Default implementation of a Role Service (can be override by creating a bean with the same name after this one)
 * @author "Nicolas Morel <nicolas.morel@atosorigin.com>"
 */
public class WebAppRoleServiceImpl extends RoleServiceImpl implements ServiceListener {

	private @Value("#{esProp['index.name']}") String indexName;
    private @Value("#{esProp['index.role.type']}") String indexType;
	@Autowired Client client;

	
	/**
     * Injection of elasticsearch indexer;
     */
	@Inject
	@Named("elasticIndexer")
	private Indexer indexer;

    public WebAppRoleServiceImpl() {
        this.addListener(this);
    }

    @Override
    public void onChange(String type, Object... arguments) {
        Assert.notEmpty(arguments);
        Role role = (Role)arguments[0];
        if(type.equals(RoleChange.ROLE_CREATION.name())) {
            indexer.add(client, role, indexName, indexType, role.getId().toString());
        } else if(type.equals(RoleService.RoleChange.ROLE_UPDATE.name())) {
            indexer.edit(client, role, indexName, indexType, role.getId().toString());
        } else if(type.equals(RoleService.RoleChange.ROLE_DELETION.name())) {
            indexer.delete(client, indexName, indexType, role.getId().toString());
        }
    }

}
