package org.resthub.identity.webapp.elasticsearch;

import javax.inject.Named;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("elasticDeleter")
public class Deleter {

private Logger logger = LoggerFactory.getLogger(Indexer.class);
	
public void deleteIndex(Client client, String indexName, String indexType){
	
	logger.debug("Delete index : " + indexName + "with type "+ indexType);
	
	client.prepareDeleteByQuery(indexName).
    setQuery(QueryBuilders.matchAllQuery()).
    setTypes(indexType).
    execute().actionGet();

	logger.debug("Deleted index : " + indexName + "with type "+ indexType);
	
}
}